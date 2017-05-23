package jtelecom.controller.csr;

import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Moiseienko Petro
 * @since 11.05.2017.
 */
@RestController
@RequestMapping({"csr","pmg"})
public class UserInfoController {

    @Resource
    private UserDAO userDAO;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @RequestMapping(value = "getDetails", method = RequestMethod.GET)
    public ModelAndView getDetails(@RequestParam(value = "id") int id, HttpSession session) throws IOException {
        ModelAndView model = new ModelAndView();
        User sessionUser=userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        User user = userDAO.getUserById(id);
        session.setAttribute("userId", id);
        model.setViewName("newPages/"+sessionUser.getRole().getNameInLowwerCase()+"/UserInfo");
        model.addObject("user", user);
        logger.debug("Get to user page, id " + id);
        return model;
    }

    @RequestMapping(value="sendPassword",method = RequestMethod.POST)
    public String sendPassword(@RequestParam(value="userId") int userId){
     boolean success=userService.generateNewPassword(userId);
     String message="";
     if(success){
         message="New password successfully sent";
     }
     else{
         message="Sorry, an error occurred while sending new password!";
     }
     return message;
    }

    @RequestMapping(value="getUserProfile", method = RequestMethod.GET)
    public ModelAndView getProfile(HttpSession session){
        Integer userId=(Integer)session.getAttribute("userId");
        User user = userDAO.getUserById(userId);
        ModelAndView model = new ModelAndView();
        model.setViewName("newPages/csr/UserInfo");
        model.addObject("user", user);
        logger.debug("Get to user page, id " + userId);
        return model;

    }


}
