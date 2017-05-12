package nc.nut.services;

import nc.nut.dao.entity.OperationStatus;
import nc.nut.dao.order.OrderDao;
import nc.nut.dao.plannedTask.PlannedTask;
import nc.nut.dao.plannedTask.PlannedTaskDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Yuliya Pedash on 08.05.2017.
 */
@Service
public class OrderService {
    @Resource
    private PlannedTaskDao plannedTaskDao;
    @Resource
    private OrderDao orderDao;

    //private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    /**
     * This method performs all operations needed for suspense of order.
     * It adds records to Planned Tasks table with information about when order needs to be
     * suspended and activated later. If order's date of suspense is equal to current date
     * it marks this order as Suspended in orders table and adds record with information
     * about activation of order to Planned Tasks.
     *
     * @param beginDate begin date os suspense of order
     * @param endDate   end date os suspense of order
     * @param orderId   order id
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    //todo transactins
    //  @Transactional
    public boolean suspendOrder(Calendar beginDate, Calendar endDate, Integer orderId) {
        PlannedTask suspendPlanTask = new PlannedTask();
        PlannedTask activatePlanTask = new PlannedTask();
        activatePlanTask.setActionDate(endDate);
        activatePlanTask.setOrderId(orderId);
        activatePlanTask.setStatus(OperationStatus.Active);
        suspendPlanTask.setActionDate(beginDate);
        suspendPlanTask.setOrderId(orderId);
        suspendPlanTask.setStatus(OperationStatus.Suspended);
        boolean isActivatedPlanTaskSaved = plannedTaskDao.save(activatePlanTask);
        if (!isActivatedPlanTaskSaved) {
            return false;
        }
        Calendar currentDate = Calendar.getInstance();
        if (beginDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                beginDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)) {
            return orderDao.suspendOrder(orderId);
        }
        return plannedTaskDao.save(suspendPlanTask);
    }

    /**
     * This method determines if within dates when order is supposed to be suspended
     * exist other planned tasks that can interrupt superdense process.
     *
     * @param beginDate begin date of superdense
     * @param endDate   end date of superdense
     * @return <code>true</code> if order can be suspended withing these dates, <code>false</code> otherwise.
     */
    public boolean canOrderBeSuspendedWithinDates(Calendar beginDate, Calendar endDate, Integer orderId) {
        List<PlannedTask> plannedTasks = plannedTaskDao.getAllPlannedTaskForDates(beginDate, endDate, orderId);
        return plannedTasks.isEmpty();
    }

    /**
     * This methods deactivates order. It marks it as deactivated in Orders
     * table and deletes all planned tasks for this order from planned_tasks table.
     *
     * @param
     * @return
     */
    @Transactional
    public boolean deactivateOrderOfUserCompletely(Integer orderId) {
        return false;
    }
}
