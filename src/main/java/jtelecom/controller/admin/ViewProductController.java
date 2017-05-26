package jtelecom.controller.admin;

import jtelecom.dao.entity.CustomerType;
import jtelecom.dao.price.PriceDAO;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.product.ProductDAO;
import jtelecom.dao.product.ProductType;
import jtelecom.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Anna Rysakova
 */
@Controller
@RequestMapping({"admin"})
public class ViewProductController {

    private static Logger logger = LoggerFactory.getLogger(ViewProductController.class);
    private static final String ERROR_EXIST = "Sorry, product doesn't exist";

    @Resource
    private ProductDAO productDAO;
    @Resource
    private PriceDAO priceDAO;
    @Resource
    private ProductService productService;

    @RequestMapping(value = {"getProducts"}, method = RequestMethod.GET)
    public ModelAndView getProducts() {
        return new ModelAndView("newPages/admin/products");
    }

    @RequestMapping(value = "getDetailsProduct", method = RequestMethod.GET)
    public ModelAndView getDetailsProduct(@RequestParam(value = "id") int id,
                                          ModelAndView mav) {
        Product foundProduct = productService.isValidProduct(id);
        if (!Objects.nonNull(foundProduct)) {
            logger.error("Product with ID = {}  does not exist in the database ", id);
            mav.addObject("error", ERROR_EXIST);
            mav.setViewName("newPages/admin/products");
        }
        logger.debug("Receive request param product id named 'id', value={} ", id);
        if (foundProduct.getProductType() == ProductType.Service) {
            ProductCategories category = productDAO.getProductCategoryById(foundProduct.getCategoryId());
            mav.addObject("category", category);
        }
        logger.debug("Product found in database, id={} ", id);
        mav.addObject("product", foundProduct);

        logger.debug("Product type is {} ", foundProduct.getCategoryId());
        if (foundProduct.getCustomerType() == CustomerType.Residential) {
            mav.setViewName("newPages/admin/updateProduct");
        }
        if (foundProduct.getCustomerType() == CustomerType.Business) {
            mav.setViewName("newPages/admin/updateProductBusiness");
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

