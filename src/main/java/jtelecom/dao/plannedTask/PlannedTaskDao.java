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
     * This method returns next planned task with status 'Active' from database for this order.
     * @param orderId id of order
     * @return <code>true</code> if any rows were deleted, <code>false</code> otherwise
     */
    boolean deleteNextPlannedTaskForActivationForThisOrder(Integer orderId);

    boolean deletePlannedTaskById(Integer plannedTaskId);

    /**
     * This method deletes next planned task for activation this order after suspense planned task.
     *
     * @param suspensePlannedTask planed tasks for suspense of this order
     * @return <code>true</code> if opperation was successful, <code>false</code> otherwise
     */
    boolean deletePlannedTaskForActivationOfThisSuspense(PlannedTask suspensePlannedTask);

    /**
     * This method gets limited panned tasks for orders of user
     *
     * @param userId id of user
     * @param start  start index
     * @param end    end Index
     * @return
     */
    List<PlannedTaskDTO> getLimitedPlannedTasksForUsersOrders(Integer userId, Integer start, Integer end);

    Integer getCountPlannedTasksForUserOrders(Integer userId);

    boolean deleteAllPlannedTasksForOrder(Integer orderId);
}
