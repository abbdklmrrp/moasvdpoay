package jtelecom.controller.admin;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.product.ProductDao;
import jtelecom.services.ProductService;
import jtelecom.util.ProductUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

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
    public ModelAndView fillTariffWithService(ModelAndView mav,
                                              @ModelAttribute("productId") Integer productId) {
        logger.debug("Get all tariff ID {} ", productId);
        List<ProductCategories> productCategories = productDao.findProductCategories();
        logger.debug("Get all service's categories {} ", productCategories.toString());

//        ra.addFlashAttribute("productId", productId);
        mav.addObject("productCategories", productCategories);
        mav.setViewName("newPages/admin/fillTariff");
        return mav;
    }

    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.POST)
    public ModelAndView identifyTariff(@RequestParam(value = "tariffId") Integer tariffId,
                                       @RequestParam(value = "selectedService") String services,
                                       ModelAndView mav) {

        try {
            Product tariff = productDao.getById(tariffId);
            logger.debug("Checked that the tariff exists {} ", tariff.toString());
        } catch (DataAccessException ex) {
            mav.addObject("error", ERROR_EXIST_PRODUCT);
            logger.error("Product with ID = {}  does not exist in the database ", tariffId);
        }

        if (services == null) {
            logger.error("Incoming data error with services ");
            mav.addObject("error", ERROR_FILL_IN_TARIFF_SERVICES);
            mav.setViewName("admin/fillTariff");
            return mav;
        }

        Integer[] servicesIdArray = ProductUtil.convertStringToIntegerArray(services);
        logger.debug("Convert a string array of service's ID to an integer array {} ", Arrays.toString(servicesIdArray));

        boolean checkUniqueCategoryServices = productService.isCategoriesUnique(servicesIdArray);
        logger.debug("Check that the new category does not exist in the database {} ", checkUniqueCategoryServices);
        if (!checkUniqueCategoryServices) {
            logger.error("Category already exist in database");
            mav.addObject("error", ERROR_UNIQUE_CATEGORY);
            mav.setViewName("admin/fillTariff");
            return mav;
        }

        try {
            productService.fillInTariffWithServices(tariffId, servicesIdArray);
            logger.debug("Fill in tariff with services to database");
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {} ", ex);
            mav.addObject("error ", ERROR_IN_CONNECTION);
            mav.setViewName("admin/fillTariff");
            return mav;
        }
        mav.setViewName("redirect:/admin/getProfile");
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

