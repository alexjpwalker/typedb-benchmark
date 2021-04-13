/*
 * Copyright (C) 2021 Grakn Labs
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

package grakn.benchmark.simulation.agent;

import grakn.benchmark.simulation.action.Action;
import grakn.benchmark.simulation.action.ActionFactory;
import grakn.benchmark.simulation.common.GeoData;
import grakn.benchmark.simulation.common.SimulationContext;
import grakn.benchmark.simulation.driver.Client;
import grakn.benchmark.simulation.driver.Session;
import grakn.benchmark.simulation.driver.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ArbitraryOneHopAgent<TX extends Transaction> extends Agent<GeoData.Global, TX> {

    public ArbitraryOneHopAgent(Client<?, TX> client, ActionFactory<TX, ?> actionFactory, SimulationContext context) {
        super(client, actionFactory, context);
    }

    @Override
    protected List<GeoData.Global> getRegions() {
        return Collections.singletonList(context.geoData().global());
    }

    @Override
    protected List<Action<?, ?>.Report> run(Session<TX> session, GeoData.Global region, Random random) {
        List<Action<?, ?>.Report> reports = new ArrayList<>();
        for (int i = 0; i <= context.scaleFactor(); i++) {
            try (TX tx = session.transaction(region.tracker(), context.iterationNumber(), isTracing())) {
                runAction(actionFactory().arbitraryOneHopAction(tx), reports);
            }
        }
        return reports;
    }
}
