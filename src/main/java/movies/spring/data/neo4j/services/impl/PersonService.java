package movies.spring.data.neo4j.services.impl;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;
import movies.spring.data.neo4j.repositories.MovieRepository;
import movies.spring.data.neo4j.repositories.PersonRepository;
import movies.spring.data.neo4j.services.IPersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kay on 2018/6/12
 */
@Service
public class PersonService implements IPersonService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Collection<Movie> selectMoviesByPerson(String personName) {
        return movieRepository.findByPersonName(personName);
    }

    @Override
    public List<Person> selectCoActorsByName(String personName,int page,int pageSize) {
        List<Person> personList = new ArrayList<>();
        if (StringUtils.isNotBlank(personName)) {
            personList = personRepository.selectCoActorsWithName(personName, PageRequest.of(page, pageSize));

        }
        return personList;
    }
}
