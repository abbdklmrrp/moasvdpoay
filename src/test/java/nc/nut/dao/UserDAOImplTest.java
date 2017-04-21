package nc.nut.dao;

import nc.nut.config.DataSourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import resources.spring.SpringTestConfig;


@ContextConfiguration(classes = {
        DataSourceConfig.class, SpringTestConfig.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testGet() throws Exception {
        System.out.println(userDAO.get(1));
    }

    @Test
    public void testGetByPhoneAdmin() throws Exception {
        System.out.println("Admin - " + userDAO.getByPhone("4653"));
    }

    @Test
    public void testGetByPhoneManager() throws Exception {
        System.out.println("Manager - " + userDAO.getByPhone("53423"));
    }

    @Test
    public void testGetByPhoneSupport() throws Exception {
        System.out.println("Support - " + userDAO.getByPhone("4679"));
    }

    @Test
    public void testGetByPhoneUser() throws Exception {
        System.out.println("User - " + userDAO.getByPhone("123732015"));
    }
}