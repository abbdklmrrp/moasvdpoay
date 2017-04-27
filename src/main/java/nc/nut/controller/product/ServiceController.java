package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductCategories;
import nc.nut.dao.product.ProductDao;
import nc.nut.services.ProductService;
import org.springframework.stereotype.Controller;
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
public class ServiceController {

    @Resource
    private ProductDao productDao;
    @Resource
    private ProductService productService;

    @RequestMapping(value = {"addService"}, method = RequestMethod.GET)
    ModelAndView addProduct(ModelAndView mav) {

        List<ProductCategories> productCategories = productDao.findProductCategories();

        mav.addObject("productCategories", productCategories);
        mav.addObject("product", new Product());

        mav.setViewName("admin/addService");

        return mav;
    }

    //TODO: add modelAttribute
    @RequestMapping(value = {"addService"}, method = RequestMethod.POST)
    String createService(
            @RequestParam(value = "productCategories") int categoryId,
            @RequestParam(value = "duration") int duration,
            @RequestParam(value = "newCategory") String newCategory,
            @RequestParam(value = "newCategoryDesc") String newCategoryDesc,
            @RequestParam(value = "needProcessing") int needProcessing,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "status") int status
    ) {

        Product product = new Product();
        int newCategoryId = productService.getCategory(newCategory, newCategoryDesc);
        int checkCategory = productService.checkIdCategory(categoryId, newCategoryId);
        product.setCategoryId(checkCategory);
        product.setDurationInDays(duration);
        product.setNeedProcessing(needProcessing);
        product.setName(name);
        product.setDescription(description);
        product.setStatus(status);

        productDao.save(product);

        return "admin/index";
    }

}
