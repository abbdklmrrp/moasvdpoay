import nc.nut.config.ServicesConfig;
import nc.nut.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(classes = {
        ServicesConfig.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class DataSourceTest {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDataSource() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT Users.NAME, USERS.PASSWORD,  AUTHORITIES.ROLE AS authorities FROM USERS, AUTHORITIES WHERE USERS.PHONE=AUTHORITIES.USERNAME");
        sqlRowSet.next();
        String name = sqlRowSet.getString("NAME");
        String password = sqlRowSet.getString("PASSWORD");
        String authorities = sqlRowSet.getString("AUTHORITIES");
        User user = new User(name, password, authorities);
        System.out.println(user);
    }
}
