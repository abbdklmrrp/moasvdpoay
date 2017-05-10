package nc.nut.controller.admin;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.product.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by Rysakova Anna on 01.05.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ViewProductController {

    @Resource
    private ProductDao productDao;
    private static Logger logger = LoggerFactory.getLogger(ViewProductController.class);

    @RequestMapping(value = "getDetailsProduct", method = RequestMethod.GET)
    public ModelAndView getDetailsProduct(@RequestParam(value = "id") int id,
                                          ModelAndView mav,
                                          HttpSession session) {

        Product foundProduct = productDao.getById(id);
        logger.debug("Receive request param product id named 'id', value={} ", id);

        if (foundProduct.getId() == id) {
            logger.debug("Product found in database, id={} ", id);
            session.setAttribute("productId", id);
            logger.debug("Save to session ID of product {} ",id);
            mav.addObject("product", foundProduct);

            if (foundProduct.getProductType().equals(ProductType.Service)) {
                logger.debug("Product type is {} ", foundProduct.getCategoryId());
                mav.setViewName("admin/updateService");
            }
            if (foundProduct.getProductType().equals(ProductType.Tariff)) {
                logger.debug("Product type is {} ", foundProduct.getCategoryId());
                List<Product> servicesByTariff = productDao.getServicesByTariff(foundProduct);
                logger.debug("Received services that are included in the tariff");
                List<ProductCategories> productCategories = productDao.findProductCategories();
                logger.debug("Get all service's categories");
                mav.addObject("allServices", productCategories);
                mav.addObject("servicesByTariff", servicesByTariff);
                mav.setViewName("admin/updateTariff");
            }
        }
        return mav;
    }

    /**
     * @author Nikita Alistratenko
     */
    @RequestMapping("disableProduct")
    public ModelAndView setProductDisabled(@RequestParam(value = "id") int id, RedirectAttributes attributes) {
        ModelAndView mw = new ModelAndView();
        mw.setViewName("redirect:/admin/getAllProducts");
        if (productDao.disableProductByID(id)) {
            attributes.addFlashAttribute("msg", "Product has been disabled");
        } else {
            attributes.addFlashAttribute("msg", "Product has not been disabled");
        }
        return mw;
    }
}

