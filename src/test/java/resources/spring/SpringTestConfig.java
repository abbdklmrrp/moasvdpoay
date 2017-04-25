package resources.spring;

import nc.nut.user.UserDAO;
import nc.nut.user.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author Sergiy Dyrda
 */

@Configuration
@EnableTransactionManagement
public class SpringTestConfig {

    @Autowired
    private ApplicationContext context;

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl(context.getBean(DataSource.class));
    }
}
