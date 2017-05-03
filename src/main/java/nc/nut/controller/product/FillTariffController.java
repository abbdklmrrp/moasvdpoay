package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    String fillTariffWithService(Model model, HttpSession session) {

        List<Product> tariffs = productDao.getAllFreeTariffs();
        Map<String, List<Product>> allServices = productDao.getAllServicesWithCategory();
//        List<Product> allServices = productDao.getAllServicesWithCategory();
//        List<ProductCategories> productCategories = productDao.findProductCategories();
        for (Map.Entry<String, List<Product>> s : allServices.entrySet()) {
            model.addAttribute(s.getKey(), s.getValue());
            for (Product p : s.getValue()) {
                System.out.println(s.getKey() + " " + p.getName());
            }
        }
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("allServices", allServices);
        session.setAttribute("tariffs", tariffs);
        session.setAttribute("allServices", allServices);

//        model.addAttribute("productCategories", productCategories);
        return "admin/fillTariff";
    }

    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.POST)
    String identifyTariff(@RequestParam(value = "tariffId") Integer tariffId,
                          @RequestParam(value = "selectto") String services,
                          Model model, HttpSession session) {

        boolean checkUniqueCategoryServices = productService.checkUniqueCategoryServices(services);
        if (!checkUniqueCategoryServices) {
            model.addAttribute("tariffs", session.getAttribute("tariffs"));
            model.addAttribute("allServices", session.getAttribute("allServices"));
            model.addAttribute("errorUniqueCategory", "Duplicate category");
            return "admin/fillTariff";
        }
        productService.fillTariff(services, tariffId);

        return "admin/index";
    }

    @ExceptionHandler({ServletRequestBindingException.class})
    public ModelAndView resolveException(Exception exception, HttpServletRequest request) {
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null) {
            if (exception instanceof MissingServletRequestParameterException) {
                logger.error("Services must be selected", exception.getMessage());
                outputFlashMap.put("errors", "Please select new services: ");

            } else {
                logger.error("Unexpected error", exception.getMessage());
                outputFlashMap.put("errors", "Unexpected error: " + exception.getMessage());
            }
        }
        return new ModelAndView("redirect:/admin/fillTariff");
    }
}

