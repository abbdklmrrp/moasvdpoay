package nc.nut.controller.product;

import nc.nut.dao.entity.OperationStatus;
import nc.nut.dao.order.Order;
import nc.nut.dao.order.OrderDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.security.SecurityAuthenticationHelper;
import nc.nut.services.ProductService;
import nc.nut.utils.ProductCatalogRow;
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
    private ProductService productService;
    private Logger logger = LoggerFactory.getLogger(ServiceOrderController.class);
    //todo or get in every method?
    private User currentUser = null;


    @RequestMapping(value = {"orderService"}, method = RequestMethod.GET)
    Model showServices(Model model) {
        currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Map<String, List<ProductCatalogRow>> categoriesWithProductsToShow = productService.getCategoriesWithProductsForUser(currentUser);
        if (categoriesWithProductsToShow.isEmpty()) {
            model.addAttribute("msg", "Sorry! There are no products for you yet.");
        } else {
            model.addAttribute("categoriesProducts", categoriesWithProductsToShow);
        }
        return model;
    }

    @RequestMapping(value = {"/ordered"}, method = RequestMethod.POST)
    String processOrder(Model model, @RequestParam(value = "product_id") String productId) {
        Product productToOrder = productDao.getById(Integer.valueOf(productId));
        Order order = new Order();
        //     User userId = userDAO.findByUsername(securityAuthenticationHelper.getCurrentUser().getUsername());
        order.setProductId(Integer.valueOf(productId));
        String msg;
        order.setUserId(currentUser.getId());
        if (productToOrder.getNeedProcessing() == 1) {
            order.setCurrentStatus(OperationStatus.InProcessing);
            msg = "Your order on " + productToOrder.getName() + " is in process.\n We will contact you later.";
        } else {
            order.setCurrentStatus(OperationStatus.Active);
            msg = "Service " + productToOrder.getName() + " has been activated.\n Enjoy using it!";

        }
        if (!orderDao.save(order)) {
            msg = "Sorry, mistake while placing your order. Please, try again in 15 minutes!";
            model.addAttribute("msg", msg);
            logger.info("Error while placing order: ", order.toString());
            return "user/result";
        }
        model.addAttribute("resultMsg", msg);
        return "redirect:orderService";
    }

    @RequestMapping(value = {"/deactivate"}, method = RequestMethod.POST)
    String deactivateOrder(Model model, @RequestParam(value = "product_id") String productId) {
        Product productToOrder = productDao.getById(Integer.valueOf(productId));
        boolean wasDeactivated = orderDao.deactivateOrderOfUserForProduct(Integer.valueOf(productId), currentUser.getId());
        String message;
        if (wasDeactivated) {
            message = "This product for you was deactivated.";
        } else {
            message = "Mistake while deactivating this product for you! Please, try again.";
            model.addAttribute("msg", message);
            return "user/result";
        }
        model.addAttribute("resultMsg", message);
        return "redirect:orderService";

    }

}
