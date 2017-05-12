package jtelecom.controller.csr;


import jtelecom.dao.customer.Customer;
import jtelecom.dao.customer.CustomerDAO;
import jtelecom.dao.entity.CustomerType;
import jtelecom.dao.user.Role;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.security.SecurityAuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Moiseienko Petro, Nikita Alistratenko
 * @since 26.04.2017.
 */
@RestController
@RequestMapping({"csr", "admin"})
public class CustomerController {
    @Resource
    private CustomerDAO customerDAO;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    //DO NOT REMOVE
    int custID = 0;


    @RequestMapping(value = {"getCreateCustomer"}, method = RequestMethod.GET)
    public ModelAndView getCreateCustomer() {
        ModelAndView modelAndView = new ModelAndView();
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        if (user.getRole().equals(Role.Admin)) {
            modelAndView.setViewName("newPages/admin/RegNewCustomer");
        } else {
            modelAndView.setViewName("newPages/csr/RegNewCustomer");
        }
        modelAndView.addObject("customer", new Customer());

        return modelAndView;
    }

    @RequestMapping(value = {"createCustomer"}, method = RequestMethod.POST)
    public ModelAndView createCustomer(Customer customer) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        if (user.getRole().equals(Role.Admin)) {
            modelAndView.setViewName("newPages/admin/RegNewCustomer");
        } else {
            modelAndView.setViewName("newPages/csr/RegNewCustomer");
        }
        customer.setCustomerType(CustomerType.Business);
        boolean success = customerDAO.save(customer);
        if (!success) {
            logger.error("Can't create customer");
            modelAndView.setViewName("newPages/includes/error");
        } else {
            logger.debug("created customer " + customer.getName());
        }
        return modelAndView;
    }

    @RequestMapping(value = {"getCustomers"}, method = RequestMethod.GET)
    public ModelAndView getProducts() {
        return new ModelAndView("newPages/csr/Customers");
    }

    @RequestMapping(value = {"allCustomers"}, method = RequestMethod.GET)
    public ListHolder servicesByTariff(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        if (!sort.isEmpty()) {
            String[] array = sort.split("=");
            if ("true".equals(array[1])) {
                sort = array[0] + " " + "ASC";
            } else {
                sort = array[0] + " " + "DESC";
            }
        }
        int start = request.getStartBorder();
        int length = request.getLength();
        String search = request.getSearch();
        List<Customer> data = customerDAO.getLimitedQuantityCustomer(start, length, sort, search);
        int size = customerDAO.getCountCustomersWithSearch(search);
        return ListHolder.create(data, size);
    }

    @RequestMapping(value = "getCustomerInfo/{id}", method = RequestMethod.GET)
    public ModelAndView getCustomerInfoPage(@PathVariable("id") int customerID) {
        custID = customerID;
        ModelAndView mw = new ModelAndView();
        Customer customer = customerDAO.getById(customerID);
        logger.info("customer: " + customer.toString());
        List<User> usersOfCustomer = customerDAO.getAllUsers(customerID);
        logger.info("users: " + Objects.toString(usersOfCustomer));
        mw.addObject("customer", customer);
        mw.addObject("userList", usersOfCustomer);
        mw.setViewName("newPages/csr/CustomerInfoPage");
        return mw;
    }

    @RequestMapping(value = {"AllUsersOfCustomer"}, method = RequestMethod.GET)
    public ListHolder allUsersOfCustomer(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        if (!sort.isEmpty()) {
            String[] array = sort.split("=");
            if ("true".equals(array[1])) {
                sort = array[0] + " " + "ASC";
            } else {
                sort = array[0] + " " + "DESC";
            }
        }
        int start = request.getStartBorder();
        int length = request.getLength();
        String search = request.getSearch();
        List<User> data = userDAO.getLimitedQuantityUsersOfCustomer(start, length, sort, search, custID);
        int size = userDAO.getCountUsersWithSearchOfCustomer(search, custID);
        return ListHolder.create(data, size);
    }
}
