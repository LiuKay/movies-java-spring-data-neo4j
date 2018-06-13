package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.Person;
import movies.spring.data.neo4j.domain.Ranker;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author kay
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


    /**
     * 查询关系 与电影 Cloud Atlas 的关系
     */
    @Query("MATCH (people:Person)-[relatedTo]-(:Movie {title: \"Cloud Atlas\"}) RETURN people.name, Type(relatedTo), relatedTo")
    Collection selectRelationship();


    /**
     *
     *  MATCH (bacon:Person {name:"Kevin Bacon"})-[*1..4]-(hollywood) RETURN DISTINCT hollywood
     *
     * 最短路径查询  Kevin Bacon 到 Meg Ryan 的最短关系路径
     */
    @Query("MATCH p=shortestPath( (bacon:Person {name:\"Kevin Bacon\"})-[*]-(meg:Person {name:\"Meg Ryan\"}) RETURN p")
    Collection selectShortestPath();

    /**
     *
     * Find actors that XX hasn't yet worked with, but his co-actors have
     *
     * 查询 没有与其一起演过戏 但与他曾搭过戏的人一起演过戏的人  以及 排名
     * @param personName
     * @return
     */
    @Query("MATCH (p:Person)-[:ACTED_IN]->(m1)<-[:ACTED_IN]-(coActors)," +
            "(coActors)-[:ACTED_IN]->(m2)<-[:ACTED_IN]-(otherActors) " +
            "where not (p)-[:ACTED_IN]->()<-[:ACTED_IN]-(otherActors) " +
            "and p<> otherActors " +
            "and p.name = {personName} " +
            "RETURN otherActors.name AS Recommended, count(*) AS Strength ORDER BY Strength DESC")
    List<Map<String,Long>> selecOtherActorsWithRank(@Param("personName") String personName);
}