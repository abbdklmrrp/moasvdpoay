package jtelecom.controller;

import jtelecom.controller.product.ProductsEndpoint;
import jtelecom.dao.price.PriceDao;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
import jtelecom.dto.PriceByRegionDto;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Nikita on 02.05.2017.
 */
@Controller
public class AboutController {
    private static Logger logger = LoggerFactory.getLogger(ProductsEndpoint.class);
    @Resource
    private ProductDao productDao;
    @Resource
    private PriceDao priceDao;

    @RequestMapping(value = "/forBusiness")
    public String forBusiness() {
        return "newPages/ForBusiness";
    }

    @RequestMapping(value = "/forCustomers")
    public String forPrivateCustomers() {
        return "newPages/ForPrivateCustomers";
    }


    @RequestMapping(value = "/products")
    public String shotProducts() {
        return "newPages/ProductsForVisitors";
    }


    @RequestMapping(value = {"productForBusiness"}, method = RequestMethod.GET)
    @ResponseBody
    public ListHolder getProductListForBusiness(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        String search = request.getSearch();
        List<Product> data = productDao.getLimitedActiveProductsForBusiness(start, length, sort, search);
        int size = productDao.getCountForLimitedActiveProductsForBusiness(search);
        return ListHolder.create(data, size);
    }

    @RequestMapping(value = {"productForResidential"}, method = RequestMethod.GET)
    @ResponseBody
    public ListHolder getProductListForResidential(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        String search = request.getSearch();
        List<Product> data = productDao.getLimitedActiveProductsForResidential(start, length, sort, search);
        int size = productDao.getCountForLimitedActiveProductsForResidential(search);
        return ListHolder.create(data, size);
    }

}
