package jtelecom.dao.plannedTask;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Yuliya Pedash on 08.05.2017.
 */
@Service
public class PlannedTaskDaoImpl implements PlannedTaskDao {
    private final static String INSERT_PLANNED_TASK_SQL = "INSERT INTO PLANNED_TASKS(STATUS_ID, ORDER_ID, ACTION_DATE)\n" +
            "    VALUES (:status_id, :order_id, TO_DATE(:date, 'YYYY-MM-DD'))";
    private final static String SELECT_PLANNED_TASKS_BY_DATES_SQL = "SELECT * FROM PLANNED_TASKS WHERE\n" +
            "ORDER_ID = :order_id AND " +
            "  ACTION_DATE BETWEEN  TO_DATE(:begin_date, 'YYYY-MM-DD') AND TO_DATE(:end_date, 'YYYY-MM-DD') ";
    private final static String DELETE_PLANNED_TASKS_FOR_ORDER_OF_PRODUCT_FOR_USER_SQL = "DELETE FROM PLANNED_TASKS\n" +
            "  WHERE ORDER_ID IN (SELECT  MAX(ID) FROM ORDERS\n" +
            "  WHERE PRODUCT_ID = :product_id AND USER_ID = :user_id\n" +
            "  AND CURRENT_STATUS_ID <> 3 /*Deactivated*/)";
    private final static String DELETE_NEXT_PLANNED_TASK_FOR_ACTIVATION_FOR_ORDER_USER_SQL = "DELETE FROM PLANNED_TASKS\n" +
            "WHERE ACTION_DATE = (SELECT MIN(ACTION_DATE) FROM PLANNED_TASKS\n" +
            "WHERE ORDER_ID = :order_id AND STATUS_ID = 1/*Active*/) AND ORDER_ID = :order_id AND STATUS_ID = 1 /*Active*/";
    private final static String SELECT_PLANNED_TASKS_OF_USER = "SELECT p.id, p.STATUS_ID, p.ORDER_ID, p.ACTION_DATE FROM PLANNED_TASKS p\n" +
            "INNER JOIN ORDERS ON p.ORDER_ID = ORDERS.ID\n" +
            "WHERE ORDERS.USER_ID = :user_id";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    PlannedTaskRowMapper plannedTaskRowMapper;

    @Override
    public PlannedTask getById(int id) {
        throw new UnsupportedOperationException("This operation is not supported");

    }

    @Override
    public boolean update(PlannedTask object) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public boolean save(PlannedTask plannedTask) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status_id", plannedTask.getStatus().getId());
        params.addValue("order_id", plannedTask.getOrderId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        params.addValue("date", dateFormat.format(plannedTask.getActionDate().getTime()));
        return jdbcTemplate.update(INSERT_PLANNED_TASK_SQL, params) > 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteAllPlannedTasksForProductOfUser(Integer productId, Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);
        params.addValue("user_id", userId);
        return jdbcTemplate.update(DELETE_PLANNED_TASKS_FOR_ORDER_OF_PRODUCT_FOR_USER_SQL, params) > 0;
    }

    @Override
    public boolean delete(PlannedTask object) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlannedTask> getAllPlannedTaskForDates(Calendar beginDate, Calendar endDate, Integer orderId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("begin_date", simpleDateFormat.format(beginDate.getTime()));
        params.addValue("end_date", simpleDateFormat.format(endDate.getTime()));
        params.addValue("order_id", orderId);
        return jdbcTemplate.query(SELECT_PLANNED_TASKS_BY_DATES_SQL, params, plannedTaskRowMapper);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteNextPlannedTask(Integer orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("order_id", orderId);
        return jdbcTemplate.update(DELETE_NEXT_PLANNED_TASK_FOR_ACTIVATION_FOR_ORDER_USER_SQL, params) > 0;
    }

    @Override
    public List<PlannedTask> selectAllPlannedTasksForUserOrder(Integer userId) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return jdbcTemplate.query(SELECT_PLANNED_TASKS_OF_USER, params, plannedTaskRowMapper);

    }
}
