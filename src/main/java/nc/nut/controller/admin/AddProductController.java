package nc.nut.controller.admin;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
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
import javax.servlet.http.HttpSession;
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


    @RequestMapping(value = {"addTariff"}, method = RequestMethod.GET)
    ModelAndView addProduct(ModelAndView mav) {
        mav.addObject("product", new Product());
        mav.setViewName("admin/addTariff");
        return mav;
    }

    @RequestMapping(value = {"addService"}, method = RequestMethod.GET)
    ModelAndView addService(ModelAndView mav, HttpSession session) {
        List<ProductCategories> productCategories = productDao.findProductCategories();
        session.setAttribute("productCategories", productCategories);
//        List<String> productTypes = productDao.findProductTypes();
//        mav.addObject("productTypes", productTypes);
        mav.addObject("productCategories", productCategories);
        mav.addObject("product", new Product());
        mav.setViewName("admin/addService");
        return mav;
    }

    @RequestMapping(value = {"addTariff"}, method = RequestMethod.POST)
    String createService(Product product, Model model) {
        System.out.println(product.getName());
        boolean checkEmptyTariff = productService.checkEmptyFieldIfProduct(product);
        if (!checkEmptyTariff) {
            model.addAttribute("errorEmptyProduct", " Please fill all fields");
            return "admin/addTariff";
        }
        productService.saveProduct(product);
        return "redirect:/admin/fillTariff";
    }

    @RequestMapping(value = {"addService"}, method = RequestMethod.POST)
    String createService(Product product, Model model, HttpSession session,
                         @RequestParam(value = "newCategory") String newCategory,
                         @RequestParam(value = "newCategoryDesc") String newCategoryDesc) {

        boolean checkEmptyService = productService.checkEmptyFieldIfProduct(product);
        if (!checkEmptyService) {
            model.addAttribute("errorEmptyProduct", " Please fill all fields");
            model.addAttribute("productCategories", session.getAttribute("productCategories"));
            return "admin/addService";
        }
        ProductCategories productCategories = new ProductCategories();
        productCategories.setName(newCategory);
        productCategories.setDescription(newCategoryDesc);
        boolean checkEmptyCategory = productService.checkEmptyNewCategory(productCategories);
        if (!checkEmptyCategory) {
            model.addAttribute("errorEmptyProduct", " Please fill all fields");
            model.addAttribute("productCategories", session.getAttribute("productCategories"));
            return "admin/addService";
        }
        try {
            product = productService.getCategory(productCategories, product);
        } catch (DuplicateKeyException e) {
            logger.error("Not unique category", e);
            model.addAttribute("error", "Not unique category");
            return "admin/addService";
        }
        productService.saveProduct(product);
        return "redirect:/admin/index";
    }
}
