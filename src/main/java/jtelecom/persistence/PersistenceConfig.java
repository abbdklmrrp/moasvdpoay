package jtelecom.persistence;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by Rysakova Anna on 20.04.2017.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db/oracle.properties")
//@PropertySource("classpath:ANN_DB.properties")
public class PersistenceConfig {
    @Value("${datasource.driver-class-name}")
    private String driver;
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;


    @Bean(name = "dataSource")
    public DataSource dataSource() {
//        for glassfish connection pool
//        DataSource dataSource= null;
//        try {
//
//            InitialContext initContext = new InitialContext();
//            dataSource = (DataSource) initContext.lookup("jdbc/ConnectionPool");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }


        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(10);
        dataSource.setMaxIdle(15);

        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        return transactionManager;
    }
}
