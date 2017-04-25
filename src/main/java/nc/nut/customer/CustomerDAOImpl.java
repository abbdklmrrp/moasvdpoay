package nc.nut.customer;

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
    private final static String FIND_COMPANY = "SELECT * FROM CUSTOMERS WHERE NAME=:customerName AND SECRET_KEY=:secretKey";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    private Md5PasswordEncoder encoder=new Md5PasswordEncoder();

    @Override
    public Customer checkCustomer(String name, String secretKey) {
        String password=encoder.encode(secretKey);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerName", name);
        params.addValue("secretKey", password);
        List<Customer> customers = jdbcTemplate.query(FIND_COMPANY, params, (rs, rowNum) -> {
            String customerName=rs.getString("NAME");
            String secret=rs.getString("SECRET_KEY");
            int id=rs.getInt("ID");
            return new Customer(id,customerName,secret);
        });
        return customers.isEmpty()? null:customers.get(0);
    }
}
