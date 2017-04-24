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

        return "admin/addService";
    }

    @RequestMapping(value = {"addService"}, method = RequestMethod.POST)
    String createProduct(@RequestParam(value = "productCategories") String categoryId,
                         @RequestParam(value = "duration") String duration,
                         @RequestParam(value = "productTypes") String typeId,
                         @RequestParam(value = "needProcessing") String needProcessing,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "description") String description,
                         @RequestParam(value = "status") String status) {

        Product product = new Product();
        product.setCategoryId(Integer.parseInt(categoryId));
        product.setDuration(Integer.parseInt(duration));
        product.setTypeId(Integer.parseInt(typeId));
        product.setNeedProcessing(Integer.parseInt(needProcessing));
        product.setName(name);
        product.setDescription(description);
        product.setStatus(Integer.parseInt(status));

        productDao.addProduct(product);

        return "admin/index";
    }

}
