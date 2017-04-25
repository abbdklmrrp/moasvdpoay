package nc.nut.user;

import nc.nut.persistence.PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import resources.spring.SpringTestConfig;


@ContextConfiguration(classes = {
        PersistenceConfig.class,
        SpringTestConfig.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testFindByUsername() throws Exception {
        User user = userDAO.findByUsername("email@manager.com");
        System.out.println(user);
    }

}