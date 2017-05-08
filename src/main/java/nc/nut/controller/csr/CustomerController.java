package nc.nut.controller.csr;


import nc.nut.dao.customer.Customer;
import nc.nut.dao.customer.CustomerDAO;
import nc.nut.dao.entity.CustomerType;
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

    @RequestMapping(value = {"getCreateCustomer"}, method = RequestMethod.GET)
    public ModelAndView getCreateCustomer() {
        ModelAndView modelAndView=new ModelAndView("csr/createCustomer");//add admin page if admin
        modelAndView.addObject("customer",new Customer());

        return modelAndView;
    }

    @RequestMapping(value = {"createCustomer"}, method = RequestMethod.POST)
    public String createCustomer(Customer customer) {
        customer.setCustomerType(CustomerType.Business);
        boolean success = customerDAO.save(customer);
        if (success) {
            return "csr/index";
        }
        return "csr/createCustomer";
    }


}
