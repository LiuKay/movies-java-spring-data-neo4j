package movies.spring.data.neo4j.controller;

import movies.spring.data.neo4j.domain.neo4j.Movie;
import movies.spring.data.neo4j.domain.neo4j.Person;
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

    /**
     * 搜索参演电影
     * @param personName 演员名
     * @return
     */
    @GetMapping("/getMovies")
    public Collection getMovies(String personName){
        Collection<Movie> movies = iPersonService.selectMoviesByPerson(personName);
        return movies;
    }

    /**
     *
     * @param movieTile
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getActorsWithTitle")
    public Collection<Person> getActorsWithTitle(@RequestParam("movieTile") String movieTile,
                                                 @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                                                 @RequestParam(value = "pageSize",required = false,defaultValue = "5") int pageSize){
       return iMovieService.findActorsByTitle(movieTile,page,pageSize);
    }

    @GetMapping("/getActorsWithPersonName")
    public Collection<Person> getActorsWithPersonName(@RequestParam("personName") String personName,
                                        @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                                        @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize){
        return iPersonService.selectCoActorsByName(personName, page, pageSize);
    }


    @GetMapping("/getUnCoActors")
    public Collection getUnCoActors(@RequestParam("personName") String personName) {
        return iPersonService.selecOtherActorsWithRank(personName);
    }
}
