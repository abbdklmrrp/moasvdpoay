package jtelecom.controller.user;

import jtelecom.dao.user.Role;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    private UserServiceImpl userService;
    @Resource
    private UserDAO userDAO;

    private static Logger logger = LoggerFactory.getLogger(RegistrationEmployeeController.class);

    @RequestMapping(value = {"/regEmployee"}, method = RequestMethod.GET)
    public ModelAndView showRegEmployeePage() {
        return new ModelAndView("newPages/business/RegEmployee").addObject("user", new User());
    }

    // to do!
    @RequestMapping(value = "/regEmployee", method = RequestMethod.POST)
    public String registrationEmployee(@ModelAttribute("user") User employee, RedirectAttributes attributes) {
        User businessUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        logger.info("User {} creates an employee", businessUser.getId());
        employee.setRole(Role.EMPLOYEE);
        employee.setCustomerId(businessUser.getCustomerId());
        String result = userService.saveWithGeneratingPassword(employee);
        if (result.equals("User successfully saved")) {
            result = "Employee successfully created";
        }
        attributes.addFlashAttribute("result", result);
        logger.info("Result of employee registration: {}", result);
        return "redirect:/business/regEmployee";
    }
}
