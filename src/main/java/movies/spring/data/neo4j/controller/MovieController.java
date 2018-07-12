package movies.spring.data.neo4j.controller;

import java.util.Collection;
import java.util.Map;

import movies.spring.data.neo4j.domain.neo4j.Movie;
import movies.spring.data.neo4j.services.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Angrish
 */
@RestController
@RequestMapping("/")
public class MovieController {

    @Autowired
	private IMovieService iMovieService;

    /**
     * 根据电影名搜索--精确查找
     * @param title
     * @return
     */
    @GetMapping("/movie")
    public Movie findByTitle(@RequestParam String title) {
        return iMovieService.findByTitle(title);
    }

    /**
     * 根据电影名搜索--模糊搜索
     * @param title
     * @return
     */
    @GetMapping("/movies")
    public Collection<Movie> findByTitleLike(@RequestParam String title) {
        return iMovieService.findByTitleLike(title);
    }

    /**
     * 查询所有 person-ACTED_IN-movie 绘制成网状
     * @param limit 条数
     * @return
     */
    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return iMovieService.graph(limit == null ? 100 : limit);
	}

}
