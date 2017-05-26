package jtelecom.dao.user;

import jtelecom.dao.entity.CustomerType;
import jtelecom.security.Md5PasswordEncoder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Anna Rysakova
 * @author Petr Moiseienko
 */
@Service
public class UserDAOImpl implements UserDAO {

    private final static String SELECT_BY_USERNAME = "SELECT * FROM AUTHORITIES WHERE USERNAME=:username AND ENABLE=1";
    private final static String SELECT_ROLE = "SELECT id FROM ROLES WHERE NAME=:name";
    private final static String SAVE_USER = "INSERT INTO USERS(NAME,SURNAME,EMAIL,PHONE,PASSWORD,ADDRESS,ROLE_ID,PLACE_ID,CUSTOMER_ID,ENABLE) " +
            "VALUES(:name,:surname,:email,:phone,:password, :address, :roleId, :placeId, :customerId, :enable)";
    private final static String SELECT_PLACE_ID = "SELECT ID FROM PLACES WHERE NAME=:place";
    private final static String SELECT_BY_EMAIL = "SELECT EMAIL FROM USERS WHERE upper(EMAIL)=upper(:email)";
    private final static String SELECT_ALL_CLIENTS = "SELECT " +
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

    private final static String SELECT_BY_PHONE = "SELECT * FROM USERS WHERE PHONE=:phone";

    private final static String UPDATE_USER = "UPDATE USERS " +
            "SET NAME=:name, SURNAME=:surname, PHONE=:phone, ENABLE= :enable, PASSWORD= :password, ADDRESS= :address " +
            "WHERE ID=:id";
    private final static String SELECT_USER_BY_ID = "SELECT * FROM USERS WHERE ID=:id";
    private final static String SELECT_LIMITED_USERS = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from USERS " +
            " Where ENABLE=1 AND CUSTOMER_ID IS NOT NULL AND " +
            " (upper(name) like upper(:pattern) " +
            " OR upper(surname) like upper(:pattern) " +
            " OR upper(email) like upper(:pattern) " +
            " OR upper(phone) like upper(:pattern) " +
            " OR upper(address) like upper(:pattern) )" +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";

    private static final String SELECT_COUNT = "Select count(ID)\n" +
            "  from Users " +
            "WHERE ENABLE=1 AND CUSTOMER_ID IS NOT NULL AND " +
            " ( upper(name) like upper(:pattern) " +
            " OR upper(surname) like upper(:pattern) " +
            " OR upper(email) like upper(:pattern) " +
            " OR upper(phone) like upper(:pattern) " +
            " OR upper(address) like upper(:pattern) )";

    private static final String SELECT_ALL_COUNT = "Select count(ID)\n" +
            "  from Users " +
            "WHERE upper(name) like upper(:pattern) " +
            " OR upper(surname) like upper(:pattern) " +
            " OR upper(email) like upper(:pattern) " +
            " OR upper(phone) like upper(:pattern) " +
            " OR upper(address) like upper(:pattern) ";

    private static final String SELECT_ALL_COUNT_OF_CUSTOMER = "Select count(ID)\n" +
            "  from Users " +
            "WHERE CUSTOMER_ID= :custID AND ( " +
            " upper(name) like upper(:pattern) " +
            " OR upper(surname) like upper(:pattern) " +
            " OR upper(email) like upper(:pattern) " +
            " OR upper(phone) like upper(:pattern) " +
            " OR upper(address) like upper(:pattern)) ";


    private static final String SELECT_LIMITED_ALL_USERS = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from USERS " +
            " Where upper(name) like upper(:pattern) " +
            " OR upper(surname) like upper(:pattern) " +
            " OR upper(email) like upper(:pattern) " +
            " OR upper(phone) like upper(:pattern) " +
            " OR upper(address) like upper(:pattern) " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";
    private static final String SELECT_LIMITED_ALL_USERS_OF_CUSTOMER = "select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from USERS " +
            " Where customer_id= :custID and ( " +
            " upper(name) like upper(:pattern) " +
            " OR upper(surname) like upper(:pattern) " +
            " OR upper(email) like upper(:pattern) " +
            " OR upper(phone) like upper(:pattern) " +
            " OR upper(address) like upper(:pattern)) " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";

    private static final String SELECT_COUNT_EMPLOYEES_BY_CUSTOMER = "Select count(ID)\n" +
            "  from Users " +
            "WHERE ROLE_ID=6 AND CUSTOMER_ID=:customerId AND " +
            " ( upper(name) like upper(:pattern) " +
            " OR upper(surname) like upper(:pattern) " +
            " OR upper(email) like upper(:pattern) " +
            " OR upper(phone) like upper(:pattern) " +
            " OR upper(address) like upper(:pattern) )";

    private static final String SELECT_EMPLOYEES_BY_CUSTOMER = "select *" +
            "from ( select a.*, rownum rnum\n" +
            "       from ( Select * from USERS " +
            "WHERE ROLE_ID=6 AND CUSTOMER_ID=:customerId AND " +
            "  (upper(name) like upper(:pattern) " +
            " OR upper(surname) like upper(:pattern) " +
            " OR upper(email) like upper(:pattern) " +
            " OR upper(phone) like upper(:pattern) " +
            " OR upper(address) like upper(:pattern)) " +
            " ORDER BY %s) a\n" +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";

