package nc.nut.controller.user;

import nc.nut.dao.product.Product;
import nc.nut.dao.user.User;
import nc.nut.dao.user.UserDAO;
import nc.nut.security.SecurityAuthenticationHelper;
import nc.nut.services.ProductService;
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
@RequestMapping({"user/residential", "user/business"})
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
        return "newPages/user/business/Product";
    }

}
