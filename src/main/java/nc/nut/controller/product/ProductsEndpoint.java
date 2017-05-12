package nc.nut.controller.product;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.grid.GridRequestDto;
import nc.nut.grid.ListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rysakova Anna on 26.04.2017.
 */
@RestController
@RequestMapping({"admin"})
public class ProductsEndpoint {
    private static Logger logger = LoggerFactory.getLogger(ProductsEndpoint.class);
    @Resource
    private ProductDao productDao;

    @RequestMapping(value = {"getProducts"}, method = RequestMethod.GET)
    public ModelAndView getProducts() {
        return new ModelAndView("newPages/admin/Products");
    }

    @RequestMapping(value = {"all"}, method = RequestMethod.GET)
    public ListHolder servicesByTariff(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getLength();
        String search = request.getSearch();
        List<Product> data = productDao.getLimitedQuantityProduct(start, length, sort, search);
        int size = productDao.getCountProductsWithSearch(search);
        return ListHolder.create(data, size);
    }
}

