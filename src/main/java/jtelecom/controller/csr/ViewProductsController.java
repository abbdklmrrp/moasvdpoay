package jtelecom.controller.csr;

import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
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
 * @author Moiseienko Petro
 * @since 09.05.2017.
 */
@RestController
@RequestMapping({"csr", "pmg"})
public class ViewProductsController {

    @Resource
    private ProductDao productDao;
    private static Logger logger = LoggerFactory.getLogger(ViewProductsController.class);

    @RequestMapping(value = {"getPmgProductsPage"}, method = RequestMethod.GET)
    public ModelAndView getPmgProductsPage() {
        return new ModelAndView("newPages/pmg/Products");
    }

    @RequestMapping(value = {"getCsrProductsPage"}, method = RequestMethod.GET)
    public ModelAndView getCsrProductsPage() {
        return new ModelAndView("newPages/csr/Products");
    }

    @RequestMapping(value = {"getProducts"}, method = RequestMethod.GET)
    public ListHolder getProducts(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getLength();
        String search = request.getSearch();
        logger.debug("Get products in interval:" + start + " : " + length);
        List<Product> data = productDao.getLimitedQuantityProduct(start, length, sort, search);
        int size = productDao.getCountProductsWithSearch(search);
        return ListHolder.create(data, size);
    }

}