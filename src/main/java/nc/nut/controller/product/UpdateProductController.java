package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    @RequestMapping(value = {"updateTariff"}, method = RequestMethod.GET)
    String getTariffForUpdate(Model model) {

        List<Product> allServices = productDao.getAllServices();
        List<Product> allTariffs = productDao.getAllTariffs();

        model.addAttribute("allServices", allServices);
        model.addAttribute("allTariffs", allTariffs);

        return "admin/updateTariff";
    }

    @RequestMapping(value = {"updateService"}, method = RequestMethod.POST)
    String updateService(Product product, HttpSession session) {
        Integer id = (Integer) session.getAttribute("productId");
        product.setId(id);
        productService.updateProduct(product);
        session.removeAttribute("productId");
        return "redirect:/admin/index";
    }

    @RequestMapping(value = {"updateTariff"}, method = RequestMethod.POST)
    String updateTariff(Product product,
                        @RequestParam(value = "selectto") String services,
                        Model model, HttpSession session) {
        Integer id = (Integer) session.getAttribute("productId");
        product.setId(id);
        productService.updateFillTariff(services, product);
        productService.updateTariffWithNewServices(services, product);
        productService.updateProduct(product);
        session.removeAttribute("productId");
        return "redirect:/admin/index";
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView resolveException(Exception exception,
                                         HttpServletRequest request,
                                         HttpSession session) {

        ModelAndView mav = new ModelAndView("redirect:/admin/getDetailsProduct");
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null) {
            if (exception instanceof MissingServletRequestParameterException) {
                logger.error("Services must be selected", exception.getMessage());
                mav.addObject("servicesByTariff", session.getAttribute("servicesByTariff"));
                mav.addObject("servicesNotInTariff", session.getAttribute("servicesNotInTariff"));
                mav.addObject("id", session.getAttribute("id"));
                outputFlashMap.put("errors", "Select all new services: ");
            } else {
                logger.error("Unexpected error", exception.getMessage());
                outputFlashMap.put("errors", "Unexpected error: " + exception.getMessage());
            }
        }
        return mav;
    }
}
