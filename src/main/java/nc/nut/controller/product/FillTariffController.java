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
import java.util.List;
import java.util.Map;

/**
 * Created by Rysakova Anna on 26.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class FillTariffController {

    @Resource
    private ProductDao productDao;

    @Resource
    private ProductService productService;
    private Logger logger = LoggerFactory.getLogger(AddProductController.class);

    @RequestMapping(value = {"fillTariffService"}, method = RequestMethod.GET)
    String fillTariffWithService(Model model) {

        List<Product> tariffs = productDao.getAllFreeTariffs();
//        List<Product> allServices = productDao.getAllServicesWithCategory();
//        List<ProductCategories> productCategories = productDao.findProductCategories();
        Map<String, List<Product>> allServices = productDao.getAllServicesWithCategory();
        for (Map.Entry<String, List<Product>> s : allServices.entrySet()) {
            model.addAttribute(s.getKey(), s.getValue());
        }
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("allServices", allServices);
//        model.addAttribute("productCategories", productCategories);
        return "admin/fillTariff";
    }

    //TODO: add modelAttribute
    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.POST)
    String identifyTariff(@RequestParam(value = "tariffId") Integer tariffId,
                          @RequestParam(value = "selectto") String services,
                          Model model
    ) {
        try {
            productService.fillTariff(services, tariffId);
        } catch (NullPointerException e) {
            logger.info("Select services ", e);
            model.addAttribute("error", "Select services");
            return "admin/updateTariff";
        }
        return "admin/index";
    }


}

