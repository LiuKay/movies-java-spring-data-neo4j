package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;

import java.util.Collection;
import java.util.List;

/**
 * Created by kay on 2018/6/12
 */
public interface IPersonService {

    /**
     * 查询某人参演的所有电影
     * @param personName
     * @return
     */
    Collection<Movie> selectMoviesByPerson(String personName);

    List<Person> selectCoActorsByName(String personName,int page,int pageSize);

    Collection selecOtherActorsWithRank(String personName);
}
