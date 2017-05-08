package nc.nut.controller;


import nc.nut.dao.customer.Customer;
import nc.nut.dao.customer.CustomerDAO;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.googleMaps.ServiceGoogleMaps;
import nc.nut.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 17.04.2017.
 */
@Controller
@RequestMapping({"", "csr", "admin"})
public class SignUpController {
    @Resource
    private UserDAO userDAO;
    @Resource
    private CustomerDAO customerDAO;
    @Resource
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/registration")
    public ModelAndView registration() {
        ModelAndView model = new ModelAndView("signUp");
        model.addObject("user", new User());
        return model;
    }

    @RequestMapping(value = "registrationFromCsr", method = RequestMethod.GET)
    public ModelAndView registrationFromCsr() {
        ModelAndView model = new ModelAndView("csr/signUp");
        List<String> customersName = customerDAO.getAllBusinessCustomersName();
        model.addObject("customersName", customersName);
        model.addObject("user", new User());
        return model;
    }

    @RequestMapping(value = "registrationFromAdmin", method = RequestMethod.GET)
    public ModelAndView registrationFromAdmin() {
        ModelAndView model = new ModelAndView("admin/createCoworker");
        model.addObject("user", new User());
        return model;
    }


    private User getUser(User user,
                         String city,
                         String street,
                         String building) {
        String address = city + ", " + street + ", " + building;
        ServiceGoogleMaps maps = new ServiceGoogleMaps();
        String place = maps.getRegion(address);
        int placeId = userDAO.findPlaceId(place);
        user.setAddress(address);
        user.setPlaceId(placeId);
        return user;

    }

    @RequestMapping(value = "/signUpCoworker", method = RequestMethod.POST)
    public String signUpCoworker(User user,
                                 @RequestParam(value = "userType") String userType) {
        user.setRole(Role.getRoleByName(userType));
        boolean success = userService.saveWithGeneratePassword(user);
        if (!success) {
            return "admin/createCoworker";
        } else {
            return "admin/index";
        }
    }

    @RequestMapping(value = "signUpUser", method = RequestMethod.POST)
    public String signUpUser(User user,
                             @RequestParam(value = "userType") String userType,
                             @RequestParam(value = "companyName") String companyName,
                             @RequestParam(value = "secretKey") String secretKey) {
        user = setCustomerId(user, companyName, secretKey, userType);
        boolean success = userService.saveWithGeneratePassword(user);
        return success ? "csr/index" : "csr/signUp";

    }

    private User setCustomerId(User user, String companyName, String secretKey, String userType) {
        user.setRole(Role.getRoleByName(userType));
        Integer customerId;
        if (Role.Individual.equals(user.getRole())) {
            Customer customer = new Customer(user.getEmail(), user.getPassword());
            customerDAO.save(customer);
            customerId = customerDAO.getCustomerId(user.getEmail(), user.getPassword());
        } else {
            customerId = customerDAO.getCustomerId(companyName, secretKey);
        }
        user.setCustomerId(customerId);
        return user;
    }


    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(User user,
                         @RequestParam(value = "userType") String userType,
                         @RequestParam(value = "companyName") String companyName,
                         @RequestParam(value = "secretKey") String secretKey) {
        user = setCustomerId(user, companyName, secretKey, userType);
        boolean success = userService.save(user);
        if (!success) {
            return "signUp";
        } else {
            return "login";
        }
    }
}
