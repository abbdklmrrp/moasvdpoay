package nc.nut.dao.user;

import nc.nut.security.Md5PasswordEncoder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rysakova Anna, Moiseienko Petro on 20.04.2017.
 */
@Service
public class UserDAOImpl implements UserDAO {

    private final static String FIND_BY_USERNAME = "SELECT * FROM AUTHORITIES WHERE USERNAME=:username AND ENABLE=1";
    private final static String FIND_ROLE = "SELECT id FROM ROLES WHERE NAME=:name";
    private final static String SAVE_USER = "INSERT INTO USERS(NAME,SURNAME,EMAIL,PHONE,PASSWORD,ADDRESS,ROLE_ID,PLACE_ID,CUSTOMER_ID,ENABLE) " +
            "VALUES(:name,:surname,:email,:phone,:password, :address, :roleId, :placeId, :customerId, :enable)";
    private final static String FIND_PLACE_ID = "SELECT ID FROM PLACES WHERE NAME=:place";
    private final static String FIND_BY_EMAIL = "SELECT EMAIL FROM USERS WHERE EMAIL=:email";
    private final static String FIND_ALL_CLIENTS = "SELECT " +
            "EMAIL, NAME,ID,SURNAME,PHONE,ADDRESS " +
            "FROM USERS " +
            "WHERE ENABLE=1 AND CUSTOMER_ID IS NOT NULL";
    private final static String SELECT_USER_BY_EMAIL_SQL = "SELECT" +
            "  USERS.ID," +
            "  USERS.NAME," +
            "  USERS.SURNAME," +
            "  USERS.EMAIL," +
            "  USERS.PHONE," +
            "  USERS.PASSWORD," +
            "  USERS.ADDRESS," +
            "  USERS.ROLE_ID," +
            "  USERS.PLACE_ID," +
            "  USERS.CUSTOMER_ID," +
            "  USERS.ENABLE" +
            "    FROM USERS" +
            "  WHERE EMAIL=:email";

    private final static String FIND_BY_PHONE = "SELECT * FROM USERS WHERE PHONE=:phone";

    private final static String UPDATE_USER = "UPDATE USERS " +
            "SET NAME=:name, SURNAME=:surname, PHONE=:phone, ENABLE= :enable, PASSWORD= :password, ADDRESS= :address " +
            "WHERE ID=:id";
    private final static String FIND_USER_BY_ID = "SELECT * FROM USERS WHERE ID=:id";
    private final static String SELECT_LIMITED_USERS = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from USERS " +
            " Where ENABLE=1 AND CUSTOMER_ID IS NOT NULL AND " +
            " (name like :pattern " +
            " OR surname like :pattern " +
            " OR email like :pattern " +
            " OR phone like :pattern " +
            " OR address like :pattern )" +
            " " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";

    private static final String SELECT_COUNT = "Select count(ID)\n" +
            "  from Users " +
            "WHERE ENABLE=1 AND CUSTOMER_ID IS NOT NULL AND " +
            " ( name like :pattern " +
            " OR surname like :pattern " +
            " OR email like :pattern " +
            " OR phone like :pattern " +
            " OR address like :pattern )";

    private static final String SELECT_ALL_COUNT = "Select count(ID)\n" +
            "  from Users " +
            "WHERE name like :pattern " +
            " OR surname like :pattern " +
            " OR email like :pattern " +
            " OR phone like :pattern " +
            " OR address like :pattern ";

    private static final String SELECT_ALL_COUNT_OF_CUSTOMER = "Select count(ID)\n" +
            "  from Users " +
            "WHERE CUSTOMER_ID= :custID AND (name like :pattern " +
            " OR surname like :pattern " +
            " OR email like :pattern " +
            " OR phone like :pattern " +
            " OR address like :pattern) ";


    private static final String SELECT_LIMITED_ALL_USERS = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from USERS " +
            " Where name like :pattern " +
            " OR surname like :pattern " +
            " OR email like :pattern " +
            " OR phone like :pattern " +
            " OR address like :pattern " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";
    private static final String SELECT_LIMITED_ALL_USERS_OF_CUSTOMER = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from USERS " +
            " Where customer_id= :custID and (name like :pattern " +
            " OR surname like :pattern " +
            " OR email like :pattern " +
            " OR phone like :pattern " +
            " OR address like :pattern) " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    private Md5PasswordEncoder encoder = new Md5PasswordEncoder();

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
    public User getById(int id) {
        return null;
    }

    /**
     * @param user
     * @return
     * @author Moiseienko Petro
     */
    @Override
    public boolean update(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("surname", user.getSurname());
        params.addValue("enable", user.getEnable());
        params.addValue("phone", user.getPhone());
        params.addValue("password", user.getPassword());
        params.addValue("address", user.getAddress());
//        params.addValue("placeId", user.getPlaceId());
        params.addValue("id", user.getId());
        int rows = jdbcTemplate.update(UPDATE_USER, params);
        return rows > 0;

    }

