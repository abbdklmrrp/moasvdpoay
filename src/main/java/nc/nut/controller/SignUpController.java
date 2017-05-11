package nc.nut.controller;


import nc.nut.dao.customer.Customer;
import nc.nut.dao.customer.CustomerDAO;
import nc.nut.dao.entity.CustomerType;
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
        ModelAndView model = new ModelAndView("newPages/Registration");
        model.addObject("user", new User());
        return model;
    }

    @RequestMapping(value = "registrationFromCsr", method = RequestMethod.GET)
    public ModelAndView registrationFromCsr() {
        ModelAndView model = new ModelAndView("newPages/csr/RegNewUser");
        List<String> customersName = customerDAO.getAllBusinessCustomersName();
        model.addObject("customersName", customersName);
        model.addObject("user", new User());
        return model;
    }

    @RequestMapping(value = "registrationFromAdmin", method = RequestMethod.GET)
    public ModelAndView registrationFromAdmin() {
        ModelAndView model = new ModelAndView("newPages/admin/RegNewUser");
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
        if (success) {
            return "newPages/admin/RegNewUser";
        } else {
            return "newPages/includes/error";
        }
    }

    @RequestMapping(value = "signUpUser", method = RequestMethod.POST)
    public String signUpUser(User user,
                             @RequestParam(value = "userType") String userType,
                             @RequestParam(value = "companyName") String companyName,
                             @RequestParam(value = "secretKey") String secretKey) {
        user = setCustomerId(user, companyName, secretKey, userType);
        if(user==null){
            return "newPages/includes/error";
        }
        boolean success = userService.saveWithGeneratePassword(user);
        return success ? "newPages/csr/RegNewUser" : "newPages/includes/error";

    }

    private User setCustomerId(User user, String companyName, String secretKey, String userType) {
        user.setRole(Role.getRoleByName(userType));
        Integer customerId;
        if (Role.Individual.equals(user.getRole())) {
            Customer customer = new Customer(user.getEmail(), user.getPassword());
            customer.setCustomerType(CustomerType.Residential);
            boolean success=customerDAO.save(customer);
            if(!success){
                return null;
            }
            customerId = customerDAO.getCustomerId(user.getEmail(), user.getPassword());
        } else {
            customerId = customerDAO.getCustomerId(companyName, secretKey);
        }
        user.setCustomerId(customerId);
        return user;
    }


    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(User user) {
        Integer customerId;
        Customer customer = new Customer(user.getEmail(), user.getPassword());
        customer.setCustomerType(CustomerType.Residential);
        boolean successCustomer = customerDAO.save(customer);
        if (!successCustomer) {
            return "newPages/includes/error";
        } else {
            customerId = customerDAO.getCustomerId(user.getEmail(), user.getPassword());
            user.setCustomerId(customerId);
            user.setRole(Role.Individual);
            boolean success = userService.save(user);
            if (!success) {
                return "newPages/includes/error";
            } else {
                return "newPages/Login";
            }
        }
    }
}
