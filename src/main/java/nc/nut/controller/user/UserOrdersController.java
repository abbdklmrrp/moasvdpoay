package nc.nut.controller.user;

import nc.nut.dao.order.OrderDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.dto.OrdersRowDTO;
import nc.nut.security.SecurityAuthenticationHelper;
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
@RequestMapping({"user"})
public class UserOrdersController {
    @Autowired
    OrderDao orderDao;
    @Resource
    private UserDAO userDAO;

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    @RequestMapping(value = {"orders"}, method = RequestMethod.GET)
    public Model showOrdersForUser(Model model) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        List<OrdersRowDTO> ordersRows = orderDao.getOrderRowsByUserId(user.getId());
        model.addAttribute("ordersRows", ordersRows);
        return model;
    }

}
