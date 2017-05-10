package nc.nut.controller.admin;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
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
import java.util.List;
import java.util.Objects;

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

    private static final String ERROR_UNIQUE_CATEGORY = "Category already exists";
    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static final String ERROR_FILL_IN_TARIFF_SERVICES = "Please, select srvices to tariff";

    private static Logger logger = LoggerFactory.getLogger(FillTariffController.class);

    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.GET)
    public ModelAndView fillTariffWithService(ModelAndView mav) {
        List<Product> tariffs = productDao.getAllFreeTariffs();
        logger.debug("Get all the tariffs that are not filled with services");
        List<ProductCategories> productCategories = productDao.findProductCategories();
        logger.debug("Get all service's categories");

        mav.addObject("allServices", productCategories);
        mav.addObject("tariffs", tariffs);
        mav.setViewName("admin/fillTariff");
        return mav;
    }

    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.POST)
    public ModelAndView identifyTariff(@RequestParam(value = "tariffId") Integer tariffId,
                                       @RequestParam(value = "selectedService") String services,
                                       ModelAndView mav) {

        Product tariff = productDao.getById(tariffId);
        logger.debug("Checked that the tariff exists {} ", tariff.toString());

        if (Objects.equals(services, null) || Objects.equals(tariff, null)) {
            logger.error("Incoming data error with services {} ", Objects.equals(services, null));
            mav.addObject("errorFilling", ERROR_FILL_IN_TARIFF_SERVICES);
            logger.error("Incoming data error with tariff {} ", Objects.equals(tariff, null));
            mav.setViewName("admin/fillTariff");
            return mav;
        }

        Integer[] servicesIdArray = ProductUtil.convertStringToIntegerArray(services);
        logger.debug("Convert a string array of service's ID to an integer array");

        boolean checkUniqueCategoryServices = productService.isCategoriesUnique(servicesIdArray);
        logger.debug("Check that the new category does not exist in the database {} ", checkUniqueCategoryServices);
        if (!checkUniqueCategoryServices) {
            logger.error("Category already exist in database");
            mav.addObject("errorFilling", ERROR_UNIQUE_CATEGORY);
            mav.setViewName("admin/fillTariff");
            return mav;
        }

        try {
            productService.fillInTariffWithServices(tariffId, servicesIdArray);
            logger.debug("Fill in tariff with services to database");
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {} ", ex);
            mav.addObject("errorFilling ", ERROR_IN_CONNECTION);
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
                outputFlashMap.put("errors", ERROR_FILL_IN_TARIFF_SERVICES);

            } else {
                logger.error("Unexpected error", exception.getMessage());
                outputFlashMap.put("errors", "Unexpected error: " + exception.getMessage());
            }
        }
        mav.setViewName("admin/index");
        return mav;
    }
}

