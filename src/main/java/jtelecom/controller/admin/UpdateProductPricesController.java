package jtelecom.controller.admin;

import jtelecom.dao.price.PriceDAO;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDAO;
import jtelecom.dto.PriceByRegionDTO;
import jtelecom.services.price.PriceService;
import jtelecom.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Anna Rysakova
 */
@Controller
@RequestMapping({"admin"})
public class UpdateProductPricesController {

    private static final String ERROR_FILL_IN_PRICE_BY_PRODUCT = "Please, check that the region was selected and price input";
    private static Logger logger = LoggerFactory.getLogger(UpdateProductPricesController.class);
    @Resource
    private PriceService priceService;
    @Resource
    private ProductService productService;
    @Resource
    private PriceDAO priceDAO;
    @Resource
    private ProductDAO productDAO;

    private static final String ERROR_EXIST_PRODUCT = "Sorry, product with such ID does not exist in the database";
    @RequestMapping(value = {"updateProductPrice"}, method = RequestMethod.GET)
    public ModelAndView getPriceByRegion(@RequestParam(value = "id") Integer productId,
                                         ModelAndView mav) {
        logger.debug("Receive product's id {} ", productId);
        Product validProduct = productService.isValidProduct(productId);
        if (!Objects.nonNull(validProduct)) {
            mav.addObject("message", ERROR_EXIST_PRODUCT);
            mav.setViewName("newPages/admin/products");
            return mav;
        }
        List<PriceByRegionDTO> placesAndPrice = priceDAO.getAllRegionsAndProductPriceInRegionByProductId(productId);
        logger.debug("Get all places and product prices if it exist {} ", placesAndPrice.toString());
        String productType = productDAO.getProductTypeByProductId(productId);

        mav.addObject("placesAndPrice", placesAndPrice);
        mav.addObject("productType", productType);
        mav.setViewName("newPages/admin/updateProductPrices");
        return mav;
    }

    @RequestMapping(value = {"updateProductPrices"}, method = RequestMethod.POST)
    public ModelAndView fillPriceByRegion(ModelAndView mav,
                                          @RequestParam(value = "id") Integer productId,
                                          @RequestParam(value = "placeId") Integer[] placeId,
                                          @RequestParam(value = "priceByRegion") BigDecimal[] priceByRegion
    ) {
        logger.debug("Receive product's id {} ", productId);
        Product validProduct = productService.isValidProduct(productId);
        boolean isValid = priceService.isValid(placeId, priceByRegion);
        if (!Objects.nonNull(validProduct)) {
            mav.addObject("error", ERROR_EXIST_PRODUCT);
            mav.setViewName("newPages/admin/products");
        }
        if (!isValid) {
            logger.error("Incoming data of place ID and is not correct {} {}",
                    Arrays.toString(placeId), Arrays.toString(priceByRegion));
            List<PriceByRegionDTO> placesAndPrice = priceDAO.getAllRegionsAndProductPriceInRegionByProductId(productId);
            logger.debug("Get all places and product prices if it exist {} ", placesAndPrice.toString());

            mav.addObject("placesAndPrice", placesAndPrice);
            mav.addObject("error", ERROR_FILL_IN_PRICE_BY_PRODUCT);
            mav.setViewName("newPages/admin/updateProductPrices?id=" + productId);

            return mav;
        }

        try {
            priceService.updateProductPriceInRegions(productId, placeId, priceByRegion);
            logger.debug("Update price in regions by product ");
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {} ", ex.getMessage());
            List<PriceByRegionDTO> placesAndPrice = priceDAO.getAllRegionsAndProductPriceInRegionByProductId(productId);
            logger.debug("Get all places and product prices if it exist {} ", placesAndPrice.toString());

            mav.addObject("placesAndPrice", placesAndPrice);
            mav.addObject("error", ERROR_FILL_IN_PRICE_BY_PRODUCT);
            mav.setViewName("newPages/admin/updateProductPrice?id=" + productId);
            return mav;
        }
        mav.setViewName("redirect:/admin/viewProductPriceInRegions?id=" + productId);
        return mav;

    }

}
