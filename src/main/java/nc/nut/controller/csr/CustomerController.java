package nc.nut.controller.csr;


import nc.nut.dao.customer.Customer;
import nc.nut.dao.customer.CustomerDAO;
import nc.nut.dao.entity.CustomerType;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.security.SecurityAuthenticationHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author Moiseienko Petro
 * @since 26.04.2017.
 */
@Controller
@RequestMapping({"csr","admin"})
public class CustomerController {


    @Resource
    private CustomerDAO customerDAO;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;


    @RequestMapping(value = {"getCreateCustomer"}, method = RequestMethod.GET)
    public ModelAndView getCreateCustomer() {
        ModelAndView modelAndView=new ModelAndView();
        User user=userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        if(user.getRole().equals(Role.Admin)){
          modelAndView.setViewName("newPages/admin/RegNewCustomer");
        }else{
            modelAndView.setViewName("newPages/csr/RegNewCustomer");
        }
        modelAndView.addObject("customer",new Customer());

        return modelAndView;
    }

    @RequestMapping(value = {"createCustomer"}, method = RequestMethod.POST)
    public ModelAndView createCustomer(Customer customer) {
        ModelAndView modelAndView=new ModelAndView();
        User user=userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        if(user.getRole().equals(Role.Admin)){
            modelAndView.setViewName("newPages/csr/createCustomer");
        }else{
            modelAndView.setViewName("newPages/admin/createCustomer");
        }
        customer.setCustomerType(CustomerType.Business);
        boolean success = customerDAO.save(customer);
        if(!success){
            modelAndView.setViewName("newPages/includes/error");
        }
       return modelAndView;
    }


}
