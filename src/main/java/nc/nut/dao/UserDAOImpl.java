package nc.nut.dao;

import nc.nut.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    private static BeanPropertyRowMapper<User> ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

    @Autowired
    Environment env;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        List<User> result = jdbcTemplate.query(env.getProperty("user.getById"), ROW_MAPPER, id);

        return DataAccessUtils.singleResult(result);
    }

    @Override
    public User getByPhone(String phone) {
        List<User> result = jdbcTemplate.query(env.getProperty("user.getByPhone"), ROW_MAPPER, phone);

        return DataAccessUtils.singleResult(result);
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
