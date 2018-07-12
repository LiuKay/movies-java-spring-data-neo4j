package movies.spring.data.neo4j;

import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by kay on 2018/7/12.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"movies.spring.data.neo4j.repositories.mysql"},
    entityManagerFactoryRef = "springTransactionManager",
    transactionManagerRef = "springTransactionManager"
)
public class JpaConfig {

    private static final String DATABASE_DRIVER = "db.driver";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER = "db.user";
    private static final String DATABASE_PASSWORD = "db.password";
    private static final String PACKAGES_TO_SCAN = "packages.to.scan";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show.sql";


    @Bean
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource dataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setDriverClassName(env.getRequiredProperty(DATABASE_DRIVER));
        source.setUrl(env.getRequiredProperty(DATABASE_URL));
        source.setUsername(env.getRequiredProperty(DATABASE_USER));
        source.setPassword(env.getRequiredProperty(DATABASE_PASSWORD));
        return source;
    }

    @Bean(name = "springEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean springEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan(env.getRequiredProperty(PACKAGES_TO_SCAN).split(","));
        factory.setJpaProperties(hibernateProperties());
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean(name = "springTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(springEntityManagerFactory().getObject());
        return manager;
    }


    @Resource
    protected Environment env;

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    protected Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
        properties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
        return properties;
    }


}
