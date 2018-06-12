package movies.spring.data.neo4j.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;
import movies.spring.data.neo4j.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    //查询指定 movie 的关系

//	@Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) WHERE m.`title` =~ {title} RETURN m,r,a LIMIT {limit}")
//	Collection<Movie> graph(@Param("title") String title,@Param("limit") int limit);


//	Match (p:Person)-[r:ACTED_IN]->(m:Movie) where p.name =~ '.*Tom.*' return p,r,m")

	/**
	 * 根据人名查出他所饰演的所有电影
	 * @param personName
	 * @return
	 */
//	@Query("Match (p:Person)-[r:ACTED_IN]->(m:Movie) where p.name =~ {personName} return p,r,m")
	@Query("Match (p:Person)-[r:ACTED_IN]->(m:Movie) where p.name =~ {personName} return m,r,p")
	Collection<Movie> findByPersonNameLike(@Param("personName") String personName);


	@Query("MATCH (n) WHERE id(n)={0} RETURN n")
	Movie getMovieFromId(Integer idOfMovie);


	@Query("MATCH (movie:Movie {title:{0}}) RETURN movie")
	Movie getMovieFromTitle(String movieTitle);

	@Query("MATCH (movie:Movie {title:{0}}) RETURN movie")
	Optional<Movie> getMovieFromTitle2(String movieTitle);


	@Query(value = "MATCH (m:Movie)<-[:ACTED_IN]-(person) where m.title =~ {0} RETURN person", countQuery= "MATCH (m:Movie)<-[:ACTED_IN]-(person) where m.title=~ {0} RETURN count(person)")
	List<Person> getActorsThatActInMovieFromTitle(String movieTitle, Pageable pageable);
}