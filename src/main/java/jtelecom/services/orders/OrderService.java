package jtelecom.services.orders;

import jtelecom.dao.entity.OperationStatus;
import jtelecom.dao.order.Order;
import jtelecom.dao.order.OrderDAO;
import jtelecom.dao.plannedTask.PlannedTaskDAO;
import jtelecom.dao.product.Product;

import java.util.Calendar;

/**
 * @author Yuliya Pedash.
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
     * exist other planned tasks that can interrupt superdense process or if begin
     * or end dates of order suspense are after date of deactivation of order.
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
     * @param productId id of product
     * @param userId  id of user
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    boolean deactivateOrderForProductOfUserCompletely(Integer productId, Integer userId);

    /**This methods marks order as 'Deactivated' and removes all planned tasks
     * for it from data base
     * @param orderId id of order
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */

    boolean deactivateOrderCompletely(Integer orderId);

    /**
     * This methods deletes planned task that was supposed to run
     * when order was determined to be activated to be suspense. Then it
     * marks marks it with 'Active" status in database.
     * {@link OperationStatus} for details on statuses.
     *
     * @param orderId id of order
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     * @see PlannedTaskDAO#deleteNextPlannedTaskForActivationForThisOrder(Integer)
     * @see OrderDAO#activateOrder(Integer)
     */
    boolean activateOrderAfterSuspense(Integer orderId);

    /**
     * This method will add  an order to database and add
     * to planned tasks  date of deactivation of this order
     *
     * @param product product
     * @param order order
     * @return <code>true</code> if activation was successful, <code>false</code> otherwise
     */
    boolean activateOrder(Product product, Order order);

    /**
     * Method activates tariff and sends
     * notification to user
     *
     * @param userId   user's id
     * @param tariffId tariff's id
     * @return success of the operation
     */
    boolean activateTariff(Integer userId, Integer tariffId);

    /**
     * Method deactivates tariff and sends
     * notification to user
     *
     * @param userId   user's id
     * @param tariffId tariff's id
     * @return success of the operation
     */
    boolean deactivateTariff(Integer userId, Integer tariffId);

    /**
     * Method activates tariff from csr
     * when order was in processing status
     *
     * @param orderId order's id
     * @return success of the operation
     */
    boolean activateOrderFromCsr(int orderId);

    /**
     * Method sends custom email to user from csr
     *
     * @param orderId  order's id
     * @param text     content of the email
     * @param csrEmail email(username) of the csr
     * @return success of the operation
     */
    boolean sendEmail(int orderId, String text, String csrEmail);
}
