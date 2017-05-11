package nc.nut.controller.user;


import com.sun.org.apache.xpath.internal.operations.Mod;
import nc.nut.dao.complaint.Complaint;
import nc.nut.dao.complaint.ComplaintDAO;
import nc.nut.dao.complaint.ComplaintStatus;
import nc.nut.dao.order.OrderDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.security.SecurityAuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 02.05.2017.
 */
@RestController
@RequestMapping({"user","csr"})
public class UserComplaintController {
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;
    @Resource
    private ProductDao productDao;
    @Resource
    private ComplaintDAO complaintDAO;
    @Resource
    private OrderDao orderDAO;
    private static Logger logger = LoggerFactory.getLogger(UserComplaintController.class);

    @RequestMapping(value = "/getComplaint", method = RequestMethod.GET)
    public ModelAndView getComplaint() {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        ModelAndView modelAndView = getProducts(user.getId());
        modelAndView.setViewName("newPages/user/residential/WriteToSupport");
        return modelAndView;
    }

    @RequestMapping(value = "getCsrComplaint", method = RequestMethod.GET)
    public ModelAndView writeComplaint(HttpSession session) {
        Integer id = (Integer) session.getAttribute("userId");
        ModelAndView modelAndView = getProducts(id);
        modelAndView.setViewName("newPages/csr/UserWriteComplaint");
        return modelAndView;
    }

    @RequestMapping(value = "/writeComplaint", method = RequestMethod.POST)
    @ResponseBody
    public String writeComplaint(@RequestParam(value = "productId") int productId,
                                 @RequestParam(value = "description") String description) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        return saveComplaint(user.getId(), productId, description);
    }

    @RequestMapping(value = "/saveComplaint", method = RequestMethod.POST)
    @ResponseBody
    public String saveComplaint(@RequestParam(value = "productId") int productId,
                                @RequestParam(value = "description") String description, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return saveComplaint(userId, productId, description);

    }

    private String saveComplaint(int userId, int productId, String description) {
        String message = "";
        Calendar calendar = Calendar.getInstance();
        Integer orderId = orderDAO.getOrderIdByUserIdAndProductId(userId, productId);
        Complaint complaint = new Complaint();
        complaint.setOrderId(orderId);
        complaint.setCreationDate(calendar);
        complaint.setDescription(description);
        complaint.setStatus(ComplaintStatus.InProcessing);
        boolean success = complaintDAO.save(complaint);
        if (success) {
            logger.debug("Complaint saved: orderId " + orderId + " user id: " + userId);

            message = "success";
        } else {
            logger.error("Complaint didn't saved: orderId " + orderId + " user id: " + userId);
            message = "fail";
        }
        return message;
    }


    private ModelAndView getProducts(Integer userId) {
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productDao.getActiveProductsByUserId(userId);
        modelAndView.addObject("productList", products);
        return modelAndView;
    }


}
