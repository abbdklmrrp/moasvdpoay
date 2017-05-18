package jtelecom.controller.admin;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
import jtelecom.services.ProductService;
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

/**
 * Created by Anna on 30.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class UpdateProductController {

    private static final String ERROR_FILL_IN_TARIFF_SERVICES = "Please, select services to tariff";
    private static final String ERROR_UNEXPECTED = "Unexpected error";
    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static Logger logger = LoggerFactory.getLogger(UpdateProductController.class);
    @Resource
    private ProductDao productDao;
    @Resource
    private ProductService productService;

    @RequestMapping(value = {"updateProduct={id}"}, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateTariff(Product product, @PathVariable(value = "id") Integer id,
                                     ModelAndView mav) {

        logger.debug("Receive tariff's id {} ", id);
        try {
            Product tariff = productDao.getById(id);
            logger.debug("Checked that the tariff exists {} ", tariff.toString());
        } catch (DataAccessException ex) {
            logger.error("Product with ID = {}  does not exist in the database ", id);
        }
        product.setId(id);
        logger.debug("Write ID to product {} ", product.getId());

        try {
            boolean isUpdate = productService.updateProduct(product);
            logger.debug("Update fields of service with success {} ", isUpdate);
            mav.addObject("message", "success");
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {}", ex.getMessage());
            mav.addObject("error ", ERROR_IN_CONNECTION);
            mav.addObject("message", "Sorry, try again later");
            mav.setViewName("redirect:/admin/getDetailsProduct=" + id);
            return mav;
        }

        logger.debug("Attribute 'productId' was removed from session");
        mav.setViewName("redirect:/admin/getDetailsProduct=" + id);
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
