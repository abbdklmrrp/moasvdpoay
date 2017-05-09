package nc.nut.controller.user;

import nc.nut.dao.order.OrderDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.dto.OrdersRowDTO;
import nc.nut.security.SecurityAuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yuliya Pedash on 07.05.2017.
 */
@Controller
@RequestMapping({"","csr","user"})
public class UserOrdersController {
    @Autowired
    OrderDao orderDao;
    @Resource
    private UserDAO userDAO;

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    private static Logger logger = LoggerFactory.getLogger(UserOrdersController.class);

    @RequestMapping(value = {"residential/orders"}, method = RequestMethod.GET)
    public String showOrdersForResidentialUser(Model model) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", user.toString());
        List<OrdersRowDTO> ordersRows = orderDao.getOrderRowsByUserId(user.getId());
        model.addAttribute("ordersRows", ordersRows);
        return "newPages/user/residential/Orders";
    }

    @RequestMapping(value = {"business/orders"}, method = RequestMethod.GET)
    public String showOrdersForBusinessUser(Model model) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", user.toString());
        List<OrdersRowDTO> ordersRows = orderDao.getOrderRowsByUserId(user.getId());
        model.addAttribute("ordersRows", ordersRows);
        return "newPages/user/business/Orders";
    }

}
