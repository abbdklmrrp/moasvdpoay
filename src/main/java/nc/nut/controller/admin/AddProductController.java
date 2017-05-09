package nc.nut.controller.admin;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.product.ProductType;
import nc.nut.services.ProductService;
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
    @Resource
    private ProductDao productDao;
    @Resource
    private ProductService productService;
    private Logger logger = LoggerFactory.getLogger(AddProductController.class);
    private final static String ERROR_WRONG_FIELDS = "Please, check the correctness of the input data";
    private final static String ERROR_EXIST_OF_CATEGORY = "Category already exists";

    // maybe ename also add to mav?
    @RequestMapping(value = {"addTariff"}, method = RequestMethod.GET)
    ModelAndView addProduct(ModelAndView mav) {
        mav.addObject("product", new Product());
        mav.setViewName("admin/addTariff");
        return mav;
    }

    // combine tariff & services?
    @RequestMapping(value = {"addService"}, method = RequestMethod.GET)
    public ModelAndView addService(ModelAndView mav) {
        List<ProductCategories> productCategories = productDao.findProductCategories();
        mav.addObject("productCategories", productCategories);
        mav.addObject("product", new Product());
        mav.addObject("newProductCategories", new ProductCategories());
        mav.setViewName("admin/addService");
        return mav;
    }

    @RequestMapping(value = {"addTariff"}, method = RequestMethod.POST)
    public ModelAndView createService(Product product, ModelAndView mav) {
        product.setProductType(ProductType.Tariff);
        boolean isEmptyFieldsOfTariff = productService.isEmptyFieldOfProduct(product);
        if (isEmptyFieldsOfTariff) {
            mav.addObject("errorEmptyFields", ERROR_WRONG_FIELDS);
            mav.setViewName("admin/addTariff");
            return mav;
        }
        productDao.save(product);
        mav.setViewName("redirect:/admin/fillTariff");
        return mav;
    }

    @RequestMapping(value = {"addService"}, method = RequestMethod.POST)
    public ModelAndView createService(ModelAndView mav,
                                      ProductCategories productCategory,
                                      Product product) {
        product.setProductType(ProductType.Service);
        boolean isEmptyFieldsOfService = productService.isEmptyFieldOfProduct(product);
        boolean isEmptyFieldsOfNewCategory = productService.isEmptyFieldsOfNewCategory(productCategory);

        if (isEmptyFieldsOfService || isEmptyFieldsOfNewCategory) {
            mav.addObject("errorEmptyFields", ERROR_WRONG_FIELDS);
            List<ProductCategories> productCategories = productDao.findProductCategories();
            mav.addObject("productCategories", productCategories);
            mav.setViewName("admin/addService");
            return mav;
        }
        try {
            product = productService.getCategory(productCategory, product);
        } catch (DuplicateKeyException e) {
            logger.error(ERROR_EXIST_OF_CATEGORY, e);
            mav.addObject("errorExistCategory", ERROR_EXIST_OF_CATEGORY);
            mav.setViewName("admin/addService");
            return mav;
        }
        productDao.save(product);
        mav.setViewName("redirect:/admin/index");
        return mav;
    }
}
