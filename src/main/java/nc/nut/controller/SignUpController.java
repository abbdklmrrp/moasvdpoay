package nc.nut.controller;


import nc.nut.dao.customer.Customer;
import nc.nut.dao.customer.CustomerDAO;
import nc.nut.dao.entity.CustomerType;
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

    private Customer customer;

    @RequestMapping(method = RequestMethod.GET, value = {"/registration"})
    public String goChoose(HttpServletRequest request) {
        if (request.getRequestURI().equals("/csr/registration")) {
            return "csr/signUp";
        } else if (request.getRequestURI().equals("/admin/registration")) {
            return "admin/createCoworker";
        } else {
            return "signUp";
        }
    }

    private User getUser(String name,
                         String surname,
                         String email,
                         String password,
                         String phone,
                         String city,
                         String street,
                         String building) {
        User user = new User();
        String address = city + ", " + street + ", " + building;
        ServiceGoogleMaps maps = new ServiceGoogleMaps();
        String place = maps.getRegion(address);
        int placeId = userDAO.findPlaceId(place);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);
        user.setPlaceId(placeId);
        return user;

    }

    @RequestMapping(value = "/signUpCoworker", method = RequestMethod.POST)
    public String signUpCoworker(@RequestParam(value = "userType") String userType,
                                 @RequestParam(value = "firstName") String name,
                                 @RequestParam(value = "lastName") String surname,
                                 @RequestParam(value = "email") String email,
                                 @RequestParam(value = "password") String password,
                                 @RequestParam(value = "phoneNumber") String phone,
                                 @RequestParam(value = "city") String city,
                                 @RequestParam(value = "street") String street,
                                 @RequestParam(value = "building") String building) {
        User user = getUser(name, surname, email, password, phone, city, street, building);
        int roleId = userDAO.findRole(userType);
        user.setRoleId(roleId);
        boolean success = userDAO.save(user);
        if (!success) {
            return "admin/createCoworker";
        } else {
            return "admin/index";
        }
    }


    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUpUser(@RequestParam(value = "userType") String userType,
                             @RequestParam(value = "companyName") String companyName,
                             @RequestParam(value = "secretKey") String secretKey,
                             @RequestParam(value = "firstName") String name,
                             @RequestParam(value = "lastName") String surname,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "phoneNumber") String phone,
                             @RequestParam(value = "city") String city,
                             @RequestParam(value = "street") String street,
                             @RequestParam(value = "building") String building,
                             HttpServletRequest request) {
        Integer customerId = 0;
        User user = getUser(name, surname, email, password, phone, city, street, building);
        if (userType.equals("INDIVIDUAL")) {
            customer = new Customer(email, password);
            customer.setCustomerType(CustomerType.Individual);
            customerDAO.save(customer);
            customerId = customerDAO.getCustomerId(email, password);
        } else if ("LEGAL".equals(userType)) {
            customerId = customerDAO.getCustomerId(companyName, secretKey);
        }
        if (customerId == null) {
            return "signUp";
        } else {
            int roleId = userDAO.findRole(userType);
            user.setCustomerId(customerId);
            user.setRoleId(roleId);
        }
        boolean success = userDAO.save(user);
        if (!success) {
            return "signUp";
        } else {
            if (request.getRequestURI().equals("/csr/signUp")) {
                return "csr/index";
            } else
                return "login";
        }
    }
}
