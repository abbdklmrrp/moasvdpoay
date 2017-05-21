package jtelecom.dao.order;

import jtelecom.dao.entity.CustomerType;
import jtelecom.dao.entity.OperationStatus;
import jtelecom.dao.product.ProductType;
import jtelecom.dto.FullInfoOrderDTO;
import jtelecom.dto.OrdersRowDTO;
import jtelecom.util.querybuilders.LimitedProductsQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
@Service
public class OrderDaoImpl implements OrderDao {

    private static Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

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
            "      AND USER_ID = :user_id AND CURRENT_STATUS_ID <> 3 /*deactivated operation status id*/";
    private final static String SUSPEND_ORDER_SQL = "UPDATE ORDERS " +
            "SET CURRENT_STATUS_ID = 2 /*suspended operation status id*/ " +
            "WHERE ID = :id ";
    private final static String ACTIVATE_ORDER_SQL = "UPDATE ORDERS " +
            "SET CURRENT_STATUS_ID = 1 /*activated operation status id*/ " +
            "WHERE ID = :id ";
    private final static String INSERT_ORDER = "INSERT INTO ORDERS (PRODUCT_ID, USER_ID, CURRENT_STATUS_ID) " +
            "VALUES (:product_id, :user_id, :cur_status_id)";
    private final static String SELECT_ORDER_ID_BY_USER_ID_AND_PRODUCT_ID_SQL = "SELECT Orders.id " +
            " FROM Orders " +
            " JOIN Users ON Orders.user_id = Users.id " +
            " WHERE Orders.current_status_id <>3 " +
            " AND Orders.product_id = :productId " +
            " AND Users.customer_id = (SELECT customer_id FROM Users WHERE id = :userId)";
    private final static String DELETE_ORDER_BY_ID_SQL = "DELETE FROM ORDERS WHERE ID = :id;";
    private final static String SELECT_NOT_DIACTIVATED_ORDER_BY_USER_AND_PRODUCT_SQL = "SELECT * FROM ORDERS WHERE\n" +
            "  PRODUCT_ID = :product_id\n" +
            "  AND USER_ID = :user_id\n " +
            "AND CURRENT_STATUS_ID <> 3 /*Deactivated status*/";
//    private final static String SELECT_ORDERS_DTO_BY_CUSTOMER_ID_SQL = "SELECT\n" +
//            "   o.id,\n" +
//            "   p.NAME,\n" +
//            "   p.TYPE_ID,\n" +
//            "   p.DURATION,\n" +
//            "   op_his.OPERATION_DATE,\n" +
//            "   o.CURRENT_STATUS_ID, \n" +
//            " p.id product_id " +
//            "FROM ORDERS o\n" +
//            "   JOIN (SELECT\n" +
//            "            OPERATION_DATE,\n" +
//            "            ORDER_ID\n" +
//            "         FROM OPERATIONS_HISTORY\n" +
//            "         WHERE ID IN (SELECT MIN(ID)\n" +
//            "                      FROM OPERATIONS_HISTORY\n" +
//            "                      GROUP BY ORDER_ID)) op_his ON op_his.ORDER_ID = o.ID\n" +
//            "   JOIN PRODUCTS p ON p.ID = o.PRODUCT_ID\n" +
//            "   JOIN USERS u ON u.ID = o.USER_ID\n" +
//            "WHERE o.CURRENT_STATUS_ID <> 3 AND u.CUSTOMER_ID =:cust_id";

