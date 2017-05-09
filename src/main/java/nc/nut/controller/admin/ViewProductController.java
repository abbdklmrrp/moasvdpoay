package nc.nut.controller.admin;

import nc.nut.dao.product.Product;
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
            mav.addObject("product", foundProduct);
//                mav.addObject("type_id", product.getProductType().getId());

            if (foundProduct.getProductType().equals(ProductType.Service)) {
                logger.debug("Product type is {} ", foundProduct.getCategoryId());
//                mav.addObject("category_id", foundProduct.getCategoryId());
                mav.setViewName("admin/updateService");
            }
            if (foundProduct.getProductType().equals(ProductType.Tariff)) {
                logger.debug("Product type is {} ", foundProduct.getCategoryId());
                List<Product> servicesByTariff = productDao.getServicesByTariff(foundProduct);
                logger.debug("Received services that are included in the tariff");
                List<Product> servicesNotInTariff = productDao.getServicesNotInTariff(foundProduct);
                logger.debug("Received services that are NOT included in the tariff");
                mav.addObject("servicesByTariff", servicesByTariff);
                mav.addObject("servicesNotInTariff", servicesNotInTariff);
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

