package movies.spring.data.neo4j.services.impl;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.repositories.MovieRepository;
import movies.spring.data.neo4j.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by kay on 2018/6/12
 */
@Service
public class PersonService implements IPersonService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Collection<Movie> selectMoviesByPerson(String personName) {
        personName = new StringBuilder().append("(?i).*").append(personName).append(".*").toString();
        return movieRepository.findByPersonNameLike(personName);
    }
}
