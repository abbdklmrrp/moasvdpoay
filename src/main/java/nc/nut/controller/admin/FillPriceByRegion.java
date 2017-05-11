package nc.nut.controller.admin;

import nc.nut.dao.place.Place;
import nc.nut.dao.place.PlaceDAO;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.dto.PriceByRegionDto;
import nc.nut.services.PriceService;
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
import java.util.List;
import java.util.Objects;

/**
 * Created by Anna Rysakova on 9.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class FillPriceByRegion {

    @Resource
    private PlaceDAO placeDAO;
    @Resource
    private ProductDao productDao;
    @Resource
    private PriceService priceService;

    private static Logger logger = LoggerFactory.getLogger(AddProductController.class);

    private static final String ERROR_FILL_IN_PRICE_BY_PRODUCT = "Please, select places to filling price";
    private static final String ERROR_IN_CONNECTION = "Error with filling database";

    @RequestMapping(value = {"fillTariffsPrices"}, method = RequestMethod.GET)
    public ModelAndView getRegionForFill(ModelAndView mav) {
        List<Product> products = productDao.getProductForResidentialCustomerWithoutPrice();
        logger.debug("Get all the tariffs that are not filled with services");
        List<Place> placesForFillInTariff = placeDAO.getPlacesForFillInTariff();
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
        Product product = productDao.getById(productId);
        logger.debug("Checked that the product exists {} ", product.toString());

        if (Objects.equals(product, null) || Objects.equals(placeId, null) || Objects.equals(priceByRegion, null)) {
            logger.error("Incoming data error with places {} ", Objects.equals(placeId, null));
            logger.error("Incoming data error with places {} ", Objects.equals(priceByRegion, null));
            mav.addObject("errorFilling", ERROR_FILL_IN_PRICE_BY_PRODUCT);
            logger.error("Incoming data error with product {} ", Objects.equals(product, null));
            mav.setViewName("admin/fillTariff");
            return mav;
        }

        try {
            priceService.fillPriceOfProductByRegion(productId, placeId, priceByRegion);
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

}
