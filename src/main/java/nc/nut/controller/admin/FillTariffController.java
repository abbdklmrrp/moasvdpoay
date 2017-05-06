package nc.nut.controller.admin;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
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

    private Logger logger = LoggerFactory.getLogger(FillTariffController.class);

    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.GET)
    public String fillTariffWithService(Model model, HttpSession session) {

        List<Product> tariffs = productDao.getAllFreeTariffs();
        Map<String, List<Product>> allServices = productDao.getAllServicesWithCategory();
//        List<Product> allServices = productDao.getAllServicesWithCategory();
        List<ProductCategories> productCategories = productDao.findProductCategories();
        for (Map.Entry<String, List<Product>> s : allServices.entrySet()) {
            model.addAttribute(s.getKey(), s.getValue());
        }
        model.addAttribute("allServices", productCategories);
        model.addAttribute("tariffs", tariffs);
//        model.addAttribute("allServices", allServices);
        session.setAttribute("tariffs", tariffs);
        session.setAttribute("allServices", allServices);

//        model.addAttribute("productCategories", productCategories);
        return "admin/fillTariff";
    }

    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.POST)
    public String identifyTariff(@RequestParam(value = "tariffId") Integer tariffId,
                                 @RequestParam(value = "selectedService") String services,
                                 Model model) {

        String[] servicesArray = services.split(",");
        boolean checkUniqueCategoryServices = productService.checkUniqueCategoryServices(servicesArray);
        if (!checkUniqueCategoryServices) {

            model.addAttribute("errorUniqueCategory", "Duplicate category");
            return "admin/fillTariff";
        }
        productService.fillTariff(servicesArray, tariffId);

        return "admin/index";
    }
//
//    @ExceptionHandler({ServletRequestBindingException.class})
//    public ModelAndView resolveException(Exception exception, HttpServletRequest request) {
//        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
//        if (outputFlashMap != null) {
//            if (exception instanceof MissingServletRequestParameterException) {
//                logger.error("Services must be selected", exception.getMessage());
//                outputFlashMap.put("errors", "Select all new services: ");
//
//            } else {
//                logger.error("Unexpected error", exception.getMessage());
//                outputFlashMap.put("errors", "Unexpected error: " + exception.getMessage());
//            }
//        }
//        return new ModelAndView("redirect:/admin/fillTariff");
//    }
}

