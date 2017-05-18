package jtelecom.controller.admin;

import jtelecom.dao.price.PriceDao;
import jtelecom.dao.product.ProductDao;
import jtelecom.dto.PriceByRegionDto;
import jtelecom.services.price.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anna Rysakova on 9.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class UpdatePriceInRegions {

    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static final String ERROR_FILL_IN_PRICE_BY_PRODUCT = "Please, check that the region was selected and price input";
    private static Logger logger = LoggerFactory.getLogger(AddProductController.class);
    @Resource
    private PriceService priceService;
    @Resource
    private PriceDao priceDao;
    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"updateProductPrice={id}"}, method = RequestMethod.GET)
    public ModelAndView getPriceByRegion(@PathVariable(value = "id") Integer productId,
                                         ModelAndView mav) {
        logger.debug("Receive product's id {} ", productId);
        List<PriceByRegionDto> placesAndPrice = priceDao.getAllRegionsAndProductPriceInRegionByProductId(productId);
        logger.debug("Get all places and product prices if it exist {} ", placesAndPrice.toString());
        String productType = productDao.getProductTypeByProductId(productId);

        mav.addObject("placesAndPrice", placesAndPrice);
        mav.addObject("productType", productType);
        mav.setViewName("newPages/admin/updateProductPrices");
        return mav;
    }

    @RequestMapping(value = {"updateProductPrice={id}"}, method = RequestMethod.POST)
    public ModelAndView fillPriceByRegion(@PathVariable(value = "id") Integer productId,
                                          ModelAndView mav,
                                          @RequestParam(value = "placeId") Integer[] placeId,
                                          @RequestParam(value = "priceByRegion") BigDecimal[] priceByRegion
    ) {
        logger.debug("Receive product's id {} ", productId);
        boolean isValid = priceService.isValid(productId, placeId, priceByRegion);
        if (!isValid) {
            logger.error("Incoming data of place ID and is not correct {} {}",
                    Arrays.toString(placeId), Arrays.toString(priceByRegion));
            List<PriceByRegionDto> placesAndPrice = priceDao.getAllRegionsAndProductPriceInRegionByProductId(productId);
            logger.debug("Get all places and product prices if it exist {} ", placesAndPrice.toString());

            mav.addObject("placesAndPrice", placesAndPrice);
            mav.addObject("error", ERROR_FILL_IN_PRICE_BY_PRODUCT);
            mav.setViewName("newPages/admin/updateProductPrices");

            return mav;
        }

        try {
            priceService.updateProductPriceInRegions(productId, placeId, priceByRegion);
            logger.debug("Update price in regions by product ");
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {} ", ex.getMessage());
            List<PriceByRegionDto> placesAndPrice = priceDao.getAllRegionsAndProductPriceInRegionByProductId(productId);
            logger.debug("Get all places and product prices if it exist {} ", placesAndPrice.toString());

            mav.addObject("placesAndPrice", placesAndPrice);
            mav.addObject("error", ERROR_FILL_IN_PRICE_BY_PRODUCT);
            mav.setViewName("newPages/admin/updateProductPrices");
            return mav;
        }
        mav.setViewName("redirect:/admin/updateProductPrice=" + productId);
        return mav;

    }

}
