package jtelecom.controller.user;

import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;

/**
 * @author Anton Bulgakov
 * @since 13.05.2017.
 */
@Controller
@RequestMapping({"business"})
public class RegistrationEmployeeController {

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserService userService;
    @Resource
    private UserDAO userDAO;

    private static Logger logger = LoggerFactory.getLogger(RegistrationEmployeeController.class);

    @RequestMapping(value = {"/regEmployee"}, method = RequestMethod.GET)
    public ModelAndView showRegEmployeePage(){
        return new ModelAndView("newPages/business/RegEmployee").addObject("user", new User());
    }

    // to do!
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String registrationEmployee(User user) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.debug("Current user: {}", currentUser.toString());
        user.setCustomerId(currentUser.getCustomerId());
        String message = null;//userService.saveEmployee(user);
        logger.debug("Result of employee registration: {}",message);
        return message;
    }
}
