package jtelecom.controller.csr;

import jtelecom.dao.order.OrderDao;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.FullInfoOrderDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.security.SecurityAuthenticationHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 15.05.2017.
 */
@RestController
@RequestMapping({"csr"})
public class CsrAllOrdersController {
    @Resource
    private OrderDao orderDao;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;

    @RequestMapping(value = "getAllOrdersPage", method = RequestMethod.GET)
    public ModelAndView getAllOrdersPage() {
        return new ModelAndView("newPages/csr/AllOrders");
    }

    @RequestMapping(value = "getAllOrders", method = RequestMethod.GET)
    public ListHolder getAllOrders(@ModelAttribute GridRequestDto requestDto) {
        Integer start = requestDto.getStartBorder();
        Integer length = requestDto.getEndBorder();
        String sort = requestDto.getSort();
        String search = requestDto.getSearch();
        Integer count = orderDao.getCountOrdersWithoutCsr(search);
        List<FullInfoOrderDTO> orders = orderDao.getIntervalOrdersWithoutCsr(start, length, sort, search);
        return ListHolder.create(orders, count);
    }

    @RequestMapping(value = "orderInfo", method = RequestMethod.POST)
    public String orderInfo(@RequestParam(value = "orderId") int orderId) {
        FullInfoOrderDTO order = orderDao.getOrderInfoByOrderId(orderId);
        order.setOrderId(orderId);
        return order.infoMessage();
    }

    @RequestMapping(value = "assignOrder", method = RequestMethod.POST)
    public String assignOrder(@RequestParam(value = "orderId") int orderId) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        boolean success = orderDao.assignToUser(user.getId(), orderId);
        return success ? "success" : "fail";
    }
}
