package jtelecom.controller.user;

import jtelecom.dao.entity.OperationStatus;
import jtelecom.dao.order.Order;
import jtelecom.dao.order.OrderDao;
import jtelecom.dao.product.ProcessingStrategy;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.product.ProductDao;
import jtelecom.dao.user.Role;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yuliya Pedash on 24.04.2017.
 */

@Controller
@RequestMapping({"residential", "business", "csr", "employee"})
public class ServiceOrderController {
    private static final Long RECORDS_PER_PAGE = 10l;
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
    User currentUser;
    private static Logger logger = LoggerFactory.getLogger(ServiceOrderController.class);

    private final static String NO_PRODUCTS_FOR_YOU_MSG = "Sorry! There are no products yet here.";
    private final static String ORDER_IN_PROCESS_MSG = "Your order on %s is in process. It will be activated after processing.";
    private final static String SERVICE_WAS_ACTIVATED_MSG = "Service %s has been activated. Thank you!";
    private final static String ERROR_PLACING_ORDER_MSG = "Sorry, mistake while placing this order. Please, try again!";

    @RequestMapping(value = {"orderService"}, method = RequestMethod.GET)
    public String getUsers(Model model, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String categoryName, HttpSession session) throws IOException {
        this.currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        String userRoleLowerCase = currentUser.getRole().getNameInLowwerCase();
        if (currentUser.getRole() == Role.CSR) {
            this.currentUser = userDAO.getUserById((Integer) session.getAttribute("userId"));
        }
        this.categoryId = categoryId;
        model.addAttribute("categoryName", categoryName == null ?
                "All categories" : categoryName);
        List<ProductCategories> productCategories = productDao.findProductCategories();
        model.addAttribute("productsCategories", productCategories);
        model.addAttribute("userRole", userRoleLowerCase);
        return "newPages/" + userRoleLowerCase + "/Services";
    }


    @RequestMapping(value = {"Services"}, method = RequestMethod.GET)
    @ResponseBody
    public ListHolder showServices(@ModelAttribute GridRequestDto request) {
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
        Product product = productDao.getById(serviceId);
        Order order = new Order();
        String msg;
        order.setProductId(serviceId);
        order.setUserId(currentUser.getId());
        if (product.getProcessingStrategy() == ProcessingStrategy.NeedProcessing) {
            order.setCurrentStatus(OperationStatus.InProcessing);
            msg = String.format(ORDER_IN_PROCESS_MSG, product.getName());
        } else {
            order.setCurrentStatus(OperationStatus.Active);
            msg = String.format(SERVICE_WAS_ACTIVATED_MSG, product.getName());

        }
        boolean isActivated = orderService.activateOrder(product, order);
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
