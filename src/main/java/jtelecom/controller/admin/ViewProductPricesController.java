package jtelecom.controller.admin;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDAO;
import jtelecom.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Created by Anna Rysakova on 10.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ViewProductPricesController {

    private static Logger logger = LoggerFactory.getLogger(ViewProductController.class);
    private static final String ERROR_EXIST_PRODUCT = "Sorry, product with such ID does not exist in the database";

    @Resource
    private ProductDAO productDAO;
    @Resource
    private ProductService productService;

    @RequestMapping(value = "viewAllProducts", method = RequestMethod.GET)
    public ModelAndView getAllProductPrice(ModelAndView mav) {
        mav.setViewName("newPages/admin/viewProductPrices");
        return mav;
    }

    @RequestMapping(value = "viewProductPriceInRegions", method = RequestMethod.GET)
    public ModelAndView getProductPriceForRegions(@RequestParam("id") Integer productId,
                                                  ModelAndView mav) {
        Product validProduct = productService.isValidProduct(productId);
        if (!Objects.nonNull(validProduct)) {
            mav.addObject("error", ERROR_EXIST_PRODUCT);
            mav.setViewName("newPages/admin/viewProductPrices");
        }
        String productType = productDAO.getProductTypeByProductId(productId);
        logger.debug("Product type: {} ", productType);
        mav.addObject("id", productId);
        logger.debug("Product id: {}", productId);
        mav.addObject("productType", productType);
        mav.setViewName("newPages/admin/viewProductPriceInRegions");
        return mav;
    }
}