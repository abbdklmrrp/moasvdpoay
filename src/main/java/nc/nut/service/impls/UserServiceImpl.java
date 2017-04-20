package nc.nut.service.impls;

import nc.nut.service.interf.UserService;
import nc.nut.dao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

/**
 * @author Rysakova Anna
 */
@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByName(String name) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT Users.NAME, USERS.PASSWORD,  AUTHORITIES.ROLE AS authorities FROM USERS, AUTHORITIES WHERE USERS.NAME=:name AND USERS.PHONE=AUTHORITIES.USERNAME", name);
            sqlRowSet.next();
            String userName = sqlRowSet.getString("NAME");
            String password = sqlRowSet.getString("PASSWORD");
            String authorities = sqlRowSet.getString("AUTHORITIES");

            return new User(userName, password, authorities);
    }
}