    /**
     * @param user
     * @return
     * @author Moisienko Petro
     */
    @Override
    public boolean save(User user) {
        if (!this.validateFields(user) || !this.isUnique(user)) {
            return false;
        } else {
            MapSqlParameterSource params = new MapSqlParameterSource();
            String encodePassword = encoder.encode(user.getPassword());
            params.addValue("name", user.getName());
            params.addValue("surname", user.getSurname());
            params.addValue("email", user.getEmail());
            params.addValue("phone", user.getPhone());
            params.addValue("password", encodePassword);
            params.addValue("roleId", user.getRole().getId());
            params.addValue("placeId", user.getPlaceId());
            params.addValue("customerId", user.getCustomerId());
            params.addValue("address", user.getAddress());
            params.addValue("enable", 1);
            int save = jdbcTemplate.update(SAVE_USER, params);
            return save > 0;
        }
    }

    @Override
    public boolean delete(User object) {
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Integer findRole(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        List<Integer> id = jdbcTemplate.query(FIND_ROLE, params, (rs, rowNum) -> {
            return rs.getInt("id");
        });
        return id.isEmpty() ? null : id.get(0);
    }

    @Override
    public Integer findPlaceId(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("place", name);
        List<Integer> id = jdbcTemplate.query(FIND_PLACE_ID, params, (rs, rowNum) -> {
            return rs.getInt("id");
        });
        return id.isEmpty() ? null : id.get(0);
    }

    /**
     * @return
     * @author Moiseienko Petro
     */
    @Override
    public List<User> getAllClients() {
        List<User> clients = jdbcTemplate.query(FIND_ALL_CLIENTS, (rs, rowNum) -> {
            User user = new User();
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setAddress(rs.getString("address"));
            user.setSurname(rs.getString("surname"));
            user.setId(rs.getInt("id"));
            user.setPhone(rs.getString("phone"));
            return user;
        });
        return clients;
    }

    private boolean validateFields(User user) {
        if (user.getSurname().isEmpty()) return false;
        else if (user.getRole().getId() == 0) return false;
        else if (user.getPhone().isEmpty()) return false;
        else if (user.getName().isEmpty()) return false;
        else if (user.getPassword().isEmpty()) return false;
        else if (user.getEmail().isEmpty()) return false;
        else if (user.getAddress().isEmpty()) return false;
        else if (user.getPlaceId() == 0) return false;
        return true;
    }

    private boolean isUnique(User user) {
        String email = user.getEmail();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);
        List<String> users = jdbcTemplate.query(FIND_BY_EMAIL, params, (rs, rowNum) -> {
            return rs.getString("EMAIL");
        });
        return users.isEmpty();
    }

    /**
     * This method finds user by his email
     * created by Yuliya Pedash
     *
     * @param email email of user
     * @return found user
     */
    @Override
    public User findByEmail(String email) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);
        return jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL_SQL, params, new UserRowMapper());
    }

    /**
     * @param phone
     * @return
     * @author Moisienko Petro
     */
    @Override
    public User getUserByPhone(String phone) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("phone", phone);
        List<User> users = jdbcTemplate.query(FIND_BY_PHONE, params, (rs, rowNum) -> {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setId(rs.getInt("id"));
            user.setSurname(rs.getString("surname"));
            user.setEmail(rs.getString("email"));
            return user;
        });
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * @param id
     * @return
     * @author Moiseienko Petro
     */
    @Override
    public User getUserById(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(FIND_USER_BY_ID, params, new UserRowMapper());
    }

    @Override
    public Integer getCountUsersWithSearch(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_COUNT, params, Integer.class);
    }

    @Override
    public List<User> getLimitedQuantityUsers(int start, int length, String sort, String search) {
        int rownum = start + length;
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_USERS, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", rownum);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new UserRowMapper());
    }

    @Override
    public Integer getCountAllUsersWithSearch(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_ALL_COUNT, params, Integer.class);
    }

    @Override
    public List<User> getLimitedQuantityAllUsers(int start, int length, String sort, String search) {
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_ALL_USERS, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new UserRowMapper());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Integer getCountUsersWithSearchOfCustomer(String search, int custID) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        params.addValue("custID", custID);
        return jdbcTemplate.queryForObject(SELECT_ALL_COUNT_OF_CUSTOMER, params, Integer.class);
    }

    @Override
    public List<User> getLimitedQuantityUsersOfCustomer(int start, int length, String sort, String search, int custID) {
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_ALL_USERS_OF_CUSTOMER, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("custID", custID);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new UserRowMapper());
    }
}
