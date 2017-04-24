package nc.nut.controller.product;

import nc.nut.product.Product;
import nc.nut.product.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna on 23.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class TariffController {

    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"identifyTariffService"}, method = RequestMethod.GET)
    String addProduct(Model model) {

        List<Product> tariffs = productDao.getTariffs();

        List<Product> servicesWI = productDao.getServices();
//        List<Product> services2G = productDao.getServices();
//        List<Product> services3G = productDao.getServices();
//        List<Product> services4G = productDao.getServices();
//        List<Product> servicesMC = productDao.getServices();
        model.addAttribute("servicesWI", servicesWI);
        model.addAttribute("tariffs", tariffs);
        return "admin/identifyTariff";
    }

    //TODO: add modelAttribute, check "typeId"
    @RequestMapping(value = {"identifyTariff"}, method = RequestMethod.POST)
    String createProduct(@RequestParam(value = "tariffs") int tariffs,
                         @RequestParam(value = "services") int services)
//                         @RequestParam(value = "serviceWI") int serviceWI,
//                         @RequestParam(value = "service2G") int service2G,
//                         @RequestParam(value = "service3G") int service3G,
//                         @RequestParam(value = "service4G") int service4G,
//                         @RequestParam(value = "serviceMC") int serviceMC)
    {
        productDao.identifyTariff(tariffs, services);
//        productDao.identifyTariff(tariffId, serviceWI);
//        productDao.identifyTariff(tariffId, service2G);
//        productDao.identifyTariff(tariffId, service3G);
//        productDao.identifyTariff(tariffId, service4G);
//        productDao.identifyTariff(tariffId, serviceMC);
        return "admin/index";
    }

}

