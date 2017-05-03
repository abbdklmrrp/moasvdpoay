package nc.nut.dao.order;

import nc.nut.dao.entity.OperationStatus;
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
    private final static String SELECT_BY_COMP_AND_PLACE_SQL = "SELECT\n" +
            "  ORDERS.ID,\n" +
            "  ORDERS.PRODUCT_ID,\n" +
            "  ORDERS.USER_ID,\n" +
            "  ORDERS.CURRENT_STATUS_ID " +
            "FROM ORDERS\n" +
            "  INNER JOIN USERS ON USERS.ID = ORDERS.USER_ID\n" +
            "  INNER JOIN CUSTOMERS ON USERS.CUSTOMER_ID = CUSTOMERS.ID\n" +
            "  INNER JOIN PLACES ON USERS.PLACE_ID = PLACES.ID\n" +
            "  INNER JOIN OPERATION_STATUS ON ORDERS.CURRENT_STATUS_ID = OPERATION_STATUS.ID\n" +
            "  INNER JOIN PRODUCTS ON PRODUCTS.ID = ORDERS.PRODUCT_ID " +
            "WHERE PLACES.ID = :place_id AND USERS.CUSTOMER_ID = :cust_id\n" +
            "      AND ORDERS.CURRENT_STATUS_ID <> 3 /*deactivated status id*/\n" +
            "      AND PRODUCTS.TYPE_ID = 2 /*service id*/\n";
    private final static String DEACTIVATE_ORDER_OF_USER_FOR_PRODUCT = "UPDATE ORDERS " +
            "SET CURRENT_STATUS_ID = 3 /*deactivated operation status id*/ " +
            "WHERE PRODUCT_ID = :product_id " +
            "      AND USER_ID = :user_id";
    private final static String INSERT_ORDER = "INSERT INTO ORDERS (PRODUCT_ID, USER_ID, CURRENT_STATUS_ID) " +
            "VALUES (:product_id, :user_id, :cur_status_id)";
    private final static String GET_ORDER_ID_BY_USER_ID_AND_PRODUCT_ID_SQL = "SELECT id FROM Orders " +
            "WHERE product_id = :productId " +
            "AND user_id = :userId " +
            "AND current_status_id = 1/*id = 1 - status active*/";
    private final static String GET_ORDER_ID_BY_USER_ID_AND_PRODUCT_NAME_SQL = "SELECT id FROM Orders " +
            "WHERE product_id = (SELECT id FROM Products WHERE name = :productName) " +
            "AND user_id = :userId " +
            "AND current_status_id = 1/*id = 1 - status active*/";
    private final static String DELETE_ORDER_BY_ID_SQL = "DELETE FROM ORDERS WHERE ID = :id;";

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
        paramsForOrder.addValue("cur_status_id", OperationStatus.getIdByStatus(order.getCurrentStatus()));
        return jdbcTemplate.update(INSERT_ORDER, paramsForOrder) > 0;
    }

    @Override
    public boolean delete(Order order) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", order.getId());
        return jdbcTemplate.update(DELETE_ORDER_BY_ID_SQL, params) > 0;
    }

    @Override
    public List<Order> getOrdersByCustomerIdAndPlaceId(long customerId, long placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cust_id", customerId);
        params.addValue("place_id", placeId);
        return jdbcTemplate.query(SELECT_BY_COMP_AND_PLACE_SQL, params, new OrderRowMapper());
    }

    /**
     * Method returns order id according to user id and product id with active status.
     *
     * @param userId    id of user.
     * @param productId id of product.
     * @return id or order.
     */
    @Override
    public Integer getOrderIdByUserIdAndProductId(Integer userId, Integer productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("productId", productId);
        return jdbcTemplate.queryForObject(GET_ORDER_ID_BY_USER_ID_AND_PRODUCT_ID_SQL, params, Integer.class);
    }

    /**
     * Method returns order id according to user id and product name with active status.
     *
     * @param userId      id of user.
     * @param productName name of product.
     * @return id or order.
     */
    @Override
    public Integer getOrderIdByUserIdAndProductName(Integer userId, String productName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("productName", productName);
        return jdbcTemplate.queryForObject(GET_ORDER_ID_BY_USER_ID_AND_PRODUCT_NAME_SQL, params, Integer.class);
    }

    @Override
    public boolean deactivateOrderOfUserForProduct(Integer productId, Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);
        params.addValue("user_id", userId);
        return jdbcTemplate.update(DEACTIVATE_ORDER_OF_USER_FOR_PRODUCT, params) > 0;
    }
}
