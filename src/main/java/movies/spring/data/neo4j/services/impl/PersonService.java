package movies.spring.data.neo4j.services.impl;

import lombok.extern.slf4j.Slf4j;
import movies.spring.data.neo4j.domain.neo4j.Movie;
import movies.spring.data.neo4j.domain.neo4j.Person;
import movies.spring.data.neo4j.repositories.neo4j.MovieRepository;
import movies.spring.data.neo4j.repositories.neo4j.PersonRepository;
import movies.spring.data.neo4j.services.IPersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by kay on 2018/6/12
 */

@Slf4j
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

    /**
     *
     * @param personName
     * @return
     */
    public Collection selecOtherActorsWithRank(String personName){
        List<Map<String,Long>> rankers = personRepository.selecOtherActorsWithRank(personName);

        log.info("recommond rank :{}",rankers);

        return rankers;
    }
}
