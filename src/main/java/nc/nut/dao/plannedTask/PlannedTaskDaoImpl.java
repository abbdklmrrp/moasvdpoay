package nc.nut.dao.plannedTask;


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
    public boolean deleteAllPlannedTasksForOrder(Integer orderId) {
        return false;
    }

    @Override
    public boolean delete(PlannedTask object) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public List<PlannedTask> getAllPlannedTaskForDates(Calendar beginDate, Calendar endDate, Integer orderId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("begin_date", simpleDateFormat.format(beginDate.getTime()));
        params.addValue("end_date", simpleDateFormat.format(endDate.getTime()));
        params.addValue("order_id", orderId);
        return jdbcTemplate.query(SELECT_PLANNED_TASKS_BY_DATES_SQL, params, plannedTaskRowMapper);

    }
}
