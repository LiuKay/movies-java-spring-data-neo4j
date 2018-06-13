package movies.spring.data.neo4j.services.impl;

import java.util.*;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;
import movies.spring.data.neo4j.domain.Role;
import movies.spring.data.neo4j.repositories.MovieRepository;
import movies.spring.data.neo4j.services.IMovieService;
import movies.spring.data.neo4j.utils.D3Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService implements IMovieService {

    private final static Logger LOG = LoggerFactory.getLogger(MovieService.class);

    @Autowired
	private  MovieRepository movieRepository;


    @Transactional(readOnly = true)
    public Movie findByTitle(String title) {
        Movie result = movieRepository.findByTitle(title);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Movie> findByTitleLike(String title) {
        Collection<Movie> result = movieRepository.findByTitleLike(title);
        return result;
    }

	@Transactional(readOnly = true)
	public Map<String, Object>  graph(int limit) {
		Collection<Movie> result = movieRepository.graph(limit);
		return D3Util.toD3Format(result);
	}


	public List<Person> findActorsByTitleLike(String title,int page,int pageSize){
        title = new StringBuilder().append("(?i).*").append(title).append(".*").toString();
        List<Person> personList = movieRepository.getPersonsActInMovieFromTitleLike(title, PageRequest.of(page, pageSize));
        return personList;
    }

    public List<Person> findActorsByTitle(String title,int page,int pageSize){
        List<Person> personList = movieRepository.getPersonsActInMovieFromTitle(title, PageRequest.of(page, pageSize));
        return personList;
    }
}