    private static final String SELECT_INTERVAL_ORDERS_BY_USER_ID = "SELECT * FROM(" +
            "Select id , product_name, description, product_type, current_status_id, ROW_NUMBER() OVER (ORDER BY %s) R " +
            " FROM \n" +
            " (Select orders.id id, " +
            " products.name product_name, \n" +
            " products.DESCRIPTION description, \n" +
            " products.type_id product_type, \n" +
            "  orders.CURRENT_STATUS_ID current_status_id \n" +
            " from orders join products on (orders.PRODUCT_ID=products.id) join (SELECT \n" +
            "       OPERATION_DATE, \n" +
            "            ORDER_ID \n" +
            "         FROM OPERATIONS_HISTORY \n" +
            "         WHERE ID IN (SELECT MIN(ID) \n" +
            "                      FROM OPERATIONS_HISTORY \n" +
            "                      GROUP BY ORDER_ID)) op_his ON op_his.ORDER_ID = orders.ID \n" +
            " where USER_ID in (select id from users where customer_id= " +
            "(select customer_id from users where id=:userId)) and orders.CURRENT_STATUS_ID<>3)) \n" +
            " where R>:start and R<=:length and upper(product_name) like upper(:pattern) ";

//    private static final String SELECT_COUNT_ORDERS_BY_USER_ID = "Select COUNT(ROWNUM) COUNT \n" +
//            " where R>:start and R<=:length and name like :pattern ";
    private final String SELECT_LIMITED_ORDERS_DTO_BY_CUSTOMER_ID_SQL = "SELECT\n" +
            "  o.id           order_id,\n" +
            "  p.name,\n" +
            "  p.id           product_id,\n" +
            "  p.type_id      product_type,\n" +
            "  op_status.name operation_status,\n" +
            "  pt.action_date end_date,\n" +
            "  rownum\n " +
            "FROM ORDERS o\n" +
            "  JOIN PRODUCTS p ON o.PRODUCT_ID = p.ID\n" +
            "  JOIN USERS u ON u.id = o.USER_ID\n" +
            "  JOIN OPERATION_STATUS op_status ON o.CURRENT_STATUS_ID = op_status.id\n" +
            "  LEFT JOIN  PLANNED_TASKS pt ON o.ID = pt.ORDER_ID " +
            "WHERE o.CURRENT_STATUS_ID <> 3 /*Deactivation*/ AND u.CUSTOMER_ID = :cust_id\n " +
            "       AND pt.STATUS_ID = 3 /*Deactivation*/ AND rownum <= :length AND rownum > :start";
    private final String SELECT_COUNT_ORDERS_DTO_BY_CUSTOMER_ID_SQL = "SELECT COUNT(*) " +
            "FROM ORDERS o\n" +
            "  JOIN USERS u ON u.id = o.USER_ID\n" +
            "  LEFT JOIN  PLANNED_TASKS pt ON o.ID = pt.ORDER_ID\n" +
            "WHERE o.CURRENT_STATUS_ID <> 3 /*Deactivation*/ AND u.CUSTOMER_ID = :cust_id\n" +
            "       AND pt.STATUS_ID = 3 /*Deactivation*/";
    private static final String SELECT_COUNT_ORDERS_BY_USER_ID="Select COUNT(ROWNUM) COUNT \n" +
            " from orders join products on (orders.PRODUCT_ID=products.id) join (SELECT \n" +
            "       OPERATION_DATE, \n" +
            "            ORDER_ID \n" +
            "         FROM OPERATIONS_HISTORY \n" +
            "         WHERE ID IN (SELECT MIN(ID) \n" +
            "                      FROM OPERATIONS_HISTORY \n" +
            "                      GROUP BY ORDER_ID)) op_his ON op_his.ORDER_ID = orders.ID \n" +
            " where USER_ID in (select id from users where customer_id= " +
            "(select customer_id from users where id=:userId)) and orders.CURRENT_STATUS_ID<>3 AND upper(PRODUCTS.NAME) LIKE upper(:pattern)";

    private static final String SELECT_ALL_ORDERS_WITHOUT_CSR = "SELECT * FROM ( \n" +
            " SELECT product_name,product_type,customer_type,order_id, " +
            " TO_CHAR(operation_date,'YYYY-MM-DD') operation_date,place, \n" +
            "  ROW_NUMBER() OVER (ORDER BY %s) R FROM  (\n" +
            " SELECT  PRODUCTS.name product_name,PRODUCTS.TYPE_ID product_type, products.CUSTOMER_TYPE_ID customer_type, \n" +
            "  orders.id order_id,a.OPERATION_DATE operation_date, PLACES. NAME place \n" +
            " FROM ORDERS JOIN \n" +
            "  (SELECT * FROM OPERATIONS_HISTORY WHERE STATUS_ID=4) a ON (ORDERS.id=a.ORDER_ID) \n" +
            "  JOIN PRODUCTS ON (ORDERS.PRODUCT_ID=PRODUCTS.id) \n" +
            "  JOIN USERS ON (users.id=orders.USER_ID) JOIN PLACES ON (users.PLACE_ID=PLACES.ID) \n" +
            " WHERE orders.CURRENT_STATUS_ID=4 AND orders.csr_id IS NULL)) \n" +
            "  WHERE R>:start AND R<=:length AND (operation_date LIKE :pattern " +
            " OR upper(product_name) LIKE upper(:pattern) OR upper(place) LIKE upper(:pattern))";

