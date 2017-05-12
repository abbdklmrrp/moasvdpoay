package nc.nut.controller.csr;

import nc.nut.dao.customer.Customer;
import nc.nut.dao.entity.CustomerType;
import nc.nut.dao.user.Role;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.security.SecurityAuthenticationHelper;
import nc.nut.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * @author Moiseienko Petro
 * @since 12.05.2017.
 */
@Controller
@RequestMapping({"csr", "admin"})
public class CreateCustomerController {

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;
    @Resource
    private CustomerService customerService;

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
    public String createCustomer(Customer customer, RedirectAttributes attributes) {
        String redirect = "";
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        if (user.getRole() == Role.Admin) {
            redirect = "redirect:/admin/getCreateCustomer";
        }
        if (user.getRole() == Role.CSR) {
            redirect = "redirect:/csr/getCreateCustomer";
        }
        customer.setCustomerType(CustomerType.Business);
        String message = customerService.save(customer);
        if (message == null) {
            attributes.addFlashAttribute("msg", "Customer created successfully");
        } else {
            attributes.addFlashAttribute("msg", message);
        }
        return redirect;
    }
}
