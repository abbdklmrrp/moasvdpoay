package nc.nut.controller.admin;

import nc.nut.dao.product.ProductDao;
import nc.nut.dto.PriceByRegionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna Rysakova on 10.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ViewProductPriceByRegion {

    @Resource
    private ProductDao productDao;
    private static Logger logger = LoggerFactory.getLogger(ViewProductController.class);

    @RequestMapping(value = "viewAllProducts", method = RequestMethod.GET)
    public ModelAndView getAllProductPrice(ModelAndView mav) {
        List<PriceByRegionDto> productPriceByRegion = productDao.getProductPriceByRegion();
        logger.debug("Receive product price by region ", productPriceByRegion.toString());
        mav.addObject("productPriceByRegion", productPriceByRegion);
        mav.setViewName("newPages/admin/viewProductPrices");
        return mav;
    }
}