    private static final String SELECT_COUNT_ORDERS_WITHOUT_CSR = "SELECT COUNT(rownum) FROM " +
            " (SELECT  PRODUCTS.name product_name,PRODUCTS.TYPE_ID, products.CUSTOMER_TYPE_ID customer_type, \n" +
            " orders.id order_id,TO_CHAR(a.OPERATION_DATE, 'YYYY-MM-DD') operation_date, PLACES. NAME place \n" +
            " FROM ORDERS JOIN \n" +
            " (SELECT * FROM OPERATIONS_HISTORY WHERE STATUS_ID=4) a ON (ORDERS.id=a.ORDER_ID) \n" +
            " JOIN PRODUCTS ON (ORDERS.PRODUCT_ID=PRODUCTS.id) \n" +
            " JOIN USERS ON (users.id=orders.USER_ID) JOIN PLACES ON (users.PLACE_ID=PLACES.ID) \n" +
            " WHERE orders.CURRENT_STATUS_ID=4 AND orders.csr_id IS NULL) \n" +
            "  WHERE upper(product_name) LIKE upper(:pattern) OR upper(operation_date) LIKE upper(:pattern) OR " +
            " upper(place) LIKE upper(:pattern)";
    private static String SELECT_ORDER_INFO_BY_ORDER_ID = "SELECT  \n" +
            " PRODUCTS.name product_name,PRODUCTS.TYPE_ID product_type, products.CUSTOMER_TYPE_ID customer_type, \n" +
            " products.DESCRIPTION description,orders.id order_id,TO_CHAR(a.OPERATION_DATE, 'YYYY-MM-DD') operation_date, \n" +
            " PLACES. NAME place, users.name user_name, users.surname user_surname, users.phone user_phone \n" +
            " FROM ORDERS JOIN \n" +
            "  (SELECT MIN(OPERATION_DATE) operation_date,Order_id FROM OPERATIONS_HISTORY WHERE STATUS_ID=4 Group by order_id) a ON (ORDERS.id=a.ORDER_ID) \n" +
            "  JOIN PRODUCTS ON (ORDERS.PRODUCT_ID=PRODUCTS.id) \n" +
            "  JOIN USERS ON (users.id=orders.USER_ID) JOIN PLACES ON (users.PLACE_ID=PLACES.ID) \n" +
            "  WHERE orders.id=:orderId";

    private final static String SET_CSR_ID = "UPDATE ORDERS SET CSR_ID=:csrId WHERE ID=:orderId AND CSR_ID IS NULL";

