/*
 * Copyright (C) 2020 Grakn Labs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package grakn.benchmark.simulation.agent.write;

import grakn.benchmark.simulation.action.Action;
import grakn.benchmark.simulation.action.ActionFactory;
import grakn.benchmark.simulation.action.read.CompaniesInCountryAction;
import grakn.benchmark.simulation.action.read.ProductsInContinentAction;
import grakn.benchmark.simulation.agent.Agent;
import grakn.benchmark.simulation.agent.base.Allocation;
import grakn.benchmark.simulation.agent.base.RandomValueGenerator;
import grakn.benchmark.simulation.agent.base.SimulationContext;
import grakn.benchmark.simulation.driver.Session;
import grakn.benchmark.simulation.driver.Transaction;
import grakn.benchmark.simulation.driver.Client;
import grakn.benchmark.simulation.world.World;
import grakn.common.collection.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static grakn.common.collection.Collections.pair;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

public class PurchaseAgent<TX extends Transaction> extends Agent<World.Country, TX> {

    public PurchaseAgent(Client<? extends Session<TX>, TX> client, ActionFactory<TX, ?> actionFactory, SimulationContext context) {
        super(client, actionFactory, context);
    }

    @Override
    protected List<World.Country> getRegions(World world) {
        return world.getCountries().collect(toList());
    }

    @Override
    protected List<Action<?, ?>.Report> run(Session<TX> session, World.Country region, Random random) {
        List<Action<?, ?>.Report> reports = new ArrayList<>();
        List<Long> companyNumbers;

        try (TX tx = session.newTransaction(region.tracker(), context.iteration(), isTracing())) {
            CompaniesInCountryAction<TX> companiesInContinentAction = actionFactory().companiesInCountryAction(tx, region, 100);
            companyNumbers = runAction(companiesInContinentAction, reports);
        }
        shuffle(companyNumbers, random);

        List<Long> productBarcodes;
        try (TX tx = session.newTransaction(region.tracker(), context.iteration(), isTracing())) {
            ProductsInContinentAction<?> productsInContinentAction = actionFactory().productsInContinentAction(tx, region.continent());
            productBarcodes = runAction(productsInContinentAction, reports);
        }

        int numTransactions = context.world().getScaleFactor() * companyNumbers.size();
        // Company numbers is the list of sellers
        // Company numbers picked randomly is the list of buyers
        // Products randomly picked

        // See if we can allocate with a Pair, which is the buyer and the product id
        List<Pair<Long, Long>> transactions = new ArrayList<>();
        for (int i = 0; i < numTransactions; i++) {
            Long companyNumber = pickOne(companyNumbers, random);
            Long productBarcode = pickOne(productBarcodes, random);
            Pair<Long, Long> buyerAndProduct = pair(companyNumber, productBarcode);
            transactions.add(buyerAndProduct);
        }
        try (TX tx = session.newTransaction(region.tracker(), context.iteration(), isTracing())) {
            Allocation.allocate(transactions, companyNumbers, (transaction, sellerCompanyNumber) -> {
                double value = RandomValueGenerator.of(random).boundRandomDouble(0.01, 10000.00);
                int productQuantity = RandomValueGenerator.of(random).boundRandomInt(1, 1000);
                boolean isTaxable = RandomValueGenerator.of(random).bool();
                runAction(actionFactory().insertTransactionAction(tx, region, transaction, sellerCompanyNumber, value, productQuantity, isTaxable), reports);
            });
            tx.commit();
        }

        return reports;
    }
}
