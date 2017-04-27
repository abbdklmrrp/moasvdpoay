package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import nc.nut.dao.product.ProductTypes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Created by Anna on 23.04.2017.
 */
@Controller
@RequestMapping({"admin"})
public class ProductController {

    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"addProduct"}, method = RequestMethod.GET)
    ModelAndView addProduct(ModelAndView mav) {

        List<ProductTypes> productTypes = productDao.findProductTypes();
        List<ProductCategories> productCategories = productDao.findProductCategories();

        mav.addObject("productTypes", productTypes);
        mav.addObject("productCategories", productCategories);
        mav.addObject("product", new Product());

        mav.setViewName("admin/addProduct");

        return mav;
    }

    //TODO: add modelAttribute, fix "typeId"
    @RequestMapping(value = {"addProduct"}, method = RequestMethod.POST)
    String createProduct(
            @RequestParam(value = "productCategories") String category,
            @RequestParam(value = "duration") int duration,
            @RequestParam(value = "productTypes") String type,
            @RequestParam(value = "needProcessing") int needProcessing,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "status") int status
    ) {

        Product product = new Product();
        if (Objects.equals(type, "service")) {
//            product.setCategoryId(categoryId);
        }
        product.setDuration(duration);
//        product.setTypeId(typeId);
        product.setNeedProcessing(needProcessing);
        product.setName(name);
        product.setDescription(description);
        product.setStatus(status);

        productDao.addProduct(product);

        return "admin/index";
    }

}