    private final static String SELECT_INPROCESSING_ORDERS_BY_CSR_ID = "SELECT * FROM ( \n" +
            "  SELECT product_name,product_type,customer_type,order_id, \n" +
            "  TO_CHAR(operation_date,'YYYY-MM-DD') operation_date,place, \n" +
            "  ROW_NUMBER() OVER (ORDER BY %s) R FROM  ( \n" +
            "  SELECT  PRODUCTS.name product_name,PRODUCTS.TYPE_ID product_type, products.CUSTOMER_TYPE_ID customer_type, \n" +
            "  orders.id order_id,a.OPERATION_DATE operation_date, PLACES. NAME place \n" +
            "  FROM ORDERS JOIN \n" +
            "  (SELECT MIN(OPERATION_DATE) operation_date,Order_id FROM OPERATIONS_HISTORY WHERE STATUS_ID=4 Group by order_id) a ON (ORDERS.id=a.ORDER_ID) \n" +
            "  JOIN PRODUCTS ON (ORDERS.PRODUCT_ID=PRODUCTS.id) \n" +
            "  JOIN USERS ON (users.id=orders.USER_ID) JOIN PLACES ON (users.PLACE_ID=PLACES.ID) \n" +
            "  WHERE orders.CURRENT_STATUS_ID=4 AND orders.csr_id=:csrId)) \n" +
            "  WHERE R>:start AND R<=:length AND (upper(operation_date) LIKE upper(:pattern) \n" +
            "  OR upper(product_name) LIKE (:pattern) OR upper(place) LIKE (:pattern))";
    private final static String SELECT_COUNT_INPROCESSING_ORDERS_BY_CSR_ID = "SELECT COUNT(rownum) FROM " +
            " (SELECT  PRODUCTS.name product_name,PRODUCTS.TYPE_ID, products.CUSTOMER_TYPE_ID customer_type, \n" +
            " orders.id order_id,TO_CHAR(a.OPERATION_DATE, 'YYYY-MM-DD') operation_date, PLACES. NAME place \n" +
            " FROM ORDERS JOIN \n" +
            " (SELECT MIN(OPERATION_DATE) operation_date,Order_id FROM OPERATIONS_HISTORY WHERE STATUS_ID=4 Group by order_id) a ON (ORDERS.id=a.ORDER_ID) \n" +
            " JOIN PRODUCTS ON (ORDERS.PRODUCT_ID=PRODUCTS.id) \n" +
            " JOIN USERS ON (users.id=orders.USER_ID) JOIN PLACES ON (users.PLACE_ID=PLACES.ID) \n" +
            " WHERE orders.CURRENT_STATUS_ID=4 AND orders.csr_id=:csrId) \n" +
            "  WHERE upper(product_name) LIKE upper(:pattern) OR upper(operation_date) LIKE upper(:pattern) " +
            " OR upper(place) LIKE upper(:pattern)";
    private final static String ACTIVATE_INPROCESSING_ORDER = "UPDATE ORDERS SET " +
            "CURRENT_STATUS_ID=1" +
            "WHERE ID=:orderId AND CURRENT_STATUS_ID=4";

    private final static String SELECT_PROCESSED_ORDERS_BY_CSR_ID = "SELECT * FROM ( \n" +
            "  SELECT product_name,product_type,customer_type,order_id, \n" +
            "  TO_CHAR(operation_date,'YYYY-MM-DD') operation_date,place, \n" +
            "  ROW_NUMBER() OVER (ORDER BY %s) R FROM  ( \n" +
            "  SELECT  PRODUCTS.name product_name,PRODUCTS.TYPE_ID product_type, products.CUSTOMER_TYPE_ID customer_type, \n" +
            "  orders.id order_id,a.OPERATION_DATE operation_date, PLACES. NAME place \n" +
            "  FROM ORDERS JOIN \n" +
            "  (SELECT min(OPERATION_DATE) operation_date,order_id FROM OPERATIONS_HISTORY WHERE STATUS_ID=1 GROUP BY ORDER_ID) a ON (ORDERS.id=a.ORDER_ID) \n" +
            "  JOIN PRODUCTS ON (ORDERS.PRODUCT_ID=PRODUCTS.id) \n" +
            "  JOIN USERS ON (users.id=orders.USER_ID) JOIN PLACES ON (users.PLACE_ID=PLACES.ID) \n" +
            "  WHERE  orders.csr_id=:csrId)) \n" +
            "  WHERE R>:start AND R<=:length AND (upper(operation_date) LIKE upper(:pattern) \n" +
            "  OR upper(product_name) LIKE upper(:pattern) OR upper(place) LIKE (:pattern))";
    private static final String SELECT_COUNT_PROCESSED_ORDERS_BY_CSR_ID = "SELECT COUNT(rownum) FROM " +
            " (SELECT  PRODUCTS.name product_name,PRODUCTS.TYPE_ID, products.CUSTOMER_TYPE_ID customer_type, \n" +
            " orders.id order_id,TO_CHAR(a.OPERATION_DATE, 'YYYY-MM-DD') operation_date, PLACES. NAME place \n" +
            " FROM ORDERS JOIN \n" +
            " (SELECT min(OPERATION_DATE) operation_date,order_id FROM OPERATIONS_HISTORY WHERE STATUS_ID=1 GROUP BY ORDER_ID) a ON (ORDERS.id=a.ORDER_ID) \n" +
            " JOIN PRODUCTS ON (ORDERS.PRODUCT_ID=PRODUCTS.id) \n" +
            " JOIN USERS ON (users.id=orders.USER_ID) JOIN PLACES ON (users.PLACE_ID=PLACES.ID) \n" +
            " WHERE orders.csr_id=:csrId) \n" +
            "  WHERE upper(product_name) LIKE upper(:pattern) " +
            " OR upper(operation_date) LIKE upper(:pattern) " +
            " OR upper(place) LIKE upper(:pattern)";

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

