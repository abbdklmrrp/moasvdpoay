package jtelecom.controller.admin;

import jtelecom.dao.entity.CustomerType;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
import jtelecom.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Rysakova Anna on 26.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class FillTariffController {

    private static final String ERROR_UNIQUE_CATEGORY = "Category already exists";
    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static final String ERROR_FILL_IN_TARIFF_SERVICES = "Please, select services to tariff";
    private static final String ERROR_EXIST_PRODUCT = "Sorry, product with such ID does not exist in the database";
    private static Logger logger = LoggerFactory.getLogger(FillTariffController.class);
    @Resource
    private ProductDao productDao;
    @Resource
    private ProductService productService;

    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.GET)
    public ModelAndView fillTariffWithService(@RequestParam(value = "tariffId") Integer tariffId,
                                              ModelAndView mav) {

        Map<String, List<Product>> allServicesWithCategory = productDao.getAllServicesWithCategory();
        logger.debug("Get all service's categories {} ", allServicesWithCategory.toString());

        mav.addObject("allServicesWithCategory", allServicesWithCategory);
        mav.addObject("tariffId", tariffId);
        mav.setViewName("newPages/admin/fillTariff");
        return mav;
    }

    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.POST)
    public ModelAndView identifyTariff(@RequestParam(value = "tariffId") Integer tariffId,
                                       @RequestParam(value = "selectedService") Integer[] servicesIdArray,
                                       ModelAndView mav) {

        logger.debug("Get all tariff ID {} ", tariffId);
        try {
            Product tariff = productDao.getById(tariffId);
            logger.debug("Checked that the tariff exists {} ", tariff.toString());
        } catch (DataAccessException ex) {
            mav.addObject("error", ERROR_EXIST_PRODUCT);
            logger.error("Product with ID = {}  does not exist in the database ", tariffId);
        }

        if (servicesIdArray == null) {
            logger.error("Incoming data error with services ");
            mav.addObject("error", ERROR_FILL_IN_TARIFF_SERVICES);
            mav.setViewName("newPages/admin/fillTariff");
            return mav;
        }

        try {
            productService.fillInTariffWithServices(tariffId, servicesIdArray);
            logger.debug("Fill in tariff with services to database");
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {} ", ex.getMessage());
            mav.addObject("error ", ERROR_IN_CONNECTION);
            mav.setViewName("redirect:/admin/fillTariff");
            return mav;
        }
        Product product = productDao.getById(tariffId);
        if (product.getCustomerType() == CustomerType.Business) {
            mav.setViewName("redirect:/admin/getProducts");
        }
        if (product.getCustomerType() == CustomerType.Residential) {
            mav.setViewName("redirect:/admin/fillTariffsPrices?id=" + tariffId);
        }
        return mav;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView resolveException(Exception exception, HttpServletRequest request, ModelAndView mav) {
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null) {
            if (exception instanceof MissingServletRequestParameterException) {
                logger.error(ERROR_FILL_IN_TARIFF_SERVICES, exception.getMessage());
                outputFlashMap.put("error", ERROR_FILL_IN_TARIFF_SERVICES);

            } else {
                logger.error("Unexpected error", exception.getMessage());
                outputFlashMap.put("error", "Unexpected error: " + exception.getMessage());
            }
        }
        mav.setViewName("admin/index");
        return mav;
    }
}

