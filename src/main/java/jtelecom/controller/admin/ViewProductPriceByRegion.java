package jtelecom.controller.admin;

import jtelecom.dao.price.PriceDao;
import jtelecom.dao.product.ProductDao;
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
        String productType = productDao.getProductTypeByProductId(productId);
        mav.addObject("id", productId);
        mav.addObject("productType", productType);
        mav.setViewName("newPages/admin/viewProductPriceInRegions");
        return mav;
    }

    @RequestMapping(value = "viewProductPriceInfo", method = RequestMethod.POST)
    public String getProductPriceInfo(@RequestParam(value = "productId") Integer id) {
        List<PriceByRegionDto> price = priceDao.getPriceInRegionsByProduct(id);
        return price.toString();
    }
}