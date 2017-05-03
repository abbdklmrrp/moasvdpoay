package nc.nut.controller.csr;


import nc.nut.dao.complaint.Complaint;
import nc.nut.dao.complaint.ComplaintDAO;
import nc.nut.dao.complaint.ComplaintStatus;
import nc.nut.dao.order.OrderDao;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
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

    @RequestMapping(value = {"getWriteComplaint"}, method = RequestMethod.GET)
    ModelAndView goComplaint() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("csr/writeComplaint");
        return modelAndView;
    }

    @RequestMapping(value = "getSearchUser")
    public User getSearchResultViaAjax(@RequestBody String phone) {
        User user = userDAO.getUserByPhone(phone);
        return user;
    }

    @RequestMapping(value = "getSearchProduct")
    public List<Product> getSearchProduct(@RequestBody int id) {
        List<Product> products = productDao.getActiveProductByUserId(id);
        return products;
    }

    @RequestMapping(value = "writeComplaint", method = RequestMethod.POST)
    public ModelAndView goForward(@RequestParam(value = "id") int userId,
                                  @RequestParam(value = "products") int productId,
                                  @RequestParam(value = "description") String description) {
        ModelAndView modelAndView = new ModelAndView();
        Integer orderId = orderDAO.getOrderIdByUserIdAndProductId(userId, productId);
        Complaint complaint = new Complaint();
        complaint.setOrderId(orderId);
        Calendar calendar = Calendar.getInstance();
        complaint.setCreationDate(calendar);
        complaint.setDescription(description);
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
