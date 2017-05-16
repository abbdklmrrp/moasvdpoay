package jtelecom.controller.admin;

import jtelecom.dao.price.PriceDao;
import jtelecom.dto.PriceByRegionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna Rysakova on 10.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ViewProductPriceByRegion {

    private static Logger logger = LoggerFactory.getLogger(ViewProductController.class);
    @Resource
    private PriceDao priceDao;

    @RequestMapping(value = "viewAllProducts", method = RequestMethod.GET)
    public ModelAndView getAllProductPrice(ModelAndView mav) {
        mav.setViewName("newPages/admin/viewProductPrices");
        return mav;
    }

    @RequestMapping(value = "viewProductPriceForRegions", method = RequestMethod.GET)
    public ModelAndView getProductPriceForRegions(@RequestParam("productId") Integer productId,
                                                  ModelAndView mav) {
        List<PriceByRegionDto> priceInRegionsByProduct = priceDao.getPriceInRegionsByProduct(productId);
        logger.debug("Receive product price by region {} ", priceInRegionsByProduct.toString());
        mav.addObject("priceInRegionsByProduct", priceInRegionsByProduct);
        mav.setViewName("newPages/admin/viewProductPrices");
        return mav;
    }


}
