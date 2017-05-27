package jtelecom.controller.admin;

import jtelecom.dao.product.Product;
import jtelecom.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Anna Rysakova
 */
@Controller
@RequestMapping({"admin"})
public class UpdateProductController {

    private static final String ERROR_IN_CONNECTION = "Error with filling database";
    private static final String ERROR_EXIST = "Sorry, product doesn't exist";
    private static Logger logger = LoggerFactory.getLogger(UpdateProductController.class);
    @Resource
    private ProductService productService;

    @RequestMapping(value = {"updateProduct"}, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateTariff(Product product, @RequestParam(value = "id") Integer id,
                                     ModelAndView mav) {

        logger.debug("Receive product ID {} ", id);
        Product foundProduct = productService.isValidProduct(id);
        if (!Objects.nonNull(foundProduct)) {
            logger.error("Product with ID = {}  does not exist in the database ", id);
            mav.addObject("error", ERROR_EXIST);
            mav.setViewName("newPages/admin/products");
        }
        product.setId(id);
        logger.debug("Write ID to product {} ", product.getId());

        try {
            boolean isUpdate = productService.updateProduct(product);
            logger.debug("Update fields of product with success {} ", isUpdate);
            mav.addObject("message", "success");
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error with filling database {}", ex.getMessage());
            mav.addObject("error ", ERROR_IN_CONNECTION);
            mav.addObject("message", "Sorry, try again later");
            mav.setViewName("redirect:/admin/getDetailsProduct?id=" + id);
            return mav;
        }

        mav.setViewName("redirect:/admin/getDetailsProduct?id=" + id);
        return mav;
    }
}
