package jtelecom.controller.admin;

import jtelecom.dao.place.Place;
import jtelecom.dao.place.PlaceDAO;
import jtelecom.dao.price.Price;
import jtelecom.dao.price.PriceDao;
import jtelecom.dao.product.Product;
import jtelecom.services.price.PriceService;
import jtelecom.services.product.ProductService;
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
import java.util.Objects;

/**
 * Created by Anna Rysakova on 9.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class FillProductPricesController {

    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static final String ERROR_FILL_IN_PRICE_BY_PRODUCT = "Please, check that the region was selected and price input";
    private static Logger logger = LoggerFactory.getLogger(FillProductPricesController.class);
    @Resource
    private PlaceDAO placeDAO;
    @Resource
    private PriceService priceService;
    @Resource
    private ProductService productService;
    @Resource
    private PriceDao priceDao;

    @RequestMapping(value = {"fillTariffsPrices"}, method = RequestMethod.GET)
    public ModelAndView getRegionForFill(@RequestParam(value = "id") Integer id,
                                         ModelAndView mav) {

        List<Place> placesForFillInTariff = placeDAO.getAllPlaces();
        logger.debug("Get products that do not have a price by region {} ", placesForFillInTariff.toString());

        mav.addObject("id", id);
        mav.addObject("placesForFillInTariff", placesForFillInTariff);
        mav.setViewName("newPages/admin/fillTariffsPrices");
        return mav;
    }

    @RequestMapping(value = {"fillTariffsPrices/{id}"}, method = RequestMethod.POST)
    public ModelAndView fillPriceByRegion(@PathVariable(value = "id") Integer productId,
                                          ModelAndView mav,
                                          @RequestParam(value = "placeId") Integer[] placeId,
                                          @RequestParam(value = "priceByRegion") BigDecimal[] priceByRegion
    ) {
        Product validProduct = productService.isValidProduct(productId);
        boolean isValid = priceService.isValid(placeId, priceByRegion);
        if (!Objects.nonNull(validProduct) || !isValid) {
            logger.error("Incoming data of place ID and is not correct {} {}",
                    Arrays.toString(placeId), Arrays.toString(priceByRegion));
            List<Place> placesForFillInTariff = placeDAO.getAllPlaces();
            logger.debug("Get products that do not have a price by region {} ", placesForFillInTariff.toString());

            mav.addObject("placesForFillInTariff", placesForFillInTariff);
            mav.addObject("error", ERROR_FILL_IN_PRICE_BY_PRODUCT);
            mav.setViewName("newPages/admin/fillTariffsPrices");
            return mav;
        }

        try {
            List<Price> priceArrayList = priceService.fillInListWithProductPriceByRegion(productId, placeId, priceByRegion);
            boolean isFillPrice = priceDao.fillPriceOfProductByRegion(priceArrayList);
            logger.debug("Fill in tariff with services to database with success {} ", isFillPrice);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {} ", ex.getMessage());
            List<Place> placesForFillInTariff = placeDAO.getAllPlaces();
            logger.debug("Get products that do not have a price by region {} ", placesForFillInTariff.toString());

            mav.addObject("placesForFillInTariff", placesForFillInTariff);
            mav.addObject("error ", ERROR_IN_CONNECTION);
            mav.setViewName("newPages/admin/fillTariffsPrices");
            return mav;
        }
        mav.setViewName("redirect:/admin/getProducts");
        return mav;

    }

}
