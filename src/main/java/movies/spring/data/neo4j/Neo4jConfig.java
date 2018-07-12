package movies.spring.data.neo4j;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by kay on 2018/7/12.
 */
@Configuration
@EnableNeo4jRepositories("movies.spring.data.neo4j.repositories")
public class Neo4jConfig {

    @Value("${spring.data.neo4j.uri}")
    private String uri;

    @Value("${spring.data.neo4j.username}")
    private String username;

    @Value("${spring.data.neo4j.password}")
    private String password;



    @Bean
    public SessionFactory sessionFactory() {
//        ClasspathConfigurationSource configurationSource = new ClasspathConfigurationSource("/ogm.properties");
        org.neo4j.ogm.config.Configuration.Builder builder = new org.neo4j.ogm.config.Configuration.Builder();
        org.neo4j.ogm.config.Configuration configuration = builder
                .uri(uri)
                .credentials(username,password)
                .build();

        return new SessionFactory(configuration,"movies.spring.data.neo4j.domain.neo4j");
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
}
