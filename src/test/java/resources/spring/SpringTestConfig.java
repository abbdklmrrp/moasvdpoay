package resources.spring;

import nc.nut.dao.UserDAO;
import nc.nut.dao.UserDAOImpl;
import nc.nut.service.impls.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergiy Dyrda
 */

@Configuration
public class SpringTestConfig {

    @Bean(name = "userService")
    public UserServiceImpl userService() {
        return new UserServiceImpl();
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }

}
