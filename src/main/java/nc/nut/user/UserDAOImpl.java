package nc.nut.user;

import nc.nut.persistence.BaseDAOImpl;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Anna on 20.04.2017.
 */
@Service
class UserDAOImpl extends BaseDAOImpl implements UserDAO {
    private PreparedStatementCreatorFactory findStatement =
            create("select * from AUTHORITIES WHERE USERNAME=?", Types.VARCHAR);

    @Override
    public User findByName(String name) {
        List<User> users = jdbcTemplate.query(findStatement.newPreparedStatementCreator(new Object[]{name}), new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                String userName = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String authorities = rs.getString("ROLE");
                return new User(userName, password, authorities);
            }
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
