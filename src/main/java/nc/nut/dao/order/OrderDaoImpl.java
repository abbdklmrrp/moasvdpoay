package nc.nut.dao.order;

import nc.nut.dto.OrdersRowDTO;
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
    @Resource
    private OrderRowMapper orderRowMapper;
    private final static String SELECT_ORDERS_BY_CUST_ID_SQL = "SELECT\n" +
            "  ORDERS.ID,\n" +
            "  ORDERS.PRODUCT_ID,\n" +
            "  ORDERS.USER_ID,\n" +
            "  ORDERS.CURRENT_STATUS_ID " +
            "FROM ORDERS\n" +
            "  INNER JOIN USERS ON USERS.ID = ORDERS.USER_ID\n" +
            "  INNER JOIN CUSTOMERS ON USERS.CUSTOMER_ID = CUSTOMERS.ID\n" +
            "  INNER JOIN OPERATION_STATUS ON ORDERS.CURRENT_STATUS_ID = OPERATION_STATUS.ID\n" +
            "  INNER JOIN PRODUCTS ON PRODUCTS.ID = ORDERS.PRODUCT_ID " +
            "WHERE USERS.CUSTOMER_ID = :cust_id\n" +
            "      AND ORDERS.CURRENT_STATUS_ID <> 3 /*deactivated status id*/\n" +
            "      AND PRODUCTS.TYPE_ID = 2 /*service id*/\n";
    private final static String DEACTIVATE_ORDER_OF_USER_FOR_PRODUCT_SQL = "UPDATE ORDERS " +
            "SET CURRENT_STATUS_ID = 3 /*deactivated operation status id*/ " +
            "WHERE PRODUCT_ID = :product_id " +
            "      AND USER_ID = :user_id";
    private final static String SUSPEND_ORDER_SQL = "UPDATE ORDERS " +
            "SET CURRENT_STATUS_ID = 2 /*suspended operation status id*/ " +
            "WHERE ID = :id ";
    private final static String INSERT_ORDER = "INSERT INTO ORDERS (PRODUCT_ID, USER_ID, CURRENT_STATUS_ID) " +
            "VALUES (:product_id, :user_id, :cur_status_id)";
    private final static String SELECT_ORDER_ID_BY_USER_ID_AND_PRODUCT_ID_SQL = "SELECT id FROM Orders " +
            "WHERE product_id = :productId " +
            "AND user_id = :userId " +
            "AND current_status_id = 1/* Active */";
    private final static String SELECT_ORDER_ID_BY_USER_ID_AND_PRODUCT_NAME_SQL = "SELECT id FROM Orders " +
            "WHERE product_id IN (SELECT id FROM Products WHERE name = :productName) " +
            "AND user_id = :userId " +
            "AND current_status_id = 1/* Active */";
    private final static String DELETE_ORDER_BY_ID_SQL = "DELETE FROM ORDERS WHERE ID = :id;";
    private final static String SELECT_NOT_DIACTIVATED_ORDER_BY_USER_AND_PRODUCT_SQL = "SELECT * FROM ORDERS WHERE\n" +
            "  PRODUCT_ID = :product_id\n" +
            "  AND USER_ID = :user_id\n " +
            "AND CURRENT_STATUS_ID <> 3 /*Deactivated status*/";
    private final static String SELECT_ORDERS_DTO_BY_CUSTOMER_ID_SQL = "SELECT\n" +
            "   o.id,\n" +
            "   p.NAME,\n" +
            "   p.TYPE_ID,\n" +
            "   p.DURATION,\n" +
            "   p.DESCRIPTION,\n" +
            "   op_his.OPERATION_DATE,\n" +
            "   o.CURRENT_STATUS_ID\n" +
            "FROM ORDERS o\n" +
            "   JOIN (SELECT\n" +
            "            OPERATION_DATE,\n" +
            "            ORDER_ID\n" +
            "         FROM OPERATIONS_HISTORY\n" +
            "         WHERE ID IN (SELECT MIN(ID)\n" +
            "                      FROM OPERATIONS_HISTORY\n" +
            "                      GROUP BY ORDER_ID)) op_his ON op_his.ORDER_ID = o.ID\n" +
            "   JOIN PRODUCTS p ON p.ID = o.PRODUCT_ID\n" +
            "   JOIN USERS u ON u.ID = o.USER_ID\n" +
            "WHERE o.CURRENT_STATUS_ID <> 3 AND u.CUSTOMER_ID =:cust_id";

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
        paramsForOrder.addValue("cur_status_id", order.getCurrentStatus().getId());
        return jdbcTemplate.update(INSERT_ORDER, paramsForOrder) > 0;
    }

    @Override
    public boolean delete(Order order) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", order.getId());
        return jdbcTemplate.update(DELETE_ORDER_BY_ID_SQL, params) > 0;
    }

    public List<Order> getOrdersByCustomerId(Integer customerId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cust_id", customerId);
        return jdbcTemplate.query(SELECT_ORDERS_BY_CUST_ID_SQL, params, new OrderRowMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getOrderIdByUserIdAndProductId(Integer userId, Integer productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("productId", productId);
        return jdbcTemplate.queryForObject(SELECT_ORDER_ID_BY_USER_ID_AND_PRODUCT_ID_SQL, params, Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getOrderIdByUserIdAndProductName(Integer userId, String productName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("productName", productName);
        return jdbcTemplate.queryForObject(SELECT_ORDER_ID_BY_USER_ID_AND_PRODUCT_NAME_SQL, params, Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deactivateOrderOfUserForProduct(Integer productId, Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);
        params.addValue("user_id", userId);
        return jdbcTemplate.update(DEACTIVATE_ORDER_OF_USER_FOR_PRODUCT_SQL, params) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order getNotDeactivatedOrderByUserAndProduct(Integer userId, Integer productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        params.addValue("product_id", productId);
        return jdbcTemplate.queryForObject(SELECT_NOT_DIACTIVATED_ORDER_BY_USER_AND_PRODUCT_SQL, params, orderRowMapper);
    }

//    @Override
//    public Calendar getEndDateOfOrderActive(Integer orderId) {
//        return null;
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrdersRowDTO> getOrderRowsBDTOByCustomerId(Integer customerId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cust_id", customerId);
        return jdbcTemplate.query(SELECT_ORDERS_DTO_BY_CUSTOMER_ID_SQL, params, new OrdersRowDTORowMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean suspendOrder(Integer orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderId);
        return jdbcTemplate.update(SUSPEND_ORDER_SQL, params) > 0;

    }
}
