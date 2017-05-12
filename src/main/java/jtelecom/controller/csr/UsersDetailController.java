package jtelecom.controller.csr;

import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.googleMaps.ServiceGoogleMaps;
import jtelecom.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Moiseienko Petro
 * @since 28.04.2017.
 */
@RestController
@RequestMapping({"csr"})
public class UsersDetailController {
    @Resource
    private UserDAO userDAO;
    @Resource
    private UserService userService;
    @Resource
    private ServiceGoogleMaps serviceGoogleMaps;
    private static Logger logger = LoggerFactory.getLogger(UsersDetailController.class);

    @RequestMapping(value = "editUser", method = RequestMethod.POST)
    public ModelAndView editUserProfile(User user, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("newPages/csr/UserInfo");
        String place = serviceGoogleMaps.getRegion(user.getAddress());
        Integer placeId = userDAO.findPlaceId(place);
        Integer id = (Integer) session.getAttribute("userId");
        user.setPlaceId(placeId);
        user.setId(id);
        boolean success = userService.updateUser(user);
        if (success) {
            logger.debug("user updated: " + id);
            modelAndView.addObject("user", user);
        } else {
            logger.error("user updating failed, userId " + id);
        }
        return modelAndView;
    }

//    @RequestMapping(value = "getUsersPage", method = RequestMethod.GET)
//    public ModelAndView getUsers(Model model) throws IOException {
//        return new ModelAndView("newPages/csr/Users");
//    }
//
//    @RequestMapping(value = {"getUsers"}, method = RequestMethod.GET)
//    public ListHolder getUsers(@ModelAttribute GridRequestDto request) {
//        String sort = request.getSort();
//        int start = request.getStartBorder();
//        int length = request.getLength();
//        String search = request.getSearch();
//        List<User> data = userDAO.getLimitedQuantityUsers(start, length, sort, search);
//        int size = userDAO.getCountUsersWithSearch(search);
//        return ListHolder.create(data, size);
//    }


    @RequestMapping(value = "userTariffs", method = RequestMethod.POST)
    public String addProduct(HttpSession session, Model model) throws IOException {

        return "csr/index";
    }

//    @RequestMapping(value="writeComplaint", method = RequestMethod.GET)
//    public ModelAndView writeComplaint(HttpSession session){
//        Integer userId=(Integer)session.getAttribute("userId");
//        List<Product> products=productDao.getActiveProductsByUserId(userId);
//        ModelAndView modelAndView=new ModelAndView("newPages/csr/UserWriteComplaint");
//        modelAndView.addObject("productList",products);
//        return modelAndView;
//    }
//    @RequestMapping(value="/saveComplaint",method=RequestMethod.POST)
//    @ResponseBody
//    public String saveComplaint(@RequestParam(value = "productId") int productId,
//                                @RequestParam(value = "description") String description,HttpSession session){
//        String message="";
//        Integer userId=(Integer)session.getAttribute("userId");
//        Calendar calendar = Calendar.getInstance();
//        Integer orderId = orderDao.getOrderIdByUserIdAndProductId(userId, productId);
//        Complaint complaint = new Complaint();
//        complaint.setOrderId(orderId);
//        complaint.setCreationDate(calendar);
//        complaint.setDescription(description);
//        complaint.setStatus(ComplaintStatus.InProcessing);
//        boolean success = complaintDAO.save(complaint);
//        if (success) {
//           message="success";
//        } else {
//            message="fail";
//        }
//        return message;
//    }

//    @RequestMapping(value = "userOrders", method = RequestMethod.POST)
//    public ModelAndView viewOrders(Model model) throws IOException {
//        return new ModelAndView("newPages/csr/UserOrders");
//    }
//
//    @RequestMapping(value="getOperationHistory",method = RequestMethod.GET)
//    public ListHolder getOperationHistory(@ModelAttribute GridRequestDto request,HttpSession session){
//        Integer userId=(Integer)session.getAttribute("userId");
//        String sort = request.getSort();
//        int start = request.getStartBorder();
//        int length = request.getEndBorder();
//        List<OperationHistoryRecord> data = operationHistoryDao.getOperationHistoryByUserId(userId,start,length,sort);
//        int size = operationHistoryDao.getCountOperationForUser(userId);
//        return ListHolder.create(data, size);
//    }

    @RequestMapping(value = "sendPassword", method = RequestMethod.POST)
    public String sendPassword(HttpSession session) {
        return "csr/index";
    }

//    @RequestMapping(value = "getDetails", method = RequestMethod.GET)
//    public ModelAndView getDetails(@RequestParam(value = "id") int id, HttpSession session) throws IOException {
//        ModelAndView model = new ModelAndView();
//        User user = userDAO.getUserById(id);
//        session.setAttribute("userId", id);
//        model.setViewName("newPages/csr/UserInfo");
//        model.addObject("user", user);
//        return model;
//    }

}
