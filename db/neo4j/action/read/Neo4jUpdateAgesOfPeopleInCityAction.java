package grakn.simulation.db.neo4j.action.read;

import grakn.simulation.db.common.action.read.UpdateAgesOfPeopleInCityAction;
import grakn.simulation.db.common.world.World;
import grakn.simulation.db.neo4j.driver.Neo4jOperation;
import grakn.simulation.db.neo4j.schema.Schema;
import org.neo4j.driver.Query;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Neo4jUpdateAgesOfPeopleInCityAction extends UpdateAgesOfPeopleInCityAction<Neo4jOperation> {
    public Neo4jUpdateAgesOfPeopleInCityAction(Neo4jOperation dbOperation, LocalDateTime today, World.City city) {
        super(dbOperation, today, city);
    }

    @Override
    public Integer run() {
        String template = "" +
                "MATCH (person:Person)-[:BORN_IN]->(city:City {locationName: $locationName})\n" +
                "SET person.age = duration.between(person.dateOfBirth, localdatetime($dateToday)).years\n" +
                "RETURN person.age";

        HashMap<String, Object> parameters = new HashMap<String, Object>(){{
            put(Schema.LOCATION_NAME, city.name());
            put("dateToday", today);
        }};
        dbOperation.execute(new Query(template, parameters));
        return null;
    }
}
