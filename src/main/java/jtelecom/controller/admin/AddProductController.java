package jtelecom.controller.admin;

import jtelecom.dao.entity.CustomerType;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductCategories;
import jtelecom.dao.product.ProductDAO;
import jtelecom.dao.product.ProductType;
import jtelecom.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna on 23.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class AddProductController {
    private final static String ERROR_WRONG_FIELDS = "Please, check the correctness of the input data";
    private final static String ERROR_EXIST_OF_CATEGORY = "Category already exists";
    private static Logger logger = LoggerFactory.getLogger(AddProductController.class);
    @Resource
    private ProductDAO productDAO;
    @Resource
    private ProductService productService;

    @RequestMapping(value = {"addTariff"}, method = RequestMethod.GET)
    public ModelAndView addProduct(ModelAndView mav) {
        mav.addObject("product", new Product());
        mav.setViewName("newPages/admin/addTariff");
        return mav;
    }

    @RequestMapping(value = {"addService"}, method = RequestMethod.GET)
    public ModelAndView addService(ModelAndView mav) {
        List<ProductCategories> productCategories = productDAO.getProductCategories();
        logger.debug("Get all service's categories");
        mav.addObject("productCategories", productCategories);
        mav.addObject("product", new Product());
        mav.addObject("newProductCategories", new ProductCategories());
        mav.setViewName("newPages/admin/addService");
        return mav;
    }

    @RequestMapping(value = {"addTariff"}, method = RequestMethod.POST)
    public ModelAndView createTariff(Product product, ModelAndView mav) {
        product.setProductType(ProductType.Tariff);
        boolean isEmptyFieldsOfTariff = productService.isEmptyFieldOfProduct(product);
        logger.debug("Check that the incoming tariff fields are not empty {} ", isEmptyFieldsOfTariff);
        productService.validateBasePriceByCustomerType(product);
        if (isEmptyFieldsOfTariff) {
            logger.error("Incoming request has empty fields");
            mav.addObject("error", ERROR_WRONG_FIELDS);
            mav.setViewName("newPages/admin/addTariff");
            return mav;
        }
        Integer isSave = productService.saveProduct(product);
        mav.addObject("tariffId", isSave);
        logger.debug("Save product was success {} ", isSave);
        mav.setViewName("redirect:/admin/fillTariff");
        return mav;
    }

    @RequestMapping(value = {"addService"}, method = RequestMethod.POST)
    public ModelAndView createService(ModelAndView mav,
                                      ProductCategories productCategory,
                                      Product product) {
        product.setProductType(ProductType.Service);
        boolean isEmptyFieldsOfService = productService.isEmptyFieldOfProduct(product);
        logger.debug("Check that the incoming tariff fields are not empty {} ", isEmptyFieldsOfService);
        boolean isEmptyFieldsOfNewCategory = productService.isEmptyFieldsOfNewCategory(productCategory);
        logger.debug("Check that the incoming new category fields are not empty {} ", isEmptyFieldsOfNewCategory);
        productService.validateBasePriceByCustomerType(product);

        if (isEmptyFieldsOfService || isEmptyFieldsOfNewCategory) {
            mav.addObject("error", ERROR_WRONG_FIELDS);
            logger.error("Incoming request has empty fields");
            List<ProductCategories> productCategories = productDAO.getProductCategories();
            logger.debug("Get all service's categories {} ", productCategories.toString());
            mav.addObject("productCategories", productCategories);
            mav.setViewName("newPages/admin/addService");
            return mav;
        }
        try {
            product = productService.getCategory(productCategory, product);
            logger.debug("Set category for tariff {} ", product.getCategoryId());
        } catch (DuplicateKeyException e) {
            logger.error(ERROR_EXIST_OF_CATEGORY, e);
            mav.addObject("error", ERROR_EXIST_OF_CATEGORY);
            mav.setViewName("newPages/admin/addService");
            return mav;
        }
        Integer isSave = productService.saveProduct(product);
        logger.debug("Save product was success with id {} ", isSave);
        if (product.getCustomerType() == CustomerType.Residential) {
            mav.setViewName("redirect:/admin/fillTariffsPrices?id=" + isSave);
        }
        if (product.getCustomerType() == CustomerType.Business) {
            mav.setViewName("redirect:/admin/getProducts");
        }
        return mav;
    }
}
