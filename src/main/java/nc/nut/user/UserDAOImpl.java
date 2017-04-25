package nc.nut.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

import static nc.nut.util.PropertiesUtil.loadProperties;
import static nc.nut.util.UserUtil.transferEnable;

/**
 * @author Rysakova Anna
 * @author Dyrda Sergiy
 */
@Repository
public class UserDAOImpl implements UserDAO {

    public static final String QUERIES_LOCATION = "sql/userdao.properties";

    public static final RowMapper<User> USER_ROW_MAPPER = new UserRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertUser;

    private Properties sqlQueries;

    @Autowired
    public UserDAOImpl(DataSource dataSource) {
        this.sqlQueries = loadProperties(QUERIES_LOCATION);
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("ID");
    }


    @Override
    public User findByEmail(String username) {
        List<User> users = jdbcTemplate.query(sqlQueries.getProperty("user.getByEmail"), (rs, rowNum) -> {
            String userName = rs.getString("USERNAME");
            String password = rs.getString("PASSWORD");
            String authorities = rs.getString("ROLE");
            return new User(userName, password, authorities);
        }, username);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("ID", user.getId())
                .addValue("NAME", user.getName())
                .addValue("SURNAME", user.getSurname())
                .addValue("EMAIL", user.getEmail())
                .addValue("PHONE", user.getPhone())
                .addValue("PASSWORD", user.getPassword())
                .addValue("ADDRESS", user.getAddress())
                .addValue("ENABLE", transferEnable(user.isEnable()))
                .addValue("ROLE_ID", getRoleId(user.getRole()))
                .addValue("PLACE_ID", user.getPlace_id())
                .addValue("CUSTOMER_ID", user.getCustomer_id());

        if (user.getId() == null) {
            Number key = insertUser.executeAndReturnKey(map);
            user.setId(key.intValue());
        } else {
            namedParameterJdbcTemplate.update(sqlQueries.getProperty("user.update"), map);
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update(sqlQueries.getProperty("user.delete"), id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> result = jdbcTemplate.query(sqlQueries.getProperty("user.getById"), USER_ROW_MAPPER, id);

        return DataAccessUtils.singleResult(result);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(sqlQueries.getProperty("user.getAll"), USER_ROW_MAPPER);
    }

    private int getRoleId(Role role) {
        return jdbcTemplate.queryForObject(sqlQueries.getProperty("role.id"), Integer.class, role.name());
    }
}
