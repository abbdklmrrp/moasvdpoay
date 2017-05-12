package nc.nut.controller;


import nc.nut.dao.customer.Customer;
import nc.nut.dao.customer.CustomerDAO;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 17.04.2017.
 */
@Controller
@RequestMapping({"", "csr", "admin"})
public class SignUpController {
    @Resource
    private CustomerDAO customerDAO;
    @Resource
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/registration")
    public ModelAndView registration() {
        ModelAndView model = new ModelAndView("newPages/Registration");
        model.addObject("user", new User());
        return model;
    }

    @RequestMapping(value = "registrationFromCsr", method = RequestMethod.GET)
    public ModelAndView registrationFromCsr() {
        ModelAndView model = new ModelAndView("newPages/csr/RegNewUser");
        List<Customer> customers = customerDAO.getAllBusinessCustomers();
        model.addObject("customers", customers);
        model.addObject("user", new User());
        return model;
    }

    @RequestMapping(value = "registrationFromAdmin", method = RequestMethod.GET)
    public ModelAndView registrationFromAdmin() {
        ModelAndView model = new ModelAndView("newPages/admin/RegNewUser");
        model.addObject("user", new User());
        return model;
    }


    @RequestMapping(value = "/signUpCoworker", method = RequestMethod.POST)
    public String signUpCoworker(User user,
                                 @RequestParam(value = "userType") String userType, RedirectAttributes attributes) {
        user.setRole(Role.getRoleByName(userType));
        String message = userService.saveWithGeneratingPassword(user);
        attributes.addFlashAttribute("msg", message);
        return "redirect:/admin/registrationFromAdmin";
    }

    @RequestMapping(value = "signUpUser", method = RequestMethod.POST)
    public String signUpUser(User user,
                             @RequestParam(value = "userType") String userType,
                             @RequestParam(value = "companyName") String companyName,
                             @RequestParam(value = "secretKey") String secretKey, RedirectAttributes attributes) {
        user.setRole(Role.getRoleByName(userType));
        String message = "";
        if (user.getRole() == Role.RESIDENTIAL) {
            message = userService.saveResidentialWithPasswordGenerating(user);
            logger.info(message);
        } else if (Role.BUSINESS == user.getRole()) {
            message = userService.saveBusinessUser(user, companyName, secretKey);
        }
        attributes.addFlashAttribute("msg", message);
        return "redirect:/csr/registrationFromCsr";

    }


    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(User user, RedirectAttributes attributes) {
        String message = userService.saveResidential(user);
        if ("User successfully saved".equals(message)) {
            return "newPages/Login";
        } else {
            attributes.addFlashAttribute("msg", message);
            return "redirect:/registration";
        }
    }
}
