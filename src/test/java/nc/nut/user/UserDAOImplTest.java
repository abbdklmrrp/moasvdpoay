package nc.nut.user;

import nc.nut.config.PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
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
    public void testFindByUsernamePMG() throws Exception {
        User user = userDAO.findByEmail("pmg");
        System.out.println(user);
    }

    @Test
    public void testGetById() throws Exception {
        User user = userDAO.get(1);
        System.out.println(user);
    }

    @Test
    @Transactional
    public void testSaveNew() throws Exception {
        User newUser = new User();
        newUser.setName("newName");
        newUser.setSurname("newSurname");
        newUser.setEmail("newEmail");
        newUser.setPhone("newPhone");
        newUser.setPassword("newPass");
        newUser.setAddress("newAddress");
        newUser.setRole(Role.USER);

        newUser = userDAO.save(newUser);
        System.out.println(userDAO.get(newUser.getId()));
    }


    @Test
    @Transactional
    public void testSaveExisted() throws Exception {
        User newUser = new User();
        newUser.setName("newName");
        newUser.setSurname("newSurname");
        newUser.setEmail("newEmail");
        newUser.setPhone("newPhone");
        newUser.setPassword("newPass");
        newUser.setAddress("newAddress");
        newUser.setRole(Role.USER);

        userDAO.save(newUser);
        System.out.println(userDAO.get(newUser.getId()));

        newUser.setName("UPDATES new name");
        userDAO.save(newUser);
        System.out.println(userDAO.get(newUser.getId()));
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        User newUser = new User();
        newUser.setName("newName");
        newUser.setSurname("newSurname");
        newUser.setEmail("newEmail");
        newUser.setPhone("newPhone");
        newUser.setPassword("newPass");
        newUser.setAddress("newAddress");
        newUser.setRole(Role.USER);

        userDAO.save(newUser);
        System.out.println(userDAO.get(newUser.getId()));

        boolean deleted = userDAO.delete(newUser.getId());
        System.out.println("deleted - " + deleted);

        System.out.println(userDAO.get(newUser.getId()));
    }

    @Test
    public void testGetAll() throws Exception {
        userDAO.getAll().forEach(System.out::println);
    }
}