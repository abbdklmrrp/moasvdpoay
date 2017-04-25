package nc.nut.controller.product;

import nc.nut.product.Product;
import nc.nut.product.ProductCategories;
import nc.nut.product.ProductDao;
import nc.nut.product.ProductTypes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Anna on 23.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ProductController {

    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"createProduct"}, method = RequestMethod.GET)
    String addProduct(Model model) {

        List<ProductTypes> productTypes = productDao.findProductTypes();
        List<ProductCategories> productCategories = productDao.findProductCategories();
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("productCategories", productCategories);

        return "admin/addProduct";
    }

    @RequestMapping(value = {"addProduct"}, method = RequestMethod.POST)
    String createProduct(@RequestParam(value = "productCategories") int categoryId,
                         @RequestParam(value = "duration") int duration,
                         @RequestParam(value = "productTypes") int typeId,
                         @RequestParam(value = "needProcessing") int needProcessing,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "description") String description,
                         @RequestParam(value = "status") int status) {

        Product product = new Product();
        product.setCategoryId(categoryId);
        product.setDuration(duration);
        product.setTypeId(typeId);
        product.setNeedProcessing(needProcessing);
        product.setName(name);
        product.setDescription(description);
        product.setStatus(status);

        productDao.addProduct(product);

        return "admin/index";
    }

}
