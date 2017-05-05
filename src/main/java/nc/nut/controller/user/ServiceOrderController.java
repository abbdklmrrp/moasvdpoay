package nc.nut.controller.user;

import nc.nut.dao.entity.OperationStatus;
import nc.nut.dao.order.Order;
import nc.nut.dao.order.OrderDao;
import nc.nut.dao.product.ProcessingStrategy;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.dto.ProductCatalogRowDTO;
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
    private ProductService productService;

    private static Logger logger = LoggerFactory.getLogger(ServiceOrderController.class);

    private final static String NO_PRODUCTS_FOR_YOU_MSG = "Sorry! There are no products for you yet.";
    private final static String ORDER_IN_PROCESS_MSG = "Your order on %s is in process.\n We will contact you later.";
    private final static String SERVICE_WAS_ACTIVATED_MSG = "Service %s has been activated.\n Enjoy using it!";
    private final static String ERROR_PLACING_ORDER_MSG = "Sorry, mistake while placing your order. Please, try again in 15 minutes!";
    private final static String ERROR_DEACTIVATING_ORDER_MSG = "Sorry, error while deactivating this product for you! Please, try again.";
    private final static String PRODUCT_WAS_DEACTIVATED_MSG = "This product for you was deactivated.";

    @RequestMapping(value = {"orderService"}, method = RequestMethod.GET)
    Model showServices(Model model) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user id : " + currentUser.getId());
        Map<String, List<ProductCatalogRowDTO>> categoriesWithProductsToShow = productService.getCategoriesWithProductsForUser(currentUser);
        if (categoriesWithProductsToShow.isEmpty()) {
            model.addAttribute("msg", NO_PRODUCTS_FOR_YOU_MSG);
            logger.info("No products for user: " + currentUser.getId());
        } else {
            model.addAttribute("categoriesProducts", categoriesWithProductsToShow);
        }
        return model;
    }

    @RequestMapping(value = {"/ordered"}, method = RequestMethod.POST)
    public String processOrder(Model model, @RequestParam(value = "product_id") String productId) {
        Product chosenProduct = productDao.getById(Integer.valueOf(productId));
        Order order = new Order();
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        String msg;
        order.setProductId(Integer.valueOf(productId));
        order.setUserId(currentUser.getId());
        if (chosenProduct.getProcessingStrategy() == ProcessingStrategy.NeedsProcessing) {
            order.setCurrentStatus(OperationStatus.InProcessing);
            msg = String.format(ORDER_IN_PROCESS_MSG, chosenProduct.getName());
        } else {
            order.setCurrentStatus(OperationStatus.Active);
            msg = String.format(SERVICE_WAS_ACTIVATED_MSG, chosenProduct.getName());

        }
        if (!orderDao.save(order)) {
            model.addAttribute("msg", ERROR_PLACING_ORDER_MSG);
            logger.warn("Error while placing order: " + order.toString());
            return "user/result";
        }
        model.addAttribute("resultMsg", msg);
        return "redirect:orderService";
    }

    @RequestMapping(value = {"/deactivate"}, method = RequestMethod.POST)
    public String deactivateOrder(Model model, @RequestParam(value = "product_id") String productId) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        boolean wasDeactivated = orderDao.deactivateOrderOfUserForProduct(Integer.valueOf(productId), currentUser.getId());
        String message;
        if (wasDeactivated) {
            message = PRODUCT_WAS_DEACTIVATED_MSG;
        } else {
            model.addAttribute("msg", ERROR_DEACTIVATING_ORDER_MSG);
            logger.error(String.format("Error while deactivating order(product_id : %s, user_id: %d)", productId,
                    currentUser.getId()));
            return "user/result";
        }
        model.addAttribute("resultMsg", message);
        return "redirect:orderService";

    }

}
