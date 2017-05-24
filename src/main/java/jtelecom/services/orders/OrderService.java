package jtelecom.services.orders;

import jtelecom.dao.entity.OperationStatus;
import jtelecom.dao.order.Order;
import jtelecom.dao.order.OrderDao;
import jtelecom.dao.plannedTask.PlannedTaskDao;
import jtelecom.dao.product.Product;

import java.util.Calendar;

/**
 * Created by Yuliya Pedash on 08.05.2017.
 */
public interface OrderService {


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
    boolean suspendOrder(Calendar beginDate, Calendar endDate, Integer orderId);

    /**
     * This method determines if within dates when order is supposed to be suspended
     * exist other planned tasks that can interrupt superdense process.
     *
     * @param beginDate begin date of superdense
     * @param endDate   end date of superdense
     * @return <code>true</code> if order can be suspended withing these dates, <code>false</code> otherwise.
     */
    boolean canOrderBeSuspendedWithinDates(Calendar beginDate, Calendar endDate, Integer orderId);

    /**
     * This methods deactivates order. It marks it as deactivated in Orders
     * table and deletes all planned tasks for this order from planned_tasks table.
     *
     * @param
     * @return
     */
    boolean deactivateOrderForProductOfUserCompletely(Integer productId, Integer userId);


    boolean deactivateOrderCompletely(Integer orderId);

    /**
     * This methods deletes planned task that was supposed to run
     * when order was determined to be activated to be suspense. Then it
     * marks marks it with 'Active" status in database.
     * {@link OperationStatus} for details on statuses.
     *
     * @param orderId id of order
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     * @see PlannedTaskDao#deleteNextPlannedTaskForActivationForThisOrder(Integer)
     * @see OrderDao#activateOrder(Integer)
     */
    public boolean activateOrderAfterSuspense(Integer orderId);

    /**
     * This method will add to planned tasks  date of deactivation of order
     *
     * @param product
     * @param order
     * @return
     */
    boolean activateOrder(Product product, Order order);

    boolean activateTariff(Integer userId, Integer tariffId);

    boolean deactivateTariff(Integer userId, Integer tariffId);

    boolean activateOrderFromCsr(int orderId);


    void sendEmail(int orderId, String text, String csrEmail);
}