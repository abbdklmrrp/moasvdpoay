package nc.nut.controller.csr;


import nc.nut.dao.complaint.Complaint;
import nc.nut.dao.complaint.ComplaintDAO;
import nc.nut.dao.complaint.ComplaintStatus;
import nc.nut.dao.order.OrderDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.security.SecurityAuthenticationHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 27.04.2017.
 */
@RestController

@RequestMapping({"csr"})
public class ComplaintController {
    @Resource
    private UserDAO userDAO;
    @Resource
    private ProductDao productDao;
    @Resource
    private ComplaintDAO complaintDAO;
    @Resource
    private OrderDao orderDAO;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    @RequestMapping(value = {"getWriteComplaint"}, method = RequestMethod.GET)
    public ModelAndView goComplaint() {
        return new ModelAndView("csr/writeComplaint");
    }

    @RequestMapping(value = "getSearchUser")
    public User getSearchUser(@RequestBody String phone) {
        return userDAO.getUserByPhone(phone);
    }

    @RequestMapping(value = "getSearchProduct")
    public List<Product> getSearchProduct(@RequestBody int id) {
        return productDao.getActiveProductsByUserId(id);
    }

    @RequestMapping(value = "writeComplaint", method = RequestMethod.POST)
    public ModelAndView writeComplain(@RequestParam(value = "id") int userId,
                                      @RequestParam(value = "products") int productId,
                                      @RequestParam(value = "description") String description) {
        ModelAndView modelAndView = new ModelAndView();
        Calendar calendar = Calendar.getInstance();
        Integer orderId = orderDAO.getOrderIdByUserIdAndProductId(userId, productId);
        User user=userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Complaint complaint = new Complaint();
        complaint.setOrderId(orderId);
        complaint.setCreationDate(calendar);
        complaint.setDescription(description);
        complaint.setCsrId(user.getId());
        complaint.setStatus(ComplaintStatus.InProcessing);
        boolean success = complaintDAO.save(complaint);
        if (success) {
            modelAndView.setViewName("csr/index");
        } else {
            modelAndView.setViewName("csr/writeComplaint");
        }
        return modelAndView;
    }
}
