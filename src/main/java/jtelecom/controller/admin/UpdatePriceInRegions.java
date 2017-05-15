package jtelecom.controller.admin;

import jtelecom.dao.place.PlaceDAO;
import jtelecom.dao.price.PriceDao;
import jtelecom.dto.PriceByRegionDto;
import jtelecom.services.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    private PlaceDAO placeDAO;
    @Resource
    private PriceService priceService;
    @Resource
    private PriceDao priceDao;

    @RequestMapping(value = {"updateProductPrice"}, method = RequestMethod.GET)
    public ModelAndView getPriceByRegion(ModelAndView mav, HttpSession session) {
        Integer productId = (Integer) session.getAttribute("productId");
        List<PriceByRegionDto> placesAndPrice = priceDao.getAllRegionsAndProductPriceInRegionByProductId(productId);
        logger.debug("Get all places and product prices if it exist {} ", placesAndPrice.toString());

        mav.addObject("placesAndPrice", placesAndPrice);
        mav.setViewName("newPages/admin/updateProductPrices");
        return mav;
    }

    @RequestMapping(value = {"updateProductPrice"}, method = RequestMethod.POST)
    public ModelAndView fillPriceByRegion(ModelAndView mav, HttpSession session,
                                          @RequestParam(value = "placeId") Integer[] placeId,
                                          @RequestParam(value = "priceByRegion") BigDecimal[] priceByRegion
    ) {
        Integer productId = (Integer) session.getAttribute("productId");
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
        session.removeAttribute("productId");
        mav.setViewName("redirect:/admin/getProducts");
        return mav;

    }

}
