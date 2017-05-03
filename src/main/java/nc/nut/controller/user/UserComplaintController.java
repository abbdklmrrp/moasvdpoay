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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 02.05.2017.
 */
@Controller
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
    public String getComplaint(Model model) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        List<Product> products = productDao.getActiveProductByUserId(user.getId());
        model.addAttribute("productList", products);
        return "user/writeComplaint";
    }

    @RequestMapping(value = "/writeComplaint", method = RequestMethod.POST)
    public String writeComplaint(@RequestParam(value = "products") int productId,
                                 @RequestParam(value = "description") String description) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Integer orderId = orderDAO.getOrderIdByUserIdAndProductId(user.getId(), productId);
        Complaint complaint = new Complaint();
        complaint.setOrderId(orderId);
        complaint.setStatus(ComplaintStatus.InProcessing);
        Calendar calendar = Calendar.getInstance();
        complaint.setCreationDate(calendar);
        complaint.setDescription(description);
        boolean success = complaintDAO.save(complaint);
        if (success) {
            return "user/index";
        }
        return "user/writeComplaint";
    }
}
