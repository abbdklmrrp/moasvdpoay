package jtelecom.controller.csr;

import jtelecom.dao.order.OrderDao;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.FullInfoOrderDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 16.05.2017.
 */
@RestController
@RequestMapping({"csr"})
public class CsrMyOrdersController {

    @Resource
    private OrderDao orderDao;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "getMyOrdersPage", method = RequestMethod.GET)
    public ModelAndView getMyOrdersPage() {
        return new ModelAndView("newPages/csr/MyOrders");
    }


    @RequestMapping(value = "getMyOrders", method = RequestMethod.GET)
    public ListHolder getAllOrders(@ModelAttribute GridRequestDto requestDto) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Integer csrId = user.getId();
        Integer start = requestDto.getStartBorder();
        Integer length = requestDto.getEndBorder();
        String sort = requestDto.getSort();
        String search = requestDto.getSearch();
        Integer count = orderDao.getCountOfInprocessingOrdersByCsrId(csrId, search);
        List<FullInfoOrderDTO> orders = orderDao.getIntervalProcessingOrdersByCsrId(start, length, sort, search, csrId);
        return ListHolder.create(orders, count);
    }

    @RequestMapping(value = "getOrderPage")
    public ModelAndView getOrderPage(@RequestParam(name = "id") Integer orderId) {
        ModelAndView modelAndView = new ModelAndView("newPages/csr/OrderPage");
        FullInfoOrderDTO order = orderDao.getOrderInfoByOrderId(orderId);
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(value = "activateOrder", method = RequestMethod.POST)
    public String activateOrder(@RequestParam(value = "orderId") int orderId) {
        boolean success = orderService.activateOrderFromCsr(orderId);
        return success ? "success" : "fail";
    }


}
