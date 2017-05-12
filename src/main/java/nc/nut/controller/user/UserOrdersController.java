package nc.nut.controller.user;

import nc.nut.dao.order.OrderDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.dto.OrdersRowDTO;
import nc.nut.dto.SuspendFormDTO;
import nc.nut.security.SecurityAuthenticationHelper;
import nc.nut.services.OrderService;
import nc.nut.utils.DatesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Yuliya Pedash on 07.05.2017.
 */
@Controller
@RequestMapping({"", "csr", "user"})
public class UserOrdersController {
    //todo to file
    private final static String SUCCESS_MSG = "Thank you! Your order will be suspended from %s to %s.";
    private final static String DATE_ERROR_MSG = "Unable to suspend your order. Please, check the dates you've entered.";
    private final static String FAIL_SUSPEND_ERROR_MSG = "Sorry! An error occurred while suspending your order. Please, try again.";
    private final static String CANT_SUSP_BECAUSE_OF_OTHER_PLANNED_TASKS_ERROR_MSG = "Unable to suspend your order within these dates, because you have other planned tasks that can interrupt superdense process.";

    @Resource
    private OrderDao orderDao;
    @Resource
    private UserDAO userDAO;
    @Resource
    private OrderService orderService;
    private static Logger logger = LoggerFactory.getLogger(UserOrdersController.class);


    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    @RequestMapping(value = {"residential/orders"}, method = RequestMethod.GET)
    public String showOrdersForUser(Model model) {
        showOrders(model);
        return "newPages/user/residential/Orders";
    }

    @RequestMapping(value = {"business/orders"}, method = RequestMethod.GET)
    public String showOrdersForBusinessUser(Model model) {
        showOrders(model);
        return "newPages/user/business/Orders";
    }

    private void showOrders(Model model) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", user.toString());
        List<OrdersRowDTO> ordersRows = orderDao.getOrderRowsBDTOByCustomerId(user.getCustomerId());
        model.addAttribute("ordersRows", ordersRows);
    }

    @RequestMapping(value = {"/residential/suspend", "/business/suspend"}, method = RequestMethod.POST)
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

    @RequestMapping(value = {"/residential/activateAfterSuspend", "/business/activateAfterSuspend"}, method = RequestMethod.POST)
    @ResponseBody
    public Boolean activateAfterSuspend(@RequestParam Integer orderId) {
        Boolean wasOrderActivated = orderDao.activateOrder(orderId);
        if (wasOrderActivated) {
            logger.info("Successful order activation, order id: {} ", orderId);
        } else {
            logger.info("Unsuccessful order activation, order id: {} ", orderId);
        }
        return wasOrderActivated;
    }
}
