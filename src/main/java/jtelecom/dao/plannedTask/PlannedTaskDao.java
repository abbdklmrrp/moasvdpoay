package jtelecom.dao.plannedTask;

import jtelecom.dao.interfaces.Dao;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Yuliya Pedash on 08.05.2017.
 */
public interface PlannedTaskDao extends Dao<PlannedTask> {

    /**
     * This method deletes all planed tasks from database for particular order.
     * @param productId
     * @param userId
     * @return
     */
    boolean deleteAllPlannedTasksForProductOfUser(Integer productId, Integer userId);

    /**
     * This method gets all planned tasks, which have action date scheduled from <code>beginDate</code>
     * to <code>endDate</code>
     *
     * @param beginDate begin date
     * @param endDate   end date
     * @return list of <code>PlannedTask</code> objects
     */
    List<PlannedTask> getAllPlannedTaskForDates(Calendar beginDate, Calendar endDate, Integer orderId);

    /**
     * This method
     *
     * @param orderId
     * @return
     */
    boolean deleteNextPlannedTask(Integer orderId);

    boolean selectAllPlannedTasksForUserOrder(Integer userId);
}
