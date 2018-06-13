package movies.spring.data.neo4j.repositories;

import java.util.Collection;
import java.util.List;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 */
public interface MovieRepository extends Neo4jRepository<Movie, Long> {

	Movie findByTitle(@Param("title") String title);

	Collection<Movie> findByTitleLike(@Param("title") String title);

	@Query("MATCH (n:`Movie`) WHERE n.`title` =~ {title} RETURN n")
	Collection<Movie> findByTitleLike2(@Param("title") String title);



	@Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
	Collection<Movie> graph(@Param("limit") int limit);

 	/**
	 * 根据人名查出他所饰演的所有电影
	 * @param personName
	 * @return
	 */
//	@Query("Match (p:Person)-[r:ACTED_IN]->(m:Movie) where p.name =~ {personName} return p,r,m")
//	@Query("Match (p:Person)-[r:ACTED_IN]->(m:Movie) where p.name = {personName} return m,p,r")   注意区别
	@Query("Match (p:Person)-[r:ACTED_IN]->(m:Movie) where p.name = {personName} return m")
	Collection<Movie> findByPersonName(@Param("personName") String personName);



	/**
	 * 模糊搜索movieTitle电影的参演者 todo 无意义，用于测试
	 * @param movieTitle
	 * @param pageable
	 * @return
	 */
	@Query(value = "MATCH (m:Movie)<-[:ACTED_IN]-(person) where m.title =~ {0} RETURN person", countQuery= "MATCH (m:Movie)<-[:ACTED_IN]-(person) where m.title=~ {0} RETURN count(person)")
	List<Person> getPersonsActInMovieFromTitleLike(String movieTitle, Pageable pageable);

	/**
	 * 搜索movieTitle电影的参演者
	 * @param movieTitle
	 * @param pageable
	 * @return
	 */
	@Query(value = "MATCH (m:Movie)<-[:ACTED_IN]-(person) where m.title = {0} RETURN person", countQuery= "MATCH (m:Movie)<-[:ACTED_IN]-(person) where m.title= {0} RETURN count(person)")
	List<Person> getPersonsActInMovieFromTitle(String movieTitle, Pageable pageable);


}