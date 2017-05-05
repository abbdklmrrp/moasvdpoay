package nc.nut.controller;


import nc.nut.dao.customer.Customer;
import nc.nut.dao.customer.CustomerDAO;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.googleMaps.ServiceGoogleMaps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(method = RequestMethod.GET, value = {"/registration"})
    public String registration(HttpServletRequest request) {
        if (request.getRequestURI().equals("/csr/registration")) {
            return "csr/signUp";
        } else if (request.getRequestURI().equals("/admin/registration")) {
            return "admin/createCoworker";
        } else {
            return "signUp";
        }
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
                                 @RequestParam(value = "userType") String userType,
                                 @RequestParam(value = "city") String city,
                                 @RequestParam(value = "street") String street,
                                 @RequestParam(value = "building") String building) {
        user = getUser(user, city, street, building);
        user.setRole(Role.getRoleByName(userType));
        boolean success = userDAO.save(user);
        if (!success) {
            return "admin/createCoworker";
        } else {
            return "admin/index";
        }
    }


    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUpUser(User user,
                             @RequestParam(value = "userType") String userType,
                             @RequestParam(value = "companyName") String companyName,
                             @RequestParam(value = "secretKey") String secretKey,
                             @RequestParam(value = "city") String city,
                             @RequestParam(value = "street") String street,
                             @RequestParam(value = "building") String building,
                             HttpServletRequest request) {
        Integer customerId = 0;
        user = getUser(user, city, street, building);
        if ("INDIVIDUAL".equals(userType)) {
            Customer customer = new Customer(user.getEmail(), user.getPassword());
            customerDAO.save(customer);
            customerId = customerDAO.getCustomerId(user.getEmail(), user.getPassword());
        } else if ("LEGAL".equals(userType)) {
            customerId = customerDAO.getCustomerId(companyName, secretKey);
        }
        if (customerId == null) {
            return "signUp";
        } else {
            user.setCustomerId(customerId);
            user.setRole(Role.getRoleByName(userType));
        }
        boolean success = userDAO.save(user);
        if (!success) {
            if (request.getRequestURI().equals("csr/signUp")) {
                return "csr/signUp";
            }
            return "signUp";
        } else {
            if (request.getRequestURI().equals("/csr/signUp")) {
                return "csr/index";
            }
            return "login";
        }
    }
}
