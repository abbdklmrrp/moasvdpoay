package nc.nut.controller.user;


import nc.nut.dao.complaint.Complaint;
import nc.nut.dao.complaint.ComplaintDAO;
import nc.nut.dao.complaint.ComplaintStatus;
import nc.nut.dao.order.OrderDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.security.SecurityAuthenticationHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 02.05.2017.
 */
@RestController
@RequestMapping({"user"})
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

    @RequestMapping(value = "/getComplaint", method = RequestMethod.GET)
    public ModelAndView getComplaint() {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        List<Product> products = productDao.getActiveProductsByUserId(user.getId());
        ModelAndView modelAndView=new ModelAndView("newPages/user/residential/WriteToSupport");
        modelAndView.addObject("productList",products);
        return modelAndView;
    }

    @RequestMapping(value = "/writeComplaint", method = RequestMethod.POST)
    @ResponseBody
    public String writeComplaint(@RequestParam(value = "productId") int productId,
                                 @RequestParam(value = "description") String description) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Integer orderId = orderDAO.getOrderIdByUserIdAndProductId(user.getId(), productId);
        String message="";
        Calendar calendar = Calendar.getInstance();
        Complaint complaint = new Complaint();
        complaint.setOrderId(orderId);
        complaint.setCreationDate(calendar);
        complaint.setDescription(description);
        complaint.setStatus(ComplaintStatus.InProcessing);
        boolean success = complaintDAO.save(complaint);
        if (success) {
            message="success";
        } else {
            message="fail";
        }
        return message;
    }
}
