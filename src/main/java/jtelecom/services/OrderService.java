package jtelecom.services;

import jtelecom.dao.entity.OperationStatus;
import jtelecom.dao.order.Order;
import jtelecom.dao.order.OrderDao;
import jtelecom.dao.plannedTask.PlannedTask;
import jtelecom.dao.plannedTask.PlannedTaskDao;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.services.mail.MailService;
import jtelecom.util.DatesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Resource
    private UserDAO userDAO;
    @Resource
    private MailService mailService;
    @Resource
    private ProductDao productDao;
    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

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
    @Transactional
    public boolean suspendOrder(Calendar beginDate, Calendar endDate, Integer orderId) {
        PlannedTask suspendPlanTask = new PlannedTask();
        PlannedTask activatePlanTask = new PlannedTask();
        activatePlanTask.setActionDate(endDate);
        activatePlanTask.setOrderId(orderId);
        activatePlanTask.setStatus(OperationStatus.Active);
        suspendPlanTask.setActionDate(beginDate);
        suspendPlanTask.setOrderId(orderId);
        suspendPlanTask.setStatus(OperationStatus.Suspended);
        User user = userDAO.getUserByOrderId(orderId);
        Product product = productDao.getProductByOrderId(orderId);
        boolean isActivatedPlanTaskSaved = plannedTaskDao.save(activatePlanTask);
        if (!isActivatedPlanTaskSaved) {
            logger.error("Unable to save activated planned task to database {}", activatePlanTask);
            return false;
        }
        Calendar currentDate = Calendar.getInstance();
        if (beginDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                beginDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)) {
            boolean success = orderDao.suspendOrder(orderId);
            if (success) {
                mailService.sendProductSuspendedEmail(user, product, beginDate, endDate);
                logger.info("Order with id {} was suspended", orderId);
            }
            return success;
        }
        boolean success = plannedTaskDao.save(suspendPlanTask);
        if (success) {
            mailService.sendProductSuspendedEmail(user, product, beginDate, endDate);
        }
        return success;
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
    public boolean deactivateOrderForProductOfUserCompletely(Integer productId, Integer userId) {
        boolean plannedTasksDeleted = plannedTaskDao.deleteAllPlannedTasksForProductOfUser(productId, userId);
        if (!plannedTasksDeleted) {
            logger.error("Not able to delete planned tasks for order for product {} of user {} ", productId, userId);
            return false;
        }
        boolean success = orderDao.deactivateOrderOfUserForProduct(productId, userId);
        if (success) {
            User user = userDAO.getUserById(userId);
            Product product = productDao.getById(productId);
            mailService.sendProductDeactivated(user, product);
        }
        return success;
    }

    public boolean deactivaateOrderCompletely(Integer orderId) {
        boolean plannedTasksDeleted = plannedTaskDao.deleteAllPlannedTasksForOrder(orderId);
        if (!plannedTasksDeleted) {
            logger.error("Not able to delete planned tasks for order for order {} ", orderId);
            return false;
        }
        return orderDao.deactivateOrder(orderId);
    }

    /**
     * This methods deletes planned task that was supposed to run
     * when order was determined to be activated to be suspense. Then it
     * marks marks it with 'Active" status in database.
     * {@link OperationStatus} for details on statuses.
     *
     * @param orderId id of order
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     * @see PlannedTaskDao#deleteNextPlannedTask(Integer)
     * @see OrderDao#activateOrder(Integer)
     */
    @Transactional
    public boolean activateOrderAfterSuspense(Integer orderId) {
        plannedTaskDao.deleteNextPlannedTask(orderId);
        boolean success = orderDao.activateOrder(orderId);
        if (success) {
            User user = userDAO.getUserByOrderId(orderId);
            Product product = productDao.getProductByOrderId(orderId);
            mailService.sendProductActivatedEmail(user, product);
        }
        return success;
    }

    /**
     * This method will add to planned tasks  date of deactivation of order
     *
     * @param product
     * @param order
     * @return
     */
    public boolean activateOrder(Product product, Order order) {
        Calendar deactivateOrderDate = DatesHelper.getCurrentDate();
        deactivateOrderDate.add(Calendar.DATE, product.getDurationInDays());
        Integer orderId = orderDao.saveAndGetGeneratedId(order);
        if (orderId != null) {
            User user = userDAO.getUserById(order.getUserId());
            if (order.getCurrentStatus() == OperationStatus.InProcessing) {
                mailService.sendProductProcessingEmail(user, product);
            } else if (order.getCurrentStatus() == OperationStatus.Active) {
                mailService.sendProductActivatedEmail(user, product);
            }
        }
        PlannedTask deactPlannedTask = new PlannedTask(OperationStatus.Deactivated, orderId, deactivateOrderDate);
        return plannedTaskDao.save(deactPlannedTask);
    }

    public boolean activateTariff(Integer userId, Integer tariffId) {
        boolean success = productDao.activateTariff(userId, tariffId);
        if (success) {
            User user = userDAO.getUserById(userId);
            Product product = productDao.getById(tariffId);
            mailService.sendProductActivatedEmail(user, product);
        }
        return success;
    }

    public boolean deactivateTariff(Integer userId, Integer tariffId) {
        boolean success = productDao.deactivateTariff(userId, tariffId);
        if (success) {
            User user = userDAO.getUserById(userId);
            Product product = productDao.getById(tariffId);
            mailService.sendProductDeactivated(user, product);
        }
        return success;
    }
}
