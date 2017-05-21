package jtelecom.controller.admin;

import jtelecom.dao.price.PriceDao;
import jtelecom.dao.product.ProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by Anna Rysakova on 10.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ViewProductPriceByRegion {

    private static Logger logger = LoggerFactory.getLogger(ViewProductController.class);
    @Resource
    private PriceDao priceDao;
    @Resource
    private ProductDao productDao;

    @RequestMapping(value = "viewAllProducts", method = RequestMethod.GET)
    public ModelAndView getAllProductPrice(ModelAndView mav) {
        mav.setViewName("newPages/admin/viewProductPrices");
        return mav;
    }

    @RequestMapping(value = "viewProductPriceInRegions", method = RequestMethod.GET)
    public ModelAndView getProductPriceForRegions(@RequestParam("id") Integer productId,
                                                  ModelAndView mav) {
        mav.addObject("id", productId);
        mav.setViewName("newPages/admin/viewProductPriceInRegions");
        return mav;
    }
}