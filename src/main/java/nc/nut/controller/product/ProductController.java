package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna on 27.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ProductController {

    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"viewAllProducts"}, method = RequestMethod.GET)
    String viewAllProducts(Model model) {

        List<Product> allServices = productDao.getAllServices();
        List<Product> allTariffs = productDao.getAllTariffs();

        model.addAttribute("allServices", allServices);
        model.addAttribute("allTariffs", allTariffs);

        return "admin/viewAllProducts";
    }


}
