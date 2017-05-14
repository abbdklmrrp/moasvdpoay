package jtelecom.controller.user;

import jtelecom.dao.product.Product;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by Yuliya Pedash on 12.05.2017.
 */
@Controller
@RequestMapping({"residential/orders", "business/orders", "csr", "employee"})
public class ProductInfoController {
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private ProductService productService;
    @Resource
    private UserDAO userDAO;

    @RequestMapping(value = {"product"}, method = RequestMethod.GET)
    public String showOrdersForUser(Model model, @RequestParam(name = "productId") Integer productId) {
        User currentUser = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Product product = productService.getProductForUser(currentUser, productId);
        model.addAttribute("product", product);
        return "newPages/" + currentUser.getRole().getNameInLowwerCase() + "/Product";
    }

}