    public Integer saveAndGetGeneratedId(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource paramsForOrder = new MapSqlParameterSource();
        paramsForOrder.addValue("product_id", order.getProductId());
        paramsForOrder.addValue("user_id", order.getUserId());
        paramsForOrder.addValue("cur_status_id", order.getCurrentStatus().getId());
        jdbcTemplate.update(INSERT_ORDER, paramsForOrder, keyHolder, new String[]{"ID"});
        return new Integer(keyHolder.getKey().intValue());
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
     * Bulgakov Anton
     * {@inheritDoc}
     */
    @Override
    public Integer getOrderIdByUserIdAndProductId(Integer userId, Integer productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("productId", productId);
        try {
            return jdbcTemplate.queryForObject(SELECT_ORDER_ID_BY_USER_ID_AND_PRODUCT_ID_SQL, params, Integer.class);
        } catch (Exception e) {
            logger.debug("There are no user`s orders with such params.");
            return null;
        }
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


    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrdersRowDTO> getLimitedOrderRowsDTOByCustomerId(Integer start, Integer length, String search, String sort, Integer customerId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = LimitedProductsQueryBuilder.getQuery(SELECT_LIMITED_ORDERS_DTO_BY_CUSTOMER_ID_SQL, sort, search, null);
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("cust_id", customerId);
        return jdbcTemplate.query(query, params, new OrdersRowDTORowMapper());
    }

    @Override
    public Integer getCountOrderRowsDTOByCustomerId(String search, String sort, Integer customerId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = LimitedProductsQueryBuilder.getQuery(SELECT_COUNT_ORDERS_DTO_BY_CUSTOMER_ID_SQL, sort, search, null);
        params.addValue("cust_id", customerId);
        return jdbcTemplate.queryForObject(query, params, Integer.class);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean activateOrder(Integer orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderId);
        return jdbcTemplate.update(ACTIVATE_ORDER_SQL, params) > 0;
    }

    @Override
    public Integer getCountOrdersByUserId(Integer userId, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_COUNT_ORDERS_BY_USER_ID, params, Integer.class);
    }

    @Override
    public List<FullInfoOrderDTO> getIntervalOrdersByUserId(int start, int length, String sort, String search, int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sort.isEmpty()) {
            sort = "ID";
        }
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("pattern", "%" + search + "%");
        params.addValue("userId", userId);
        String sql = String.format(SELECT_INTERVAL_ORDERS_BY_USER_ID, sort);
        List<FullInfoOrderDTO> orders = jdbcTemplate.query(sql, params, (resultSet, rownum) -> {
            String name = resultSet.getString("product_name");
            Integer orderId = resultSet.getInt("id");
            ProductType productType = ProductType.getProductTypeFromId(resultSet.getInt("product_type"));
            OperationStatus operationStatus = OperationStatus.getOperationStatusFromId(resultSet.getInt("current_status_id"));
            String description = resultSet.getString("description");
            return new FullInfoOrderDTO(orderId, name, description, productType, operationStatus);
        });
        return orders;
    }

