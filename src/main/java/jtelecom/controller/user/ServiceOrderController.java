package jtelecom.controller.user;

import jtelecom.dao.entity.OperationStatus;
import jtelecom.dao.order.Order;
import jtelecom.dao.order.OrderDao;
import jtelecom.dao.product.ProcessingStrategy;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.product.ProductDao;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.ProductCatalogRowDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.OrderService;
import jtelecom.services.ProductService;
import jtelecom.util.SharedVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yuliya Pedash on 24.04.2017.
 */
@SessionScope
@Controller
@RequestMapping({"residential", "business", "csr", "employee"})
public class ServiceOrderController {
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private ProductDao productDao;
    @Resource
    private UserDAO userDAO;
    @Resource
    OrderService orderService;
    Integer categoryId;

    @Resource
    private OrderDao orderDao;
    @Resource
    private ProductService productService;

    private static Logger logger = LoggerFactory.getLogger(ServiceOrderController.class);

    private final static String NO_PRODUCTS_FOR_YOU_MSG = "Sorry! There are no products for you yet here.";
    private final static String ORDER_IN_PROCESS_MSG = "Your order on %s is in process. We will contact you later.";
    private final static String SERVICE_WAS_ACTIVATED_MSG = "Service %s has been activated. Enjoy using it!";
    private final static String ERROR_PLACING_ORDER_MSG = "Sorry, mistake while placing your order. Please, try again!";

    @RequestMapping(value = {"orderService"}, method = RequestMethod.GET)
    public String getUsers(Model model, @RequestParam(required = false) Integer categoryId) throws IOException {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
//        ModelAndView modelAndView = new ModelAndView("newPages/" + currentUser.getRole().getNameInLowwerCase() + "/Services");
        this.categoryId = categoryId;
        List<ProductCategories> productCategories = productDao.findProductCategories();
        model.addAttribute("productsCategories", productCategories);
        model.addAttribute("userRole", currentUser.getRole().getNameInLowwerCase());
        return "newPages/" + currentUser.getRole().getNameInLowwerCase() + "/Services";
    }

    @RequestMapping(value = {"Services"}, method = RequestMethod.GET)
    @ResponseBody
    public ListHolder showServices(@ModelAttribute GridRequestDto request) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        String search = request.getSearch();
        List<ProductCatalogRowDTO> products = productService.getLimitedServicesForUser(currentUser, start, length, sort, search, categoryId);
        int size = productService.getCountForServicesWithSearch(currentUser, search, categoryId);
        logger.debug("Get products in interval:" + start + " : " + length);
        return ListHolder.create(products, size);
    }

//    @RequestMapping(value = {"orderService"}, method = RequestMethod.GET)
//    public String showServices(Model model,  @RequestParam(required = false) String categoryName ) {
//        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
//        logger.debug("Current user id : {} ", currentUser.getId());
//        List<ProductCategories> productCategories = productDao.findProductCategories();
//        model.addAttribute( "productsCategories", productCategories);
//        Map<String, List<ProductCatalogRowDTO>> categoriesWithProductsToShow = productService.getCategoriesWithProductsForUser(currentUser);
//        if (categoriesWithProductsToShow.isEmpty()) {
//            model.addAttribute("msg", NO_PRODUCTS_FOR_YOU_MSG);
//            logger.info("No products for user: {}", currentUser.getId());
//        } else {
//            model.addAttribute("categoriesProducts", categoriesWithProductsToShow);
//            model.addAttribute("msg", null);
//        }
//        return "newPages/" + currentUser.getRole().getNameInLowwerCase() + "/Services";
//    }

    /**
     * This method takes id of service and creates order for this service for
     * currently logged user.
     *
     * @param serviceId id of service
     * @return message with result of operation
     */
    @RequestMapping(value = {"activateService"}, method = RequestMethod.POST)
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
            logger.warn("Error while placing order: {} ", order.toString());
        }
        logger.debug("Order for service: {} was placed", serviceId);
        return msg;

    }

    @RequestMapping(value = {"getNewOrderStatus"}, method = RequestMethod.GET)
    @ResponseBody
    public String getNewOrderStatus(@RequestParam Integer serviceId) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Order newOrder = orderDao.getNotDeactivatedOrderByUserAndProduct(currentUser.getId(), serviceId);
        logger.debug("Gotten  order of user: {} ", newOrder);
        return newOrder.getCurrentStatus().getName();
    }

    /**
     * This method deactivates order of user for particular order.
     *
     * @param serviceId id of service
     * @return String "success" if deactivation was successful, "fail" otherwise
     */
    @RequestMapping(value = {"deactivateService"}, method = RequestMethod.POST)
    @ResponseBody
    public String deactivateOrder(@RequestParam Integer serviceId) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Boolean wasDeactivated = orderService.deactivateOrderForProductOfUserCompletely(serviceId, currentUser.getId());
        if (wasDeactivated) {
            logger.info("Successful deactivation of order (product_id : {}, user_id: {})", serviceId,
                    currentUser.getId());
            return SharedVariables.SUCCESS;
        } else {
            logger.error("Error while deactivating order(product_id : {}, user_id: {})", serviceId,
                    currentUser.getId());
        }
        return SharedVariables.FAIL;

    }

}
