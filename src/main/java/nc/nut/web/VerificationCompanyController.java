package nc.nut.web;

import nc.nut.customer.Customer;
import nc.nut.customer.CustomerDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Moiseienko Petro
 * @since 24.04.2017.
 */
@Controller
public class VerificationCompanyController {
    @Resource
    private CustomerDAO customerDAO;

    @RequestMapping(value = "/verificationCompany", method = RequestMethod.POST)
    public String verification(@RequestParam(value = "companyName") String name,
                               @RequestParam(value = "secretKey") String key, HttpServletRequest request) {
        Customer customer = customerDAO.checkCustomer(name, key);
        if (customer == null) {
            return "chooseCompany";
        } else {
            int id = customer.getId();
            HttpSession session = request.getSession();
            session.setAttribute("companyId", id);
            return "signUp";
        }
    }
}
