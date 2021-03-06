package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.domain.neo4j.Movie;
import movies.spring.data.neo4j.domain.neo4j.Person;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by kay on 2018/6/12
 */
public interface IMovieService {

    Movie findByTitle(String title);

    Collection<Movie> findByTitleLike(String title);


    /**
     *
     * @param limit
     * @return
     */
    Map<String, Object> graph(int limit);

    /**
     * 根据电影名查询参演者   模糊查询
     * @param title  电影
     * @param page
     * @param pageSize
     * @return
     */
    List<Person> findActorsByTitleLike(String title, int page, int pageSize);

    /**
     * 精确查询
     * @param title
     * @param page
     * @param pageSize
     * @return
     */
    List<Person> findActorsByTitle(String title,int page,int pageSize);
}
