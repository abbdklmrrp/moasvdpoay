package jtelecom.dao.plannedTask;

import jtelecom.dao.interfaces.Dao;
import jtelecom.dto.PlannedTaskDTO;

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

    boolean deletePlannedTaskById(Integer plannedTaskId);

    boolean deletePlannedTaskForActivationOfThisSuspense(PlannedTask suspensePlannedTask);

    List<PlannedTaskDTO> getLimitedPlannedTasksForUsersOrders(Integer userId, Integer start, Integer length);

    Integer getCountPlannedTasksForUserOrders(Integer userId);

    boolean deleteAllPlannedTasksForOrder(Integer orderId);
}
