package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Anna on 30.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class UpdateProductController {

    @Resource
    private ProductDao productDao;
    @Resource
    private ProductService productService;
    private Logger logger = LoggerFactory.getLogger(UpdateProductController.class);

    @RequestMapping(value = {"updateProduct"}, method = RequestMethod.GET)
    String getServiceForUpdate(Model model) {

        List<Product> allServices = productDao.getAllServices();
        model.addAttribute("allServices", allServices);

        return "admin/updateProduct";
    }


    @RequestMapping(value = {"updateTariff"}, method = RequestMethod.GET)
    String getTariffForUpdate(Model model) {

        List<Product> allServices = productDao.getAllServices();
        List<Product> allTariffs = productDao.getAllTariffs();

        model.addAttribute("allServices", allServices);
        model.addAttribute("allTariffs", allTariffs);

        return "admin/updateTariff";
    }

    @RequestMapping(value = {"updateProduct"}, method = RequestMethod.POST)
    String updateService(Product product) {

        productService.updateProduct(product);

        return "admin/index";
    }

    @RequestMapping(value = {"updateTariff"}, method = RequestMethod.POST)
    String updateTariff(Product product,
                        @RequestParam(value = "selectto") String services,
                        Model model, HttpSession session
    ) {
        Integer id = (Integer) session.getAttribute("productId");
        product.setId(id);
        productService.updateFillTariff(services, product);
        productService.updateTariffWithNewServices(services, product);
        productService.updateProduct(product);

        return "admin/index";
    }

}
