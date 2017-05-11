package nc.nut.controller.admin;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.services.ProductService;
import nc.nut.util.ProductUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
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
import java.util.Objects;

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

    private static final String ERROR_FILL_IN_TARIFF_SERVICES = "Please, select services to tariff";
    private static final String ERROR_UNEXPECTED = "Unexpected error";
    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static final String ERROR_TYPE = "Wrong type of input data";

    private static Logger logger = LoggerFactory.getLogger(UpdateProductController.class);

    @RequestMapping(value = {"updateService"}, method = RequestMethod.POST)
    public ModelAndView updateService(ModelAndView mav,
                                      Product product, HttpSession session) {

        Integer id = (Integer) session.getAttribute("productId");
        logger.debug("Receive service's id {} ", id);
        product.setId(id);
        logger.debug("Write ID to product {} ", product.getId());
        boolean isUpdate = productService.updateProduct(product);
        logger.debug("Service was update {} ", isUpdate);
        session.removeAttribute("productId");
        logger.debug("Attribute 'productId' was removed from session");
        mav.setViewName("redirect:/admin/getProfile");
        return mav;
    }

    @RequestMapping(value = {"updateTariff"}, method = RequestMethod.POST)
    public ModelAndView updateTariff(Product product,
                                     @RequestParam(value = "selectedService", required = false) String services,
                                     ModelAndView mav, HttpSession session) {

        Integer id = (Integer) session.getAttribute("productId");
        logger.debug("Receive tariff's id {} ", id);
        Product tariff = productDao.getById(id);
        logger.debug("Checked that the tariff exists {} ", tariff.toString());

        if (Objects.equals(services, null) || Objects.equals(tariff, null)) {
            logger.error("Incoming data error with services {} ", Objects.equals(services, null));
            logger.error("Incoming data error with tariff {} ", Objects.equals(tariff, null));
            mav.addObject("error", ERROR_FILL_IN_TARIFF_SERVICES);
            mav.setViewName("admin/updateTariff");
            return mav;
        }
        product.setId(id);
        logger.debug("Write ID to product {} ", product.getId());

        try {
            Integer[] servicesIdArray = ProductUtil.convertStringToIntegerArray(services);
            logger.debug("Convert a string array of service's ID to an integer array");
            productService.updateFillingOfTariffsWithServices(servicesIdArray, product);
            logger.debug("Update tariff: removed unnecessary services, added new services");
            productService.updateProduct(product);
            logger.debug("Update fields of service");
        } catch (NumberFormatException e) {
            mav.addObject("error ", ERROR_TYPE);
            logger.error("Wrong parameter's type ", e.getMessage());
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {}", ex.getMessage());
            mav.addObject("error ", ERROR_IN_CONNECTION);
            mav.setViewName("admin/fillTariff");
            return mav;
        }

        session.removeAttribute("productId");
        logger.debug("Attribute 'productId' was removed from session");
        mav.setViewName("redirect:/admin/getProfile");
        return mav;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView resolveException(Exception exception, HttpServletRequest request, ModelAndView mav) {
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null) {
            if (exception instanceof MissingServletRequestParameterException) {
                logger.error("Services must be selected", exception.getMessage());
                outputFlashMap.put("error", ERROR_FILL_IN_TARIFF_SERVICES);
            } else {
                logger.error("Unexpected error", exception.getMessage());
                outputFlashMap.put("error", ERROR_UNEXPECTED + exception.getMessage());
            }
        }
        mav.setViewName("admin/index");
        return mav;
    }
}
