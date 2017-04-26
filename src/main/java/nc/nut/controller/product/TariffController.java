package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rysakova Anna on 23.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class TariffController {

    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"identifyTariffService"}, method = RequestMethod.GET)
    String identifyTariffService(Model model) {

        List<Product> tariffs = productDao.getAllFreeTariffs();

        List<Product> servicesWI = productDao.getServices("Wired Internet");
        List<Product> services2G = productDao.getServices("2G");
        List<Product> services3G = productDao.getServices("3G");
        List<Product> services4G = productDao.getServices("4G");
        List<Product> servicesMC = productDao.getServices("Mobile communication");

        model.addAttribute("servicesWI", servicesWI);
        model.addAttribute("services2G", services2G);
        model.addAttribute("services3G", services3G);
        model.addAttribute("services4G", services4G);
        model.addAttribute("servicesMC", servicesMC);

        model.addAttribute("tariffs", tariffs);

        return "admin/identifyTariff";
    }

    //TODO: add modelAttribute, check "typeId"
    @RequestMapping(value = {"identifyTariff"}, method = RequestMethod.POST)
    String identifyTariff(@RequestParam(value = "tariffs") int tariffs,
                          @RequestParam(value = "serviceWI") int serviceWI,
                          @RequestParam(value = "service2G") int service2G,
                          @RequestParam(value = "service3G") int service3G,
                          @RequestParam(value = "service4G") int service4G,
                          @RequestParam(value = "serviceMC") int serviceMC) {

        productDao.identifyTariff(tariffs, serviceWI);
        productDao.identifyTariff(tariffs, service2G);
        productDao.identifyTariff(tariffs, service3G);
        productDao.identifyTariff(tariffs, service4G);
        productDao.identifyTariff(tariffs, serviceMC);

        return "admin/index";
    }

}

