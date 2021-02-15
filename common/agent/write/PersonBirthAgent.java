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
import grakn.benchmark.common.agent.region.CityAgent;
import grakn.benchmark.common.driver.DbDriver;
import grakn.benchmark.common.driver.DbOperation;
import grakn.benchmark.common.driver.DbOperationFactory;
import grakn.benchmark.common.world.World;

import java.util.Random;

public class PersonBirthAgent<DB_OPERATION extends DbOperation> extends CityAgent<DB_OPERATION> {

    public PersonBirthAgent(DbDriver<DB_OPERATION> dbDriver, ActionFactory<DB_OPERATION, ?> actionFactory, grakn.benchmark.common.agent.base.BenchmarkContext benchmarkContext) {
        super(dbDriver, actionFactory, benchmarkContext);
    }

    @Override
    protected Regional getRegionalAgent(int iteration, String tracker, Random random, boolean test) {
        return new City(iteration, tracker, random, test);
    }

    public class City extends CityRegion {
        public City(int iteration, String tracker, Random random, boolean test) {
            super(iteration, tracker, random, test);
        }

        @Override
        protected void run(DbOperationFactory<DB_OPERATION> dbOperationFactory, World.City city) {
            // Find bachelors and bachelorettes who are considered adults and who are not in a marriage and pair them off randomly
            int numBirths = benchmarkContext.world().getScaleFactor();
            try (DB_OPERATION dbOperation = dbOperationFactory.newDbOperation(tracker(), iteration(), isTracing(), true)) {
                for (int i = 0; i < numBirths; i++) {
                    String gender;
                    String forename;
                    String surname = pickOne(benchmarkContext.world().getSurnames());

                    boolean genderBool = random().nextBoolean();
                    if (genderBool) {
                        gender = "male";
                        forename = pickOne(benchmarkContext.world().getMaleForenames());
                    } else {
                        gender = "female";
                        forename = pickOne(benchmarkContext.world().getFemaleForenames());
                    }
                    String email = "email/" + uniqueId(benchmarkContext, i);
                    runAction(actionFactory().insertPersonAction(dbOperation, city, benchmarkContext.today(), email, gender, forename, surname));
                }
                dbOperation.save();
            }
        }
    }
}
