package jtelecom.controller.admin;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.product.ProductDao;
import jtelecom.dao.product.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Rysakova Anna on 01.05.2017., Nikita Alistratenko
 */
@Controller
@RequestMapping({"admin"})
public class ViewProductController {

    private static Logger logger = LoggerFactory.getLogger(ViewProductController.class);
    @Resource
    private ProductDao productDao;

    @RequestMapping(value = "getDetailsProduct", method = RequestMethod.GET)
    public ModelAndView getDetailsProduct(@RequestParam(value = "id") int id,
                                          ModelAndView mav,
                                          HttpSession session) {
// FIXME: 12.05.2017
        try {
            Product tariff = productDao.getById(id);
            logger.debug("Checked that the tariff exists {} ", tariff.toString());
        } catch (DataAccessException ex) {
            logger.error("Product with ID = {}  does not exist in the database ", id);
        }

        Product foundProduct = productDao.getById(id);
        logger.debug("Receive request param product id named 'id', value={} ", id);

        logger.debug("Product found in database, id={} ", id);
        session.setAttribute("productId", id);
        logger.debug("Save to session ID of product {} ", id);
        mav.addObject("product", foundProduct);

        if (foundProduct.getProductType() == ProductType.Service) {
            logger.debug("Product type is {} ", foundProduct.getCategoryId());
            mav.setViewName("admin/updateService");
        }
        if (foundProduct.getProductType() == ProductType.Tariff) {
            logger.debug("Product type is {} ", foundProduct.getCategoryId());
            List<Product> servicesByTariff = productDao.getServicesByTariff(foundProduct);
            logger.debug("Received services that are included in the tariff {}", servicesByTariff.toString());
            List<ProductCategories> productCategories = productDao.findProductCategories();
            logger.debug("Get all service's categories {}", productCategories.toString());
            mav.addObject("allServices", productCategories);
            mav.addObject("servicesByTariff", servicesByTariff);
            mav.setViewName("admin/updateTariff");
        }
        return mav;
    }

    /**
     * @author Nikita Alistratenko
     */
    @RequestMapping("disableEnableProduct")
    public ModelAndView setProductDisabledEnabled(@RequestParam(value = "id") int id, RedirectAttributes attributes) {
        logger.debug("Product sent to get status changed, id = ", id);
        ModelAndView mw = new ModelAndView();
        if (productDao.disableEnableProductByID(id)) {
            attributes.addFlashAttribute("msg", "Product status has been changed");
            logger.debug("Product status has been changed, id = ", id);
        } else {
            attributes.addFlashAttribute("msg", "Product status has not been changed");
            logger.error("Product status has not been changed, id = ", id);
        }
        mw.setViewName("redirect:/admin/getAllProducts");
        return mw;
    }
}
