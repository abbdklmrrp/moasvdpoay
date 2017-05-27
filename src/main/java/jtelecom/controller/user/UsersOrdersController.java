package jtelecom.controller.user;

import jtelecom.dao.order.OrderDAO;
import jtelecom.dao.plannedTask.PlannedTaskDAO;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.OrdersRowDTO;
import jtelecom.dto.PlannedTaskDTO;
import jtelecom.dto.SuspendFormDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.orders.OrderService;
import jtelecom.services.plannedTasks.PlannedTaskService;
import jtelecom.util.DatesHelper;
import jtelecom.util.SharedVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Yuliya Pedash on 07.05.2017.
 */
@Controller
@Scope(value = "session")
public class UsersOrdersController implements Serializable {
    private final static String SUCCESS_MSG = "Thank you! This order will be suspended from %s to %s.";
    private final static String DATE_ERROR_MSG = "Unable to suspend this order. Please, check the dates you've entered.";
    private final static String FAIL_SUSPEND_ERROR_MSG = "Sorry! An error occurred while suspending this order. Please, try again.";
    private final static String CANT_SUSP_BECAUSE_OF_OTHER_PLANNED_TASKS_ERROR_MSG = "Unable to suspend the order within these dates, because there are other planned tasks that can interrupt suspense process.";
    User currentUser;
    @Resource
    private OrderDAO orderDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private OrderService orderService;
    private static Logger logger = LoggerFactory.getLogger(UsersOrdersController.class);
    @Resource
    PlannedTaskDAO plannedTaskDAO;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    PlannedTaskService plannedTaskService;

    private void showOrdersForUser(Model model) {
        String userRoleLowerCase = currentUser.getRole().getNameInLowwerCase();
        logger.debug("Current user: {}", currentUser.toString());
        model.addAttribute("userRole", userRoleLowerCase);
    }

    @RequestMapping(value = {"csr/orders"}, method = RequestMethod.GET)
    public String showOrdersForUserCsr(Model model, HttpSession session) {
        this.currentUser = userDAO.getUserById((Integer) session.getAttribute("userId"));
        model.addAttribute("userRole", "csr");
        logger.debug("Current user: {}", currentUser.toString());
        return "newPages/csr/Orders";
    }

    @RequestMapping(value = {"residential/orders"}, method = RequestMethod.GET)
    public String showOrdersForRes(Model model) {
        this.currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        showOrdersForUser(model);
        logger.debug("Current user: {}", currentUser.toString());
        return "newPages/residential/Orders";
    }

    @RequestMapping(value = {"business/orders"}, method = RequestMethod.GET)
    public String showOrdersForBusiness(Model model, HttpSession session) {
        this.currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        showOrdersForUser(model);
        logger.debug("Current user: {}", currentUser.toString());
        return "newPages/business/Orders";
    }

    @RequestMapping(value = {"employee/orders"}, method = RequestMethod.GET)
    public String showOrdersForEmp(Model model) {
        this.currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        showOrdersForUser(model);
        logger.debug("Current user: {}", currentUser.toString());
        return "newPages/employee/Orders";
    }

    @RequestMapping(value = {"csr/getOrders", "residential/getOrders", "business/getOrders", "employee/getOrders"}, method = RequestMethod.GET)
    @ResponseBody
    public ListHolder showOrders(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        String search = request.getSearch();
        List<OrdersRowDTO> products = orderDAO.getLimitedOrderRowsDTOByCustomerId(start, length, search, sort, currentUser.getCustomerId());
        int size = orderDAO.getCountOrdersByCustomerId(search, sort, currentUser.getCustomerId());
        logger.debug("Get orders in interval:" + start + " : " + length);
        return ListHolder.create(products, size);
    }

