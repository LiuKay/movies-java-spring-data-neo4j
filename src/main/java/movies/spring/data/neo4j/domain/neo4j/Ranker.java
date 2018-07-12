package movies.spring.data.neo4j.domain.neo4j;


/**
 * Created by kay on 2018/6/13
 */

public class Ranker {

    private String Recommended;  //推荐名称

    private Long Strength;  //推荐级别

    public Ranker() {
    }

    public String getRecommended() {
        return Recommended;
    }

    public void setRecommended(String recommended) {
        Recommended = recommended;
    }

    public Long getStrength() {
        return Strength;
    }

    public void setStrength(Long strength) {
        Strength = strength;
    }
}
