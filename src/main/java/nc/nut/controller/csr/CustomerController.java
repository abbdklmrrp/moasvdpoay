package nc.nut.controller.csr;


import nc.nut.dao.customer.Customer;
import nc.nut.dao.customer.CustomerDAO;
import nc.nut.dao.entity.CustomerType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author Moiseienko Petro
 * @since 26.04.2017.
 */
@Controller
@RequestMapping({"csr"})
public class CustomerController {

    @Resource
    private CustomerDAO customerDAO;

    @RequestMapping(value = {"getCreateCustomer"}, method = RequestMethod.GET)
    String getCreateCustomer() {
        return "csr/createCustomer";
    }

    @RequestMapping(value = {"createCustomer"}, method = RequestMethod.POST)
    String createCustomer(@RequestParam(value = "companyName") String companyName,
                          @RequestParam(value = "secretKey") String secretKey) {
        Customer customer = new Customer(companyName, secretKey);
        customer.setCustomerType(CustomerType.Business);
        customerDAO.save(customer);
        return "csr/index";
    }


}
