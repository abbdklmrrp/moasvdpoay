package jtelecom.controller.user;

import jtelecom.dao.order.OrderDao;
import jtelecom.dao.user.Role;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.OrdersRowDTO;
import jtelecom.dto.SuspendFormDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.OrderService;
import jtelecom.util.DatesHelper;
import jtelecom.util.SharedVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Yuliya Pedash on 07.05.2017.
 */

@Controller
@RequestMapping({"residential", "business", "employee", "csr"})
public class UsersOrdersController {
    private final static String SUCCESS_MSG = "Thank you! This order will be suspended from %s to %s.";
    private final static String DATE_ERROR_MSG = "Unable to suspend this order. Please, check the dates you've entered.";
    private final static String FAIL_SUSPEND_ERROR_MSG = "Sorry! An error occurred while suspending this order. Please, try again.";
    private final static String CANT_SUSP_BECAUSE_OF_OTHER_PLANNED_TASKS_ERROR_MSG = "Unable to suspend the order within these dates, because there are other planned tasks that can interrupt suspense process.";
    User user;
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserDAO userDAO;
    @Resource
    private OrderService orderService;
    private static Logger logger = LoggerFactory.getLogger(UsersOrdersController.class);


    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    @RequestMapping(value = {"orders"}, method = RequestMethod.GET)
    public String showOrdersForUser(Model model, HttpSession session) {
        user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        String userRoleLowerCase = user.getRole().getNameInLowwerCase();
        if (user.getRole() == Role.CSR) {
            this.user = userDAO.getUserById((Integer) session.getAttribute("userId"));
        }
        logger.debug("Current user: {}", user.toString());
//        List<OrdersRowDTO> ordersRows = orderDao.getLimitedOrderRowsDTOByCustomerId(, user.getCustomerId());
//        model.addAttribute("ordersRows", ordersRows);
        model.addAttribute("userRole", userRoleLowerCase);
        return "newPages/" + userRoleLowerCase + "/Orders";
    }

//    @RequestMapping(value = {"business/orders"}, method = RequestMethod.GET)
//    public String showOrdersForBusinessUser(Model model) {
//        showOrders(model);
//        return "newPages/business/Orders";
//    }
//
//    private void showOrders(Model model) {
//        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
//        logger.debug("Current user: {}", user.toString());
//        List<OrdersRowDTO> ordersRows = orderDao.getLimitedOrderRowsDTOByCustomerId(user.getCustomerId());
//        model.addAttribute("ordersRows", ordersRows);
//    }
@RequestMapping(value = {"getOrders"}, method = RequestMethod.GET)
@ResponseBody
public ListHolder showServices(@ModelAttribute GridRequestDto request) {
    String sort = request.getSort();
    int start = request.getStartBorder();
    int length = request.getEndBorder();
    String search = request.getSearch();
    List<OrdersRowDTO> products = orderDao.getLimitedOrderRowsDTOByCustomerId(start, length, search, sort, user.getCustomerId());
    int size = orderDao.getCountOrderRowsDTOByCustomerId(search, sort, user.getCustomerId());
    logger.debug("Get orders in interval:" + start + " : " + length);
    return ListHolder.create(products, size);
}

    @RequestMapping(value = {"suspend"}, method = RequestMethod.POST)
    @ResponseBody
    public String suspendOrder(@RequestBody SuspendFormDTO suspendFormDTO) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar beginDate = suspendFormDTO.getBeginDate();
        Calendar endDate = suspendFormDTO.getEndDate();
        Integer orderId = suspendFormDTO.getOrderId();
        if (!DatesHelper.areDatesCorrectForOrderSuspense(beginDate, endDate)) {
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

    @RequestMapping(value = {"activateAfterSuspend"}, method = RequestMethod.POST)
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

    @RequestMapping(value = {"deactivateOrder"}, method = RequestMethod.POST)
    @ResponseBody
    public String deactivateOrder(@RequestParam Integer orderId) {
        Boolean wasDeactivated = orderService.deactivaateOrderCompletely(orderId);
        if (wasDeactivated) {
            logger.info("Successful deactivation of order with id {} ", orderId);
            return SharedVariables.SUCCESS;
        } else {
            logger.info("Unsuccessful deactivation of order with id {} ", orderId);

        }
        return SharedVariables.FAIL;
    }
}
