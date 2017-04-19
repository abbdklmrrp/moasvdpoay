package nc.nut.dao;

import nc.nut.config.ServicesConfig;
import nc.nut.service.interf.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(classes = {
        ServicesConfig.class
})
@RunWith(SpringJUnit4ClassRunner.class)

public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void testFindByName() throws Exception {
        System.out.println("admin - " + userService.findByName("admin"));
        System.out.println("manager - " + userService.findByName("manager"));
        System.out.println("support - " + userService.findByName("support"));
        System.out.println("user - " + userService.findByName("user"));
    }

}