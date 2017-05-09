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

    private Logger logger = LoggerFactory.getLogger(FillTariffController.class);

    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.GET)
    public ModelAndView fillTariffWithService(ModelAndView mav) {
        List<Product> tariffs = productDao.getAllFreeTariffs();
        List<ProductCategories> productCategories = productDao.findProductCategories();
        mav.addObject("allServices", productCategories);
        mav.addObject("tariffs", tariffs);
        mav.setViewName("admin/fillTariff");
        return mav;
    }

    // FIXME: 08.05.2017 all checks of parameter to one validate method
    @RequestMapping(value = {"fillTariff"}, method = RequestMethod.POST)
    public ModelAndView identifyTariff(@RequestParam(value = "tariffId") Integer tariffId,
                                       @RequestParam(value = "selectedService", required = false) String services,
                                       ModelAndView mav) {
        if (Objects.equals(services, null)) {
            mav.setViewName("admin/fillTariff");
            return mav;
        }
        Product tariff = productDao.getById(tariffId);
        if (tariff == null) {
            mav.setViewName("admin/fillTariff");
            return mav;
        }
        Integer[] servicesIdArray = ProductUtil.convertStringToIntegerArray(services);

        boolean checkUniqueCategoryServices = productService.isCategoriesUnique(servicesIdArray);
        if (!checkUniqueCategoryServices) {
            mav.addObject("errorUniqueCategory", "Category already exists");
            mav.setViewName("admin/fillTariff");
            return mav;
        }

        try {
            productService.fillInTariffWithServices(tariffId, servicesIdArray);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error in query to db ", ex);
            mav.addObject("errorSQL ", "Error in query to db");
            mav.setViewName("admin/fillTariff");
            return mav;
        }
        mav.setViewName("admin/index");
        return mav;
    }

    // FIXME: 08.05.2017 add a stub for error page
    @ExceptionHandler({Exception.class})
    public ModelAndView resolveException(Exception exception, HttpServletRequest request, ModelAndView mav) {
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null) {
            if (exception instanceof MissingServletRequestParameterException) {
                logger.error("Services must be selected", exception.getMessage());
                outputFlashMap.put("errors", "Select all new services: ");

            } else {
                logger.error("Unexpected error", exception.getMessage());
                outputFlashMap.put("errors", "Unexpected error: " + exception.getMessage());
            }
        }
        mav.setViewName("admin/index");
        return mav;
    }
}

