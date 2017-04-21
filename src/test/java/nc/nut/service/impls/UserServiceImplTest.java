package nc.nut.service.impls;

import nc.nut.config.DataSourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import resources.spring.SpringTestConfig;

@ContextConfiguration(classes = {
        DataSourceConfig.class, SpringTestConfig.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserDetailsService userService;

    @Test
    public void testLoadUserByUsernameAdmin() throws Exception {
        System.out.println("Admin - " + userService.loadUserByUsername("4653"));
    }


    @Test
    public void testLoadUserByUsernameManager() throws Exception {
        System.out.println("Manager - " + userService.loadUserByUsername("53423"));
    }


    @Test
    public void testLoadUserByUsernameSupport() throws Exception {
        System.out.println("Support - " + userService.loadUserByUsername("4679"));
    }


    @Test
    public void testLoadUserByUsernameUser() throws Exception {
        System.out.println("User - " + userService.loadUserByUsername("123732015"));

    }
}