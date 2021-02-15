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

package grakn.benchmark.common.agent.write;

import grakn.benchmark.common.action.ActionFactory;
import grakn.benchmark.common.action.read.CompaniesInCountryAction;
import grakn.benchmark.common.action.read.ResidentsInCityAction;
import grakn.benchmark.common.agent.region.CityAgent;
import grakn.benchmark.common.driver.DbDriver;
import grakn.benchmark.common.driver.DbOperation;
import grakn.benchmark.common.driver.DbOperationFactory;
import grakn.benchmark.common.world.World;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static grakn.benchmark.common.agent.base.Allocation.allocate;

public class EmploymentAgent<DB_OPERATION extends DbOperation> extends CityAgent<DB_OPERATION> {

    public EmploymentAgent(DbDriver<DB_OPERATION> dbDriver, ActionFactory<DB_OPERATION, ?> actionFactory, grakn.benchmark.common.agent.base.BenchmarkContext benchmarkContext) {
        super(dbDriver, actionFactory, benchmarkContext);
    }

    @Override
    protected Regional getRegionalAgent(int iteration, String tracker, Random random, boolean test) {
        return new City(iteration, tracker, random, test);
    }

    public class City extends CityRegion {
        double MIN_ANNUAL_WAGE = 18000.00;
        double MAX_ANNUAL_WAGE = 80000.00;
        double MIN_CONTRACTED_HOURS = 30.0;
        double MAX_CONTRACTED_HOURS = 70.0;
        int MIN_CONTRACT_CHARACTER_LENGTH = 200;
        int MAX_CONTRACT_CHARACTER_LENGTH = 600;

        public City(int iteration, String tracker, Random random, boolean test) {
            super(iteration, tracker, random, test);
        }

        @Override
        protected void run(DbOperationFactory<DB_OPERATION> dbOperationFactory, World.City city) {
            LocalDateTime employmentDate = benchmarkContext.today().minusYears(0);
            List<String> employeeEmails;
            List<Long> companyNumbers;

            try (DB_OPERATION dbOperation = dbOperationFactory.newDbOperation(tracker(), iteration(), isTracing(), false)) {
                ResidentsInCityAction<DB_OPERATION> employeeEmailsAction = actionFactory().residentsInCityAction(dbOperation, city, benchmarkContext.world().getScaleFactor(), employmentDate);
                employeeEmails = runAction(employeeEmailsAction);
                System.out.println("Employee emails: " + employeeEmails);
            }

            try (DB_OPERATION dbOperation = dbOperationFactory.newDbOperation(tracker(), iteration(), isTracing(), false)) {
                CompaniesInCountryAction<DB_OPERATION> companyNumbersAction = actionFactory().companiesInCountryAction(dbOperation, city.country(), benchmarkContext.world().getScaleFactor());
                companyNumbers = runAction(companyNumbersAction);
            }

            try (DB_OPERATION dbOperation = dbOperationFactory.newDbOperation(tracker(), iteration(), isTracing(), true)) {
                // A second transaction is being used to circumvent graknlabs/grakn issue #5585
                boolean allocated = allocate(employeeEmails, companyNumbers, (employeeEmail, companyNumber) -> {
                    double wageValue = randomAttributeGenerator().boundRandomDouble(MIN_ANNUAL_WAGE, MAX_ANNUAL_WAGE);
                    String contractContent = randomAttributeGenerator().boundRandomLengthRandomString(MIN_CONTRACT_CHARACTER_LENGTH, MAX_CONTRACT_CHARACTER_LENGTH);
                    double contractedHours = randomAttributeGenerator().boundRandomDouble(MIN_CONTRACTED_HOURS, MAX_CONTRACTED_HOURS);
                    runAction(actionFactory().insertEmploymentAction(dbOperation, city, employeeEmail, companyNumber, employmentDate, wageValue, contractContent, contractedHours));
                });
                if (allocated) {
                    dbOperation.save();
                }
            }
        }
    }
}
