package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pdtyreus
 * @author Mark Angrish
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findByName(String name);

    /**
     * 查询  co-actors
     * @param personName
     * @param pageable
     * @return
     */
    @Query(value = "MATCH (p:Person)-[:ACTED_IN]->(m)<-[:ACTED_IN]-(coActors) where p.name = {0} RETURN coActors",
            countQuery = "MATCH (p:Person)-[:ACTED_IN]->(m)<-[:ACTED_IN]-(coActors) where p.name = {0} RETURN count(coActors)")
    List<Person> selectCoActorsWithName(String personName, Pageable pageable);
}