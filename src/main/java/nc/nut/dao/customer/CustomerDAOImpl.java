package nc.nut.dao.customer;

import nc.nut.dao.user.User;
import nc.nut.security.Md5PasswordEncoder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 24.04.2017.
 */
@Service
public class CustomerDAOImpl implements CustomerDAO {
    private final static String FIND_COMPANY_SQL = "SELECT ID " +
            "FROM CUSTOMERS " +
            "WHERE NAME=:name AND SECRET_KEY=:secretKey";
    private final static String SAVE_CUSTOMER_SQL = "INSERT INTO CUSTOMERS(NAME,SECRET_KEY,TYPE_ID) " +
             "VALUES(:name, :secretKey,:typeId)";

    private final static String SELECT_BUSINESS_CUSTOMERS="SELECT NAME FROM CUSTOMERS\n" +
            "WHERE ID=1";
    private final static String SELECT_CUSTOMERS_BY_NAME="SELECT ID FROM CUSTOMERS WHERE NAME=:name";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    private Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    @Override
    public Integer getCustomerId(String name, String secretKey) {
        String password = encoder.encode(secretKey);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        params.addValue("secretKey", password);
        return jdbcTemplate.queryForObject(FIND_COMPANY_SQL,params,Integer.class);
    }

    @Override
    public boolean changeSecretKey(int customerId, String newSecretKey) {
        return false;
    }

    @Override
    public boolean changeInvoice(int customerId, int newInvoice) {
        return false;
    }

    @Override
    public boolean changeName(int customerId, String newName) {
        return false;
    }

    @Override
    public List<User> getAllUsers(int customerId) {
        return null;
    }

    @Override
    public Customer getById(int id) {
        return null;
    }

    @Override
    public boolean update(Customer object) {
        return false;
    }

    @Override
    public boolean save(Customer customer) {
        String name=customer.getName();
        if(!isUnique(name)){
            return false;
        }else{
        String password = encoder.encode(customer.getSecretKey());
        Integer type=customer.getCustomerType().getId();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", customer.getName());
        params.addValue("secretKey", password);
        params.addValue("typeId", type);
        return jdbcTemplate.update(SAVE_CUSTOMER_SQL, params)>0;}
    }

    @Override
    public boolean delete(Customer object) {
        return false;
    }

    @Override
    public List<String> getAllBusinessCustomersName() {
        List<String> customers=jdbcTemplate.query(SELECT_BUSINESS_CUSTOMERS,(rs,rowNum)-> rs.getString("name"));
        return customers;
    }

    private boolean isUnique(String name){
        MapSqlParameterSource params=new MapSqlParameterSource("name",name);
        List<Customer> customers=jdbcTemplate.query(SELECT_CUSTOMERS_BY_NAME,params,(rs,rownum)->{
            Customer customer=new Customer();
            customer.setId(rs.getInt("id"));
            return customer;
        });
         return customers.isEmpty();
    }
}
