package nc.nut.controller.admin;

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

    @RequestMapping(value = {"updateService"}, method = RequestMethod.POST)
    public String updateService(Product product, HttpSession session) {
        Integer id = (Integer) session.getAttribute("productId");
        product.setId(id);
        productService.updateProduct(product);
        session.removeAttribute("productId");
        return "redirect:/admin/index";
    }

    @RequestMapping(value = {"updateTariff"}, method = RequestMethod.POST)
    public String updateTariff(Product product,
                        @RequestParam(value = "selectto") String services,
                        Model model, HttpSession session) {

        String[] servicesId = services.split(",");
        Integer id = (Integer) session.getAttribute("productId");
        product.setId(id);

        productService.updateFillTariff(servicesId, product);
//        productService.updateTariffWithNewServices(servicesId, product);
        productService.updateProduct(product);

        session.removeAttribute("productId");
        session.removeAttribute("servicesByTariff");
        session.removeAttribute("servicesNotInTariff");

        return "redirect:/admin/index";
    }

//    @ExceptionHandler({Exception.class})
//    public ModelAndView resolveException(Exception exception,
//                                         HttpServletRequest request,
//                                         HttpSession session) {
//
//        ModelAndView mav = new ModelAndView("redirect:/admin/getDetailsProduct");
//        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
//        if (outputFlashMap != null) {
//            if (exception instanceof MissingServletRequestParameterException) {
//                logger.error("Services must be selected", exception.getMessage());
//                mav.addObject("servicesByTariff", session.getAttribute("servicesByTariff"));
//                mav.addObject("servicesNotInTariff", session.getAttribute("servicesNotInTariff"));
//                mav.addObject("id", session.getAttribute("id"));
//                outputFlashMap.put("errors", "Select all new services: ");
//            } else {
//                logger.error("Unexpected error", exception.getMessage());
//                outputFlashMap.put("errors", "Unexpected error: " + exception.getMessage());
//            }
//        }
//        return mav;
//    }
}
