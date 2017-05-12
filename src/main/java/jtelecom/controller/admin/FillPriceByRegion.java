package jtelecom.controller.admin;

import jtelecom.dao.place.Place;
import jtelecom.dao.place.PlaceDAO;
import jtelecom.dao.price.Price;
import jtelecom.dao.price.PriceDao;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anna Rysakova on 9.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class FillPriceByRegion {

    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static final String ERROR_FILL_IN_PRICE_BY_PRODUCT = "Please, check that the region was selected and price input";
    private static Logger logger = LoggerFactory.getLogger(AddProductController.class);
    @Resource
    private PlaceDAO placeDAO;
    @Resource
    private ProductDao productDao;
    @Resource
    private PriceService priceService;
    @Resource
    private PriceDao priceDao;

    @RequestMapping(value = {"fillTariffsPrices"}, method = RequestMethod.GET)
    public ModelAndView getRegionForFill(ModelAndView mav) {

        List<Product> products = productDao.getProductForResidentialCustomerWithoutPrice();
        logger.debug("Get all the tariffs that are not filled with services {} ", products.toString());
        List<Place> placesForFillInTariff = placeDAO.getPlacesForFillInTariff();
        logger.debug("Get products that do not have a price by region {} ", placesForFillInTariff.toString());

        mav.addObject("products", products);
        mav.addObject("placesForFillInTariff", placesForFillInTariff);
        mav.addObject("priceByRegionDto", new PriceByRegionDto());

        mav.setViewName("newPages/admin/fillTariffsPrices");
        return mav;
    }

    @RequestMapping(value = {"fillTariffsPrices"}, method = RequestMethod.POST)
    public ModelAndView fillPriceByRegion(ModelAndView mav,
                                          @RequestParam(value = "productId") Integer productId,
                                          @RequestParam(value = "placeId") Integer[] placeId,
                                          @RequestParam(value = "priceByRegion") BigDecimal[] priceByRegion
    ) {
        boolean isValid = priceService.isValid(productId, placeId, priceByRegion);
        if (!isValid) {
            logger.error("Incoming data of place ID and is not correct {} {}",
                    Arrays.toString(placeId), Arrays.toString(priceByRegion));
            mav.addObject("error", ERROR_FILL_IN_PRICE_BY_PRODUCT);
            mav.setViewName("admin/fillTariff");
            return mav;
        }

        try {
            ArrayList<Price> priceArrayList = priceService.fillInListWithProductPriceByRegion(productId, placeId, priceByRegion);
            boolean isFillPrice = priceDao.fillPriceOfProductByRegion(priceArrayList);
            logger.debug("Fill in tariff with services to database with success {} ", isFillPrice);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {} ", ex);
            mav.addObject("error ", ERROR_IN_CONNECTION);
            mav.setViewName("admin/fillTariff");
            return mav;
        }

        mav.setViewName("redirect:/admin/getProfile");
        return mav;

    }

}