    @Override
    public Integer getCountOrdersWithoutCsr(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_COUNT_ORDERS_WITHOUT_CSR, params, Integer.class);
    }

    //TODO rowmapper
    @Override
    public List<FullInfoOrderDTO> getIntervalOrdersWithoutCsr(int start, int length, String sort, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sort.isEmpty()) {
            sort = "order_id";
        }
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("pattern", "%" + search + "%");
        String sql = String.format(SELECT_ALL_ORDERS_WITHOUT_CSR, sort);
        List<FullInfoOrderDTO> orders = jdbcTemplate.query(sql, params, (rs, rownum) -> {
            FullInfoOrderDTO order = new FullInfoOrderDTO();
            order.setProductName(rs.getString("product_name"));
            order.setProductType(ProductType.getProductTypeFromId(rs.getInt("product_type")));
            order.setCustomerType(CustomerType.getCustomerTypeFromId(rs.getInt("customer_type")));
            order.setOrderId(rs.getInt("order_id"));
            order.setActionDate(rs.getString("operation_date"));
            order.setPlace(rs.getString("place"));
            return order;
        });
        return orders;
    }

    @Override
    public FullInfoOrderDTO getOrderInfoByOrderId(Integer orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource("orderId", orderId);
        return jdbcTemplate.queryForObject(SELECT_ORDER_INFO_BY_ORDER_ID, params, (rs, rownum) -> {
            FullInfoOrderDTO order = new FullInfoOrderDTO();
            order.setProductName(rs.getString("product_name"));
            order.setDescription(rs.getString("description"));
            order.setProductType(ProductType.getProductTypeFromId(rs.getInt("product_type")));
            order.setCustomerType(CustomerType.getCustomerTypeFromId(rs.getInt("customer_type")));
            order.setOrderId(rs.getInt("order_id"));
            order.setActionDate(rs.getString("operation_date"));
            order.setPlace(rs.getString("place"));
            order.setUserName(rs.getString("user_name"));
            order.setUserSurname(rs.getString("user_surname"));
            order.setPhone(rs.getString("user_phone"));
            return order;
        });
    }

    @Override
    public boolean assignToUser(int csrId, int orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("csrId", csrId);
        params.addValue("orderId", orderId);
        return jdbcTemplate.update(SET_CSR_ID, params) > 0;
    }

    @Override
    public Integer getCountOfInprocessingOrdersByCsrId(int csrId, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        params.addValue("csrId", csrId);
        return jdbcTemplate.queryForObject(SELECT_COUNT_INPROCESSING_ORDERS_BY_CSR_ID, params, Integer.class);
    }

    //TODO rowmapper
    @Override
    public List<FullInfoOrderDTO> getIntervalInprocessingOrdersByCsrId(int start, int length, String sort, String search, int csrId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sort.isEmpty()) {
            sort = "order_id";
        }
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("pattern", "%" + search + "%");
        params.addValue("csrId", csrId);
        String sql = String.format(SELECT_INPROCESSING_ORDERS_BY_CSR_ID, sort);
        List<FullInfoOrderDTO> orders = jdbcTemplate.query(sql, params, (rs, rownum) -> {
            FullInfoOrderDTO order = new FullInfoOrderDTO();
            order.setProductName(rs.getString("product_name"));
            order.setProductType(ProductType.getProductTypeFromId(rs.getInt("product_type")));
            order.setCustomerType(CustomerType.getCustomerTypeFromId(rs.getInt("customer_type")));
            order.setOrderId(rs.getInt("order_id"));
            order.setActionDate(rs.getString("operation_date"));
            order.setPlace(rs.getString("place"));
            return order;
        });
        return orders;
    }

    @Override
    public boolean activatedOrder(int orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource("orderId", orderId);
        return jdbcTemplate.update(ACTIVATE_INPROCESSING_ORDER, params) > 0;
    }

    //TODO rowmapper
    @Override
    public List<FullInfoOrderDTO> getIntervalProccesedOrdersByCsrId(int start, int length, String sort, String search, int csrId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sort.isEmpty()) {
            sort = "order_id";
        }
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("pattern", "%" + search + "%");
        params.addValue("csrId", csrId);
        String sql = String.format(SELECT_PROCESSED_ORDERS_BY_CSR_ID, sort);
        List<FullInfoOrderDTO> orders = jdbcTemplate.query(sql, params, (rs, rownum) -> {
            FullInfoOrderDTO order = new FullInfoOrderDTO();
            order.setProductName(rs.getString("product_name"));
            order.setProductType(ProductType.getProductTypeFromId(rs.getInt("product_type")));
            order.setCustomerType(CustomerType.getCustomerTypeFromId(rs.getInt("customer_type")));
            order.setOrderId(rs.getInt("order_id"));
            order.setActionDate(rs.getString("operation_date"));
            order.setPlace(rs.getString("place"));
            return order;
        });
        return orders;
    }

    @Override
    public Integer getCountOfProcessedOrdersByCsrId(int csrId, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        params.addValue("csrId", csrId);
        return jdbcTemplate.queryForObject(SELECT_COUNT_PROCESSED_ORDERS_BY_CSR_ID, params, Integer.class);
    }

}

