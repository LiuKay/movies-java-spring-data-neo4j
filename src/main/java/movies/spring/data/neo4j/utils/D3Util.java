package movies.spring.data.neo4j.utils;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Role;

import java.util.*;

/**
 * Created by kay on 2018/6/12
 */
public class D3Util {


    /**
     * 转换成 d3 显示格式
     * @param movies
     * @return
     */
    public static Map<String, Object> toD3Format(Collection<Movie> movies) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Movie> result = movies.iterator();
        while (result.hasNext()) {
            Movie movie = result.next();
            nodes.add(map("title", movie.getTitle(), "label", "movie"));
            int target = i;
            i++;
            for (Role role : movie.getRoles()) {
                Map<String, Object> actor = map("title", role.getPerson().getName(), "label", "actor");

                //去重
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }

                rels.add(map("source", source, "target", target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    /**
     *  设置属性
     *  node 属性：      "title": name
     *  				"lable":"movie" / "actor"
     *
     *  relationship 属性："source":
     *  				  "target":
     *
     * @param key1
     * @param value1
     * @param key2
     * @param value2
     * @return
     */
    private static Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }
}
