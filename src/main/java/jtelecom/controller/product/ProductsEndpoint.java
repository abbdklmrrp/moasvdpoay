package jtelecom.controller.product;

import jtelecom.dao.price.PriceDao;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
import jtelecom.dto.PriceByRegionDto;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rysakova Anna on 26.04.2017.
 */
@RestController
@RequestMapping({"admin", "csr", "pmg"})
public class ProductsEndpoint {
    private static Logger logger = LoggerFactory.getLogger(ProductsEndpoint.class);
    @Resource
    private ProductDao productDao;
    @Resource
    private PriceDao priceDao;

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

    @RequestMapping(value = {"productsPriceInRegions/{id}"}, method = RequestMethod.GET)
    public ListHolder productsPriceInRegions(@ModelAttribute GridRequestDto request,
                                             @PathVariable(value = "id") Integer id) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getLength();
        String search = request.getSearch();
        List<PriceByRegionDto> data = priceDao.getLimitedQuantityProductPricesInRegions(id, start, length, sort, search);
        int size = priceDao.getCountPriceByPlace(search, id);
        return ListHolder.create(data, size);
    }
}

