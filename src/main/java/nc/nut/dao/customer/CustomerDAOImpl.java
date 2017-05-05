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
    private final static String FIND_COMPANY = "SELECT ID,name,SECRET_KEY " +
            "FROM CUSTOMERS " +
            "WHERE NAME=:name AND SECRET_KEY=:secretKey";
    private final static String SAVE_CUSTOMER = "INSERT INTO CUSTOMERS(NAME,SECRET_KEY,TYPE_ID) " +
            "VALUES(:name, :secretKey,:typeId)";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    private Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    @Override
    public Integer getCustomerId(String name, String secretKey) {
        String password = encoder.encode(secretKey);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        params.addValue("secretKey", password);
        List<Customer> customers = jdbcTemplate.query(FIND_COMPANY, params, (rs, rowNum) -> {
            String customerName = rs.getString("NAME");
            String secret = rs.getString("SECRET_KEY");
            Integer id = rs.getInt("ID");
            return new Customer(id, customerName, secret);
        });
        return customers.isEmpty() ? null : customers.get(0).getId();
       // return jdbcTemplate.queryForObject(FIND_COMPANY,params,Integer.class);
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
        String password = encoder.encode(customer.getSecretKey());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", customer.getName());
        params.addValue("secretKey", password);
        switch (customer.getCustomerType()){
            case Business: params.addValue("typeId",1);
            break;
            case Residential:params.addValue("typeId",2);
        }
        return jdbcTemplate.update(SAVE_CUSTOMER, params)>0;
    }

    @Override
    public boolean delete(Customer object) {
        return false;
    }
}
