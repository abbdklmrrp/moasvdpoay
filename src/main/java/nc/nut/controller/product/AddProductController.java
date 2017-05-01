package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.product.ProductType;
import nc.nut.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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


    @RequestMapping(value = {"addProduct"}, method = RequestMethod.GET)
    ModelAndView addProduct(ModelAndView mav) {
        mav.addObject("product", new Product());
        mav.setViewName("admin/addProduct");
        return mav;
    }

    @RequestMapping(value = {"addService"}, method = RequestMethod.GET)
    ModelAndView addService(ModelAndView mav) {

        List<ProductCategories> productCategories = productDao.findProductCategories();
//        List<String> productTypes = productDao.findProductTypes();
//        mav.addObject("productTypes", productTypes);
        mav.addObject("productCategories", productCategories);
        mav.addObject("product", new Product());
        mav.setViewName("admin/addService");
        return mav;
    }

    @RequestMapping(value = {"addProduct"}, method = RequestMethod.POST)
    String createService(Product product
    ) {
        product.setProductType(ProductType.Tariff);
        productService.saveProduct(product);
        return "admin/index";
    }

    @RequestMapping(value = {"addService"}, method = RequestMethod.POST)
    String createService(Product product, Model model,
                         @RequestParam(value = "newCategory") String newCategory,
                         @RequestParam(value = "newCategoryDesc") String newCategoryDesc
    ) {
        product.setProductType(ProductType.Service);
        ProductCategories productCategories = new ProductCategories();
        productCategories.setName(newCategory);
        productCategories.setDescription(newCategoryDesc);
        try {
            product = productService.getCategory(productCategories, product);
        } catch (DuplicateKeyException e) {
            logger.error("Not unique category", e);
            model.addAttribute("error", "Not unique category");
            return "admin/addService";
        }
        productService.saveProduct(product);
        return "admin/index";
    }

    @RequestMapping(value = {"viewAllProducts"}, method = RequestMethod.GET)
    String viewAllProducts(Model model) {

        List<Product> allServices = productDao.getAllServices();
        List<Product> allTariffs = productDao.getAllTariffs();

        model.addAttribute("allServices", allServices);
        model.addAttribute("allTariffs", allTariffs);

        return "admin/viewAllProducts";
    }

}
