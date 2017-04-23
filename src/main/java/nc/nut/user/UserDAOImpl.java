package nc.nut.user;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna on 20.04.2017.
 */
@Service
class UserDAOImpl implements UserDAO {

    private final static String FIND_BY_USERNAME = "SELECT * FROM AUTHORITIES WHERE USERNAME=:username";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User findByUsername(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        List<User> users = jdbcTemplate.query(FIND_BY_USERNAME, params, (rs, rowNum) -> {
            String userName = rs.getString("USERNAME");
            String password = rs.getString("PASSWORD");
            String authorities = rs.getString("ROLE");
            return new User(userName, password, authorities);
        });
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