    private static final String SELECT_USER_BY_COMPLAINT_ID = "SELECT * " +
            " FROM USERS WHERE ID=( " +
            " SELECT USER_ID FROM ORDERS JOIN COMPLAINTS ON (ORDERS.ID=COMPLAINTS.ORDER_ID) " +
            " WHERE COMPLAINTS.ID=:complaintId)";

    private static final String SELECT_USER_BY_ORDER_ID = "SELECT * " +
            " FROM USERS WHERE ID=( " +
            " SELECT USER_ID FROM ORDERS WHERE ID=:orderId)";

    private static final String SELECT_USERS_BY_CUSTOMER_TYPE = "SELECT * " +
            " FROM USERS WHERE CUSTOMER_ID IN ( " +
            " SELECT ID FROM CUSTOMERS WHERE TYPE_ID=:typeId)";

    private static final String SELECT_USER_BY_PRODUCT_ID = "SELECT * " +
            " FROM USERS WHERE ID IN ( " +
            " SELECT USER_ID FROM ORDERS WHERE PRODUCT_ID=:productId OR PRODUCT_ID IN ( " +
            " SELECT TARIFF_ID FROM TARIFF_SERVICES WHERE SERVICE_ID=:productId))";

    private static final String UPDATE_ENABLE_OR_DISABLE = "UPDATE USERS " +
            " SET ENABLE=:status " +
            " WHERE ID=:userId";

    private static final String UPDATE_PASSWORD="UPDATE USERS " +
            " SET PASSWORD=:password " +
            " WHERE ID=:userId";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    private Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    @Override
    public User findByUsername(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        List<User> users = jdbcTemplate.query(SELECT_BY_USERNAME, params, (rs, rowNum) -> {
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
        params.addValue("enable", user.getStatus().getId());
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
        List<Integer> id = jdbcTemplate.query(SELECT_ROLE, params, (rs, rowNum) -> {
            return rs.getInt("id");
        });
        return id.isEmpty() ? null : id.get(0);
    }

    @Override
    public Integer findPlaceId(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("place", name);
        List<Integer> id = jdbcTemplate.query(SELECT_PLACE_ID, params, (rs, rowNum) -> {
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
        List<User> clients = jdbcTemplate.query(SELECT_ALL_CLIENTS, (rs, rowNum) -> {
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


    public boolean isUnique(User user) {
        String email = user.getEmail();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);
        List<String> users = jdbcTemplate.query(SELECT_BY_EMAIL, params, (rs, rowNum) -> {
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
        List<User> users = jdbcTemplate.query(SELECT_BY_PHONE, params, (rs, rowNum) -> {
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
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID, params, new UserRowMapper());
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

    @Override
    public List<User> getLimitedQuantityEmployeesOfCustomer(int start, int length, String sort, String search, int customerId) {
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_EMPLOYEES_BY_CUSTOMER, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new UserRowMapper());
    }

    @Override
    public Integer getCountEmployeesWithSearchOfCustomer(String search, int customerId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        params.addValue("customerId", customerId);
        return jdbcTemplate.queryForObject(SELECT_COUNT_EMPLOYEES_BY_CUSTOMER, params, Integer.class);
    }

    @Override
    public User getUserByComplaintId(int complaintId) {
        MapSqlParameterSource params = new MapSqlParameterSource("complaintId", complaintId);
        return jdbcTemplate.queryForObject(SELECT_USER_BY_COMPLAINT_ID, params, new UserRowMapper());
    }

    @Override
    public User getUserByOrderId(int orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource("orderId", orderId);
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ORDER_ID, params, new UserRowMapper());
    }

    @Override
    public List<User> getUsersByCustomerType(CustomerType customerType) {
        Integer typeId = customerType.getId();
        MapSqlParameterSource params = new MapSqlParameterSource("typeId", typeId);
        return jdbcTemplate.query(SELECT_USERS_BY_CUSTOMER_TYPE, params, new UserRowMapper());
    }

    @Override
    public List<User> getUsersByProductId(int productId) {
        MapSqlParameterSource params = new MapSqlParameterSource("productId", productId);
        return jdbcTemplate.query(SELECT_USER_BY_PRODUCT_ID, params, new UserRowMapper());
    }

    @Override
    public boolean enableDisableUser(User user) {
        Integer status = user.getStatus().getId();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        params.addValue("status", status);
        return jdbcTemplate.update(UPDATE_ENABLE_OR_DISABLE, params) > 0;
    }

    @Override
    public boolean updatePassword(User user) {
        String password=encoder.encode(user.getPassword());
        MapSqlParameterSource params=new MapSqlParameterSource();
        params.addValue("password",password);
        params.addValue("userId",user.getId());
        return jdbcTemplate.update(UPDATE_PASSWORD,params)>0;
    }
}
