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
 * Created by Rysakova Anna on 26.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class TariffController {

    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"addTariff"}, method = RequestMethod.GET)
    String addTariff() {
        return "admin/addTariff";
    }

    //TODO: add modelAttribute
    @RequestMapping(value = {"addTariff"}, method = RequestMethod.POST)
    String createTariff(
            @RequestParam(value = "duration") int duration,
            @RequestParam(value = "needProcessing") int needProcessing,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "status") int status
    ) {

        Product product = new Product();
        product.setDurationInDays(duration);
        product.setNeedProcessing(needProcessing);
        product.setName(name);
        product.setDescription(description);
        product.setStatus(status);

        productDao.save(product);

        return "admin/index";
    }

    //TODO : fix List of Services
    @RequestMapping(value = {"identifyTariffService"}, method = RequestMethod.GET)
    String identifyTariffService(Model model) {

        List<Product> tariffs = productDao.getAllFreeTariffs();

        List<Product> servicesWI = productDao.getAllServices("Wired Internet");
        List<Product> services2G = productDao.getAllServices("2G");
        List<Product> services3G = productDao.getAllServices("3G");
        List<Product> services4G = productDao.getAllServices("4G");
        List<Product> servicesMC = productDao.getAllServices("Mobile communication");

        model.addAttribute("servicesWI", servicesWI);
        model.addAttribute("services2G", services2G);
        model.addAttribute("services3G", services3G);
        model.addAttribute("services4G", services4G);
        model.addAttribute("servicesMC", servicesMC);

        model.addAttribute("tariffs", tariffs);

        return "admin/fillTariff";
    }

    //TODO: add modelAttribute
    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.POST)
    String identifyTariff(@RequestParam(value = "tariffs") int tariffs,
                          @RequestParam(value = "serviceWI") int serviceWI,
                          @RequestParam(value = "service2G") int service2G,
                          @RequestParam(value = "service3G") int service3G,
                          @RequestParam(value = "service4G") int service4G,
                          @RequestParam(value = "serviceMC") int serviceMC) {

        productDao.fillTariff(tariffs, serviceWI);
        productDao.fillTariff(tariffs, service2G);
        productDao.fillTariff(tariffs, service3G);
        productDao.fillTariff(tariffs, service4G);
        productDao.fillTariff(tariffs, serviceMC);

        return "admin/index";
    }


}

