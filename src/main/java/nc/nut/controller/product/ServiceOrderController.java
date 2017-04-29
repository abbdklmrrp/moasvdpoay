package nc.nut.controller.product;

import nc.nut.dao.order.Order;
import nc.nut.dao.order.OrderDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.security.SecurityAuthenticationHelper;
import nc.nut.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yuliya Pedash on 24.04.2017.
 */
@Controller
@RequestMapping({"user"})
public class ServiceOrderController {
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private ProductDao productDao;
    @Resource
    private UserDAO userDAO;

    @Resource
    private OrderDao orderDao;
    @Resource
    ProductService productService;
    private Logger logger = LoggerFactory.getLogger(ServiceOrderController.class);


    @RequestMapping(value = {"orderService"}, method = RequestMethod.GET)
    Model showServices(Model model) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Map<String, Map<Product, String>> categoriesWithProductsToShow = productService.getCategoriesWithProductsToShow(user);
        model.addAttribute("categoriesProducts", categoriesWithProductsToShow);
        return model;
    }
//
//    @RequestMapping(value = {"/ordered"}, method = RequestMethod.POST)
//    String processOrder(Model model, @RequestParam(value = "product_id") String productId) {
//        Product productToOrder = productDao.getById(Integer.valueOf(productId));
//        Order order = new Order();
//        User userId = userDAO.findByUsername(securityAuthenticationHelper.getCurrentUser().getUsername());
//        order.setProductId(Integer.valueOf(productId));
//        order.setUserId(userId.getId());
//        String msg;
//        if (productToOrder.getNeedProcessing() == 1) {
//            order.setCurrent_status_id(4);
//            msg = "Your order on " + productToOrder.getName() + " is in proces.\n We will contact you later.";
//        } else {
//            order.setCurrent_status_id(1);
//            msg = "Service " + productToOrder.getName() + " has been activated.\n Enjoy using it!";
//
//        }
//        model.addAttribute("msg", msg);
//        if (orderDao.save(order)){
//            msg = "Sorry, mistake while placing your order. Please, try again in 15 minutes!";
//            logger.info("Error while placing order: ", order.toString());
//        }
//        model.addAttribute("msg",msg);
//        return "user/orderServiceResult";
//    }

}
