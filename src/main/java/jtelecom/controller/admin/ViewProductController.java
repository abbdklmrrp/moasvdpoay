package jtelecom.controller.admin;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
import jtelecom.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Created by Rysakova Anna on 01.05.2017., Nikita Alistratenko
 */
@Controller
@RequestMapping({"admin"})
public class ViewProductController {

    private static Logger logger = LoggerFactory.getLogger(ViewProductController.class);
    @Resource
    private ProductDao productDao;
    @Resource
    private ProductService productService;

    @RequestMapping(value = "getDetailsProduct={id}", method = RequestMethod.GET)
    public ModelAndView getDetailsProduct(@PathVariable(value = "id") int id,
                                          ModelAndView mav) {
// FIXME: 14.05.2017 validate product
        try {
            Product tariff = productDao.getById(id);
            logger.debug("Checked that the tariff exists {} ", tariff.toString());
        } catch (DataAccessException ex) {
            logger.error("Product with ID = {}  does not exist in the database ", id);
        }

        Product foundProduct = productDao.getById(id);
        logger.debug("Receive request param product id named 'id', value={} ", id);

        logger.debug("Product found in database, id={} ", id);
        logger.debug("Save to session ID of product {} ", id);
        mav.addObject("product", foundProduct);

        logger.debug("Product type is {} ", foundProduct.getCategoryId());
        mav.setViewName("newPages/admin/updateProduct");
        return mav;
    }

    /**
     * @author Nikita Alistratenko
     */
    @RequestMapping("disableEnableProduct={id}")
    public ModelAndView setProductDisabledEnabled(@PathVariable(value = "id") int id, RedirectAttributes attributes) {
        logger.debug("Product sent to get status changed, id = ", id);
        ModelAndView mw = new ModelAndView();
        if (productService.disableEnableProduct(id)) {
            attributes.addFlashAttribute("msg", "Product status has been changed");
            logger.debug("Product status has been changed, id = ", id);
        } else {
            attributes.addFlashAttribute("msg", "Product status has not been changed");
            logger.error("Product status has not been changed, id = ", id);
        }
        mw.setViewName("redirect:/admin/getProducts");
        return mw;
    }
}