    @RequestMapping(value = {"csr/suspend", "residential/suspend", "business/suspend"}, method = RequestMethod.POST)
    @ResponseBody
    public String suspendOrder(@RequestBody SuspendFormDTO suspendFormDTO) {
        logger.debug("Request for suspense of order, SuspendFormDTO object {}", suspendFormDTO);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar beginDate = suspendFormDTO.getBeginDate();
        Calendar endDate = suspendFormDTO.getEndDate();
        Integer orderId = suspendFormDTO.getOrderId();
        boolean areDatesCorrect = DatesHelper.areDatesCorrectForOrderSuspense(beginDate, endDate);
        if (!areDatesCorrect) {
            logger.error("Incorrect dates received from superdense form: {}, {}", beginDate, endDate);
            return DATE_ERROR_MSG;
        }
        if (!orderService.canOrderBeSuspendedWithinDates(beginDate, endDate, orderId)) {
            logger.error("Unable to suspend order because of other planned tasks, orderId {} ", orderId);
            return CANT_SUSP_BECAUSE_OF_OTHER_PLANNED_TASKS_ERROR_MSG;
        }
        boolean isServiceSuspended = orderService.suspendOrder(beginDate, endDate, orderId);
        if (isServiceSuspended) {
            logger.info("Successful order suspense, order id: {}", orderId);
            return String.format(SUCCESS_MSG, dateFormat.format(beginDate.getTime()), dateFormat.format(endDate.getTime()));
        }
        logger.error("Unable to suspend order, order id: {}", orderId);
        return FAIL_SUSPEND_ERROR_MSG;

    }

    @RequestMapping(value = {"csr/activateAfterSuspend", "residential/activateAfterSuspend", "business/activateAfterSuspend"}, method = RequestMethod.POST)
    @ResponseBody
    public Boolean activateAfterSuspend(@RequestParam Integer orderId) {
        Boolean wasOrderActivated = orderService.activateOrderAfterSuspense(orderId);
        if (wasOrderActivated) {
            logger.info("Successful order activation, order id: {} ", orderId);
        } else {
            logger.info("Unsuccessful order activation, order id: {} ", orderId);
        }
        return wasOrderActivated;
    }

    @RequestMapping(value = {"csr/deactivateOrder", "residential/deactivateOrder", "business/deactivateOrder"}, method = RequestMethod.POST)
    @ResponseBody
    public String deactivateOrder(@RequestParam Integer orderId) {
        Boolean wasDeactivated = orderService.deactivateOrderCompletely(orderId);
        if (wasDeactivated) {
            logger.info("Successful deactivation of order with id {} ", orderId);
            return SharedVariables.SUCCESS;
        } else {
            logger.info("Unsuccessful deactivation of order with id {} ", orderId);

        }
        return SharedVariables.FAIL;
    }

    @RequestMapping(value = {"csr/getPlannedTasks", "residential/getPlannedTasks", "business/getPlannedTasks"}, method = RequestMethod.GET)
    @ResponseBody
    public ListHolder showPlannedTasks(@ModelAttribute GridRequestDto request) {
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        logger.debug("Current user {} ", currentUser);
        List<PlannedTaskDTO> plannedTaskDTOS = plannedTaskDAO.getLimitedPlannedTasksForUsersOrders(currentUser.getId(), start, length);
        int size = plannedTaskDAO.getCountPlannedTasksForUserOrders(currentUser.getId());
        logger.debug("Got Planned Tasks {} ", plannedTaskDTOS);
        return ListHolder.create(plannedTaskDTOS, size);
    }

    @RequestMapping(value = {"csr/cancelSuspense", "residential/cancelSuspense", "business/cancelSuspense"}, method = RequestMethod.POST)
    @ResponseBody
    public String cancelSuspense(@RequestParam Integer plannedTaskId) {
        logger.debug("Request for cancelling suspense with id  {}", plannedTaskId);
        boolean wasSuspenseCancelled = plannedTaskService.cancelSuspense(plannedTaskId);
        if (wasSuspenseCancelled) {
            logger.info("Suspense with id  {}  was cancelled", plannedTaskId);
            return SharedVariables.SUCCESS;
        }
        logger.error("Unable to cancel suspense with id {}", plannedTaskId);
        return SharedVariables.FAIL;
    }

}
