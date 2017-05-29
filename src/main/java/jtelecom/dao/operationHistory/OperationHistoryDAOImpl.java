package jtelecom.dao.operationHistory;

import jtelecom.dao.entity.OperationStatus;
import jtelecom.dto.FullInfoOrderDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 10.05.2017.
 */
@Service
public class OperationHistoryDAOImpl implements OperationHistoryDAO {

    private static final String PRODUCT_NAME = "product_name";
    private static final String OPERATION_DATE = "operation_date";
    private static final String STATUS_ID = "status_id";
    private static final String ORDER_ID = "order_id";
    private static final String USER_ID = "user_id";
    private static final String CURRENT_STATUS_ID = "current_status_id";

    private static final String START = "start";
    private static final String END = "end";
    private static final String PATTERN = "pattern";

    private final static String SELECT_OPERATION_HISTORY_BY_USER = "SELECT * from( \n" +
            "  select product_name,OPERATION_DATE,current_status_id, ROW_NUMBER() OVER (ORDER BY %s) R  from \n" +
            "    (Select products.name product_name, TO_CHAR(OPERATION_DATE,'YYYY-MM-DD') operation_date, STATUS_ID current_status_id \n" +
            "     from OPERATIONS_HISTORY \n" +
            "       join ORDERS on OPERATIONS_HISTORY.ORDER_ID = ORDERS.ID \n" +
            "       join products on ORDERS.PRODUCT_ID=PRODUCTS.id \n" +
            "       WHERE ORDER_ID IN (SELECT ID FROM ORDERS WHERE USER_ID IN (Select id from users where customer_id= " +
            " (Select customer_id from users where id=:userId))))) \n" +
            "    where R>:start and R<=:end AND (upper(product_name) LIKE upper(:pattern)  " +
            " or upper(operation_date) like upper(:pattern))";

    private final static String SELECT_COUNT_OPERATION_FOR_USER = "Select count(*) from ( Select " +
            "products.name product_name, TO_CHAR(OPERATION_DATE,'YYYY-MM-DD') operation_date, STATUS_ID \n" +
            "from OPERATIONS_HISTORY \n" +
            "  join ORDERS on OPERATIONS_HISTORY.ORDER_ID = ORDERS.ID \n" +
            "  join products on ORDERS.PRODUCT_ID=PRODUCTS.id \n" +
            "WHERE ORDER_ID IN (SELECT ID FROM ORDERS WHERE USER_ID IN (Select id from users where customer_id= " +
            " (Select customer_id from users where id=:userId)))) \n" +
            "where upper(product_name) like upper(:pattern) " +
            " or upper(operation_date) like upper(:pattern)";

    private final static String SELECT_COUNT_OF_OPERATIONS_BY_ORDER_ID = "SELECT COUNT(ID) \n" +
            "  FROM OPERATIONS_HISTORY \n" +
            "  WHERE ORDER_ID=:orderId";

    private final static String SELECT_INTERVAL_OF_OPERATIONS_BY_ORDER_ID = "SELECT * FROM \n" +
            "(SELECT TO_CHAR(OPERATION_DATE,'YYYY-MM-DD') operation_date, STATUS_ID, PRODUCTS.NAME product_name, ROW_NUMBER() OVER (ORDER BY OPERATION_DATE) R\n" +
            "FROM OPERATIONS_HISTORY JOIN ORDERS ON OPERATIONS_HISTORY.ORDER_ID = ORDERS.ID \n" +
            "  JOIN PRODUCTS ON PRODUCTS.ID=ORDERS.PRODUCT_ID \n" +
            "WHERE ORDER_ID=:orderId ) \n" +
            "WHERE R > :start AND R <= :end";


    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FullInfoOrderDTO> getOperationHistoryByUserId(Integer userId, int start, int length, String order, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource(USER_ID, userId);
        if (order.isEmpty()) {
            order = OPERATION_DATE;
        }
        params.addValue(START, start);
        params.addValue(END, length);
        params.addValue(PATTERN, "%" + search + "%");
        String sql = String.format(SELECT_OPERATION_HISTORY_BY_USER, order);
        return jdbcTemplate.query(sql, params, (rs, rownum) -> {
            FullInfoOrderDTO history = new FullInfoOrderDTO();
            history.setProductName(rs.getString(PRODUCT_NAME));
            history.setActionDate(rs.getString(OPERATION_DATE));
            history.setOperationStatus(OperationStatus.getOperationStatusFromId(rs.getInt(CURRENT_STATUS_ID)));
            return history;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getCountOperationForUser(Integer userId, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource(USER_ID, userId);
        params.addValue(PATTERN, "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_COUNT_OPERATION_FOR_USER, params, Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getCountOperationsByOrderId(int orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource(ORDER_ID, orderId);
        return jdbcTemplate.queryForObject(SELECT_COUNT_OF_OPERATIONS_BY_ORDER_ID, params, Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FullInfoOrderDTO> getIntervalOfOperationsByOrderId(int startIndex, int endIndex, int orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(START, startIndex);
        params.addValue(END, endIndex);
        params.addValue(ORDER_ID, orderId);
        List<FullInfoOrderDTO> history = jdbcTemplate.query(SELECT_INTERVAL_OF_OPERATIONS_BY_ORDER_ID, params, (rs, rownum) -> {
            FullInfoOrderDTO order = new FullInfoOrderDTO();
            order.setActionDate(rs.getString(OPERATION_DATE));
            Integer status = rs.getInt(STATUS_ID);
            order.setOperationStatus(OperationStatus.getOperationStatusFromId(status));
            order.setProductName(rs.getString(PRODUCT_NAME));
            return order;
        });
        return history;
    }
}
