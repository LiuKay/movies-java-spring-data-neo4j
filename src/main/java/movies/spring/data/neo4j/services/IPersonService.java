package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;

import java.util.Collection;

/**
 * Created by kay on 2018/6/12
 */
public interface IPersonService {


    Collection<Movie> selectMoviesByPerson(String personName);
}
