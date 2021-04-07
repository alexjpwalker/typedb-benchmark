/* * Copyright (C) 2020 Grakn Labs * * This program is free software: you can redistribute it and/or modify * it under the terms of the GNU Affero General Public License as * published by the Free Software Foundation, either version 3 of the * License, or (at your option) any later version. * * This program is distributed in the hope that it will be useful, * but WITHOUT ANY WARRANTY; without even the implied warranty of * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the * GNU Affero General Public License for more details. * * You should have received a copy of the GNU Affero General Public License * along with this program.  If not, see <https://www.gnu.org/licenses/>. */package grakn.benchmark.neo4j.action;import grakn.benchmark.neo4j.action.insight.Neo4jArbitraryOneHopAction;import grakn.benchmark.neo4j.action.insight.Neo4jFindCurrentResidentsAction;import grakn.benchmark.neo4j.action.insight.Neo4jFindLivedInAction;import grakn.benchmark.neo4j.action.insight.Neo4jFindSpecificMarriageAction;import grakn.benchmark.neo4j.action.insight.Neo4jFindSpecificPersonAction;import grakn.benchmark.neo4j.action.insight.Neo4jFindTransactionCurrencyAction;import grakn.benchmark.neo4j.action.insight.Neo4jFourHopAction;import grakn.benchmark.neo4j.action.insight.Neo4jMeanWageOfPeopleInWorldAction;import grakn.benchmark.neo4j.action.insight.Neo4jThreeHopAction;import grakn.benchmark.neo4j.action.insight.Neo4jTwoHopAction;import grakn.benchmark.neo4j.action.read.Neo4jBirthsInCityAction;import grakn.benchmark.neo4j.action.read.Neo4jCitiesInContinentAction;import grakn.benchmark.neo4j.action.read.Neo4jCompaniesInCountryAction;import grakn.benchmark.neo4j.action.read.Neo4jMarriedCoupleAction;import grakn.benchmark.neo4j.action.read.Neo4jProductsInContinentAction;import grakn.benchmark.neo4j.action.read.Neo4jResidentsInCityAction;import grakn.benchmark.neo4j.action.read.Neo4jUnmarriedPeopleInCityAction;import grakn.benchmark.neo4j.action.write.Neo4jInsertCompanyAction;import grakn.benchmark.neo4j.action.write.Neo4jInsertEmploymentAction;import grakn.benchmark.neo4j.action.write.Neo4jInsertFriendshipAction;import grakn.benchmark.neo4j.action.write.Neo4jInsertMarriageAction;import grakn.benchmark.neo4j.action.write.Neo4jInsertParentShipAction;import grakn.benchmark.neo4j.action.write.Neo4jInsertPersonAction;import grakn.benchmark.neo4j.action.write.Neo4jInsertProductAction;import grakn.benchmark.neo4j.action.write.Neo4jInsertRelocationAction;import grakn.benchmark.neo4j.action.write.Neo4jInsertTransactionAction;import grakn.benchmark.neo4j.action.write.Neo4jUpdateAgesOfPeopleInCityAction;import grakn.benchmark.neo4j.driver.Neo4jTransaction;import grakn.benchmark.simulation.action.ActionFactory;import grakn.benchmark.simulation.action.SpouseType;import grakn.benchmark.simulation.action.insight.ArbitraryOneHopAction;import grakn.benchmark.simulation.action.insight.FindCurrentResidentsAction;import grakn.benchmark.simulation.action.insight.FindLivedInAction;import grakn.benchmark.simulation.action.insight.FindSpecificMarriageAction;import grakn.benchmark.simulation.action.insight.FindSpecificPersonAction;import grakn.benchmark.simulation.action.insight.FindTransactionCurrencyAction;import grakn.benchmark.simulation.action.insight.FourHopAction;import grakn.benchmark.simulation.action.insight.MeanWageOfPeopleInWorldAction;import grakn.benchmark.simulation.action.insight.ThreeHopAction;import grakn.benchmark.simulation.action.insight.TwoHopAction;import grakn.benchmark.simulation.action.read.BirthsInCityAction;import grakn.benchmark.simulation.action.read.CitiesInContinentAction;import grakn.benchmark.simulation.action.read.MarriedCoupleAction;import grakn.benchmark.simulation.action.read.ProductsInContinentAction;import grakn.benchmark.simulation.action.read.UnmarriedPeopleInCityAction;import grakn.benchmark.simulation.action.write.InsertCompanyAction;import grakn.benchmark.simulation.action.write.InsertEmploymentAction;import grakn.benchmark.simulation.action.write.InsertFriendshipAction;import grakn.benchmark.simulation.action.write.InsertMarriageAction;import grakn.benchmark.simulation.action.write.InsertParentShipAction;import grakn.benchmark.simulation.action.write.InsertPersonAction;import grakn.benchmark.simulation.action.write.InsertProductAction;import grakn.benchmark.simulation.action.write.InsertRelocationAction;import grakn.benchmark.simulation.action.write.InsertTransactionAction;import grakn.benchmark.simulation.action.write.UpdateAgesOfPeopleInCityAction;import grakn.benchmark.simulation.common.World;import grakn.common.collection.Pair;import org.neo4j.driver.Record;import java.time.LocalDateTime;import java.util.HashMap;public class Neo4jActionFactory extends ActionFactory<Neo4jTransaction, Record> {    @Override    public Neo4jResidentsInCityAction residentsInCityAction(Neo4jTransaction tx, World.City city, int numResidents, LocalDateTime earliestDate) {        return new Neo4jResidentsInCityAction(tx, city, numResidents, earliestDate);    }    @Override    public Neo4jCompaniesInCountryAction companiesInCountryAction(Neo4jTransaction tx, World.Country country, int numCompanies) {        return new Neo4jCompaniesInCountryAction(tx, country, numCompanies);    }    @Override    public InsertEmploymentAction<Neo4jTransaction, Record> insertEmploymentAction(Neo4jTransaction tx, World.City city, String employeeEmail, long companyNumber, LocalDateTime employmentDate, double wageValue, String contractContent, double contractedHours) {        return new Neo4jInsertEmploymentAction(tx, city, employeeEmail, companyNumber, employmentDate, wageValue, contractContent, contractedHours);    }    @Override    public InsertCompanyAction<Neo4jTransaction, Record> insertCompanyAction(Neo4jTransaction tx, World.Country country, LocalDateTime today, int companyNumber, String companyName) {        return new Neo4jInsertCompanyAction(tx, country, today, companyNumber, companyName);    }    @Override    public InsertFriendshipAction<Neo4jTransaction, Record> insertFriendshipAction(Neo4jTransaction tx, LocalDateTime today, String friend1Email, String friend2Email) {        return new Neo4jInsertFriendshipAction(tx, today, friend1Email, friend2Email);    }    @Override    public UnmarriedPeopleInCityAction<Neo4jTransaction> unmarriedPeopleInCityAction(Neo4jTransaction tx, World.City city, String gender, LocalDateTime dobOfAdults) {        return new Neo4jUnmarriedPeopleInCityAction(tx, city, gender, dobOfAdults);    }    @Override    public InsertMarriageAction<Neo4jTransaction, Record> insertMarriageAction(Neo4jTransaction tx, World.City city, int marriageIdentifier, String wifeEmail, String husbandEmail) {        return new Neo4jInsertMarriageAction(tx, city, marriageIdentifier, wifeEmail, husbandEmail);    }    @Override    public BirthsInCityAction<Neo4jTransaction> birthsInCityAction(Neo4jTransaction tx, World.City city, LocalDateTime today) {        return new Neo4jBirthsInCityAction(tx, city, today);    }    @Override    public MarriedCoupleAction<Neo4jTransaction> marriedCoupleAction(Neo4jTransaction tx, World.City city, LocalDateTime today) {        return new Neo4jMarriedCoupleAction(tx, city, today);    }    @Override    public InsertParentShipAction<Neo4jTransaction, Record> insertParentshipAction(Neo4jTransaction tx, HashMap<SpouseType, String> marriage, String childEmail) {        return new Neo4jInsertParentShipAction(tx, marriage, childEmail);    }    @Override    public InsertPersonAction<Neo4jTransaction, Record> insertPersonAction(Neo4jTransaction tx, World.City city, LocalDateTime today, String email, String gender, String forename, String surname) {        return new Neo4jInsertPersonAction(tx, city, today, email, gender, forename, surname);    }    @Override    public InsertProductAction<Neo4jTransaction, Record> insertProductAction(Neo4jTransaction tx, World.Continent continent, Long barcode, String productName, String productDescription) {        return new Neo4jInsertProductAction(tx, continent, barcode, productName, productDescription);    }    @Override    public CitiesInContinentAction<Neo4jTransaction> citiesInContinentAction(Neo4jTransaction tx, World.City city) {        return new Neo4jCitiesInContinentAction(tx, city);    }    @Override    public InsertRelocationAction<Neo4jTransaction, Record> insertRelocationAction(Neo4jTransaction tx, World.City city, LocalDateTime today, String residentEmail, String relocationCityName) {        return new Neo4jInsertRelocationAction(tx, city, today, residentEmail, relocationCityName);    }    @Override    public ProductsInContinentAction<Neo4jTransaction> productsInContinentAction(Neo4jTransaction tx, World.Continent continent) {        return new Neo4jProductsInContinentAction(tx, continent);    }    @Override    public InsertTransactionAction<Neo4jTransaction, Record> insertTransactionAction(Neo4jTransaction tx, World.Country country, Pair<Long, Long> transaction, Long sellerCompanyNumber, double value, int productQuantity, boolean isTaxable) {        return new Neo4jInsertTransactionAction(tx, country, transaction, sellerCompanyNumber, value, productQuantity, isTaxable);    }    @Override    public UpdateAgesOfPeopleInCityAction<Neo4jTransaction> updateAgesOfPeopleInCityAction(Neo4jTransaction tx, LocalDateTime today, World.City city) {        return new Neo4jUpdateAgesOfPeopleInCityAction(tx, today, city);    }    @Override    public MeanWageOfPeopleInWorldAction<Neo4jTransaction> meanWageOfPeopleInWorldAction(Neo4jTransaction tx) {        return new Neo4jMeanWageOfPeopleInWorldAction(tx);    }    @Override    public FindLivedInAction<Neo4jTransaction> findlivedInAction(Neo4jTransaction tx) {        return new Neo4jFindLivedInAction(tx);    }    @Override    public FindCurrentResidentsAction<Neo4jTransaction> findCurrentResidentsAction(Neo4jTransaction tx) {        return new Neo4jFindCurrentResidentsAction(tx);    }    @Override    public FindTransactionCurrencyAction<Neo4jTransaction> findTransactionCurrencyAction(Neo4jTransaction tx) {        return new Neo4jFindTransactionCurrencyAction(tx);    }    @Override    public ArbitraryOneHopAction<Neo4jTransaction> arbitraryOneHopAction(Neo4jTransaction tx) {        return new Neo4jArbitraryOneHopAction(tx);    }    @Override    public TwoHopAction<Neo4jTransaction> twoHopAction(Neo4jTransaction tx) {        return new Neo4jTwoHopAction(tx);    }    @Override    public ThreeHopAction<Neo4jTransaction> threeHopAction(Neo4jTransaction tx) {        return new Neo4jThreeHopAction(tx);    }    @Override    public FourHopAction<Neo4jTransaction> fourHopAction(Neo4jTransaction tx) {        return new Neo4jFourHopAction(tx);    }    @Override    public FindSpecificMarriageAction<Neo4jTransaction> findSpecificMarriageAction(Neo4jTransaction tx) {        return new Neo4jFindSpecificMarriageAction(tx);    }    @Override    public FindSpecificPersonAction<Neo4jTransaction> findSpecificPersonAction(Neo4jTransaction tx) {        return new Neo4jFindSpecificPersonAction(tx);    }}