package nc.nut.dao.plannedTask;

import nc.nut.dao.interfaces.Dao;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Yuliya Pedash on 08.05.2017.
 */
public interface PlannedTaskDao extends Dao<PlannedTask> {

    /**
     * This method deletes all planed tasks from database for particular order.
     *
     * @param orderId id of order
     * @return <code>true</code> if rows were deleted, <code>false</code> otherwise
     */
    boolean deleteAllPlannedTasksForOrder(Integer orderId);

    /**
     * This method gets all planned tasks, which have action date scheduled from <code>beginDate</code>
     * to <code>endDate</code>
     *
     * @param beginDate begin date
     * @param endDate   end date
     * @return list of <code>PlannedTask</code> objects
     */
    List<PlannedTask> getAllPlannedTaskForDates(Calendar beginDate, Calendar endDate, Integer orderId);
}
