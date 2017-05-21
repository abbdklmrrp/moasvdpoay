package jtelecom.dao.operationHistory;

import jtelecom.dao.entity.OperationStatus;
import jtelecom.dto.FullInfoOrderDTO;
import jtelecom.repositories.FullComplaintInfoRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 10.05.2017.
 */
@Service
public class OperationHistoryDaoImpl implements OperationHistoryDao {
    private final static String SELECT_OPERATION_HISTORY_BY_USER = "SELECT * from( \n" +
            "  select product_name,OPERATION_DATE,current_status_id, ROW_NUMBER() OVER (ORDER BY %s) R  from \n" +
            "    (Select products.name product_name, TO_CHAR(OPERATION_DATE,'YYYY-MM-DD') operation_date, STATUS_ID current_status_id \n" +
            "     from OPERATIONS_HISTORY \n" +
            "       join ORDERS on OPERATIONS_HISTORY.ORDER_ID = ORDERS.ID \n" +
            "       join products on ORDERS.PRODUCT_ID=PRODUCTS.id \n" +
            "       WHERE ORDER_ID IN (SELECT ID FROM ORDERS WHERE USER_ID IN (Select id from users where customer_id= " +
            " (Select customer_id from users where id=:userId))))) \n" +
            "    where R>:start and R<=:length AND (upper(product_name) LIKE upper(:pattern)  " +
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
            "WHERE R > :startIndex AND R <= :endIndex";


    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<FullInfoOrderDTO> getOperationHistoryByUserId(Integer userId, int start, int length, String order, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        if (order.isEmpty()) {
            order = "operation_date";
        }
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("pattern", "%" + search + "%");
        String sql = String.format(SELECT_OPERATION_HISTORY_BY_USER, order);
        return jdbcTemplate.query(sql, params, (rs, rownum) -> {
            FullInfoOrderDTO history = new FullInfoOrderDTO();
            history.setProductName(rs.getString("product_name"));
            history.setActionDate(rs.getString("operation_date"));
            history.setOperationStatus(OperationStatus.getOperationStatusFromId(rs.getInt("current_status_id")));
            return history;
        });
    }

    @Override
    public Integer getCountOperationForUser(Integer userId, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_COUNT_OPERATION_FOR_USER, params, Integer.class);
    }

    @Override
    public Integer getCountOperationsByOrderId(int orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource("orderId", orderId);
        return jdbcTemplate.queryForObject(SELECT_COUNT_OF_OPERATIONS_BY_ORDER_ID, params, Integer.class);
    }

    @Override
    public List<FullInfoOrderDTO> getIntervalOfOperationsByOrderId(int startIndex, int endIndex, int orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startIndex", startIndex);
        params.addValue("endIndex", endIndex);
        params.addValue("orderId", orderId);
        List<FullInfoOrderDTO> history = jdbcTemplate.query(SELECT_INTERVAL_OF_OPERATIONS_BY_ORDER_ID, params, (rs, rownum) -> {
            FullInfoOrderDTO order = new FullInfoOrderDTO();
            order.setActionDate(rs.getString("operation_date"));
            Integer status = rs.getInt("status_id");
            order.setOperationStatus(OperationStatus.getOperationStatusFromId(status));
            order.setProductName(rs.getString("product_name"));
            return order;
        });
        return history;
    }
}
