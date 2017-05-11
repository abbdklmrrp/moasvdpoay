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
import org.springframework.web.bind.annotation.ResponseBody;

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
    private final static String ORDER_IN_PROCESS_MSG = "Your order on %s is in process. We will contact you later.";
    private final static String SERVICE_WAS_ACTIVATED_MSG = "Service %s has been activated. Enjoy using it!";
    private final static String ERROR_PLACING_ORDER_MSG = "Sorry, mistake while placing your order. Please, try again!";

    @RequestMapping(value = {"orderService"}, method = RequestMethod.GET)
    String showServices(Model model) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user id : " + currentUser.getId());
        Map<String, List<ProductCatalogRowDTO>> categoriesWithProductsToShow = productService.getCategoriesWithProductsForUser(currentUser);
        if (categoriesWithProductsToShow.isEmpty()) {
            model.addAttribute("msg", NO_PRODUCTS_FOR_YOU_MSG);
            logger.info("No products for user: " + currentUser.getId());
        } else {
            model.addAttribute("categoriesProducts", categoriesWithProductsToShow);
        }
        return "newPages/user/residential/Services";
    }

    @RequestMapping(value = {"/activateService"}, method = RequestMethod.POST)
    @ResponseBody
    public String activateService(@RequestParam Integer serviceId) {
        Product chosenProduct = productDao.getById(serviceId);
        Order order = new Order();
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        String msg;
        order.setProductId(serviceId);
        order.setUserId(currentUser.getId());
        if (chosenProduct.getProcessingStrategy() == ProcessingStrategy.NeedProcessing) {
            order.setCurrentStatus(OperationStatus.InProcessing);
            msg = String.format(ORDER_IN_PROCESS_MSG, chosenProduct.getName());
        } else {
            order.setCurrentStatus(OperationStatus.Active);
            msg = String.format(SERVICE_WAS_ACTIVATED_MSG, chosenProduct.getName());

        }
        boolean isActivated = orderDao.save(order);
        if (!isActivated) {
            msg = ERROR_PLACING_ORDER_MSG;
            logger.warn("Error while placing order: " + order.toString());
        }
        return msg;
    }

    @RequestMapping(value = {"/getNewOrderStatus"}, method = RequestMethod.GET)
    @ResponseBody
    public String getNewOrderStatus(@RequestParam Integer serviceId) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Order newOrder = orderDao.getNotDeactivatedOrderByUserAndProduct(currentUser.getId(), serviceId);
        return newOrder.getCurrentStatus().getName();
    }

    @RequestMapping(value = {"/deactivateService"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String deactivateOrder(@RequestParam Integer serviceId) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        boolean wasDeactivated = orderDao.deactivateOrderOfUserForProduct(Integer.valueOf(serviceId), currentUser.getId());
        if (!wasDeactivated) {
            logger.error(String.format("Error while deactivating order(product_id : %s, user_id: %d)", serviceId,
                    currentUser.getId()));
            return "fail";
        }
        return "success";

    }

}
