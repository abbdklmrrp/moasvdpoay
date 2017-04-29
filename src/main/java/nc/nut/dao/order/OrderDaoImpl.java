package nc.nut.dao.order;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
@Service
public class OrderDaoImpl implements OrderDao {
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    //todo go from company
    private final static String SELECT_BY_COMP_AND_PLACE ="SELECT\n" +
            "  ORDERS.ID,\n" +
            "  ORDERS.PRODUCT_ID,\n" +
            "  ORDERS.USER_ID,\n" +
            "  ORDERS.CURRENT_STATUS_ID "+
            "FROM ORDERS\n" +
            "  INNER JOIN USERS ON USERS.ID = ORDERS.USER_ID\n" +
            "  INNER JOIN CUSTOMERS ON USERS.CUSTOMER_ID = CUSTOMERS.ID\n" +
            "  INNER JOIN PLACES ON USERS.PLACE_ID = PLACES.ID\n" +
            "  INNER JOIN OPERATION_STATUS ON ORDERS.CURRENT_STATUS_ID = OPERATION_STATUS.ID\n" +
            "  INNER JOIN PRODUCTS ON PRODUCTS.ID = ORDERS.PRODUCT_ID " +
            "WHERE PLACES.ID = :place_id AND USERS.CUSTOMER_ID = :cust_id\n" +
            "      AND ORDERS.CURRENT_STATUS_ID <> 3 /*deactivated status id*/\n" +
            "      AND PRODUCTS.TYPE_ID = 2 /*service id*/\n";
    private final static String INSERT_ORDER = "INSERT INTO ORDERS (PRODUCT_ID, USER_ID, CURRENT_STATUS_ID) " +
            "VALUES (:product_id, :user_id, :cur_status_id)";


    @Override
    public Order getById(int id) {
        return null;
    }

    @Override
    public boolean update(Order object) {
        return false;
    }

    @Override
    public boolean save(Order order) {
        MapSqlParameterSource paramsForOrder = new MapSqlParameterSource();
        MapSqlParameterSource paramsForStatus = new MapSqlParameterSource();
        paramsForOrder.addValue("product_id", order.getProductId());
        paramsForOrder.addValue("user_id", order.getUserId());
        paramsForOrder.addValue("cur_status_id", order.getCurrentStatus());
        return jdbcTemplate.update(INSERT_ORDER, paramsForOrder) > 0;
    }

    @Override
    public boolean delete(Order object) {
        return false;
    }

    @Override
    public List<Order> getOrdersByCustomerIdAndPlaceId(long customerId, long placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cust_id", customerId);
        params.addValue("place_id", placeId);
        return jdbcTemplate.query(SELECT_BY_COMP_AND_PLACE, params, new OrderRowMapper());
    }
}
