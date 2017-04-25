package resources.spring;

import nc.nut.user.UserDAO;
import nc.nut.user.UserDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Sergiy Dyrda
 */

@Configuration
public class SpringTestConfig {

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
