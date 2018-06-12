package movies.spring.data.neo4j.controller;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.services.IMovieService;
import movies.spring.data.neo4j.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by kay on 2018/6/12
 */

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private IPersonService iPersonService;

    @Autowired
    private IMovieService iMovieService;

    @GetMapping("/getMovies")
    public Collection getMovies(String personName){
        Collection<Movie> movies = iPersonService.selectMoviesByPerson(personName);
        return movies;
    }

    @GetMapping("/co-actors")
    public Collection getCoactorsInMovie(@RequestParam("movieTile") String movieTile,
                                         @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                                         @RequestParam(value = "pageSize",required = false,defaultValue = "5") int pageSize){
       return iMovieService.findActorsByTitle(movieTile,page,pageSize);
    }

}
