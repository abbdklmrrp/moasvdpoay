package nc.nut.controller.csr;

import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
import nc.nut.grid.GridRequestDto;
import nc.nut.grid.ListHolder;
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
@RequestMapping({"csr","pmg"})
public class ViewProductsController {

    @Resource
    private ProductDao productDao;

    @RequestMapping(value={"getPmgProductsPage"},method =RequestMethod.GET)
    public ModelAndView getPmgProductsPage(){
        return new ModelAndView("newPages/pmg/Products");
    }
    @RequestMapping(value={"getCsrProductsPage"},method = RequestMethod.GET)
    public ModelAndView getCsrProductsPage(){
        return new ModelAndView("newPages/csr/Products");
    }

    @RequestMapping(value = {"getProducts"}, method = RequestMethod.GET)
    public ListHolder servicesByTariff(@ModelAttribute GridRequestDto request) {
        String sort=request.getSort();
        int start=request.getStartBorder();
        int length=request.getLength();
        String search=request.getSearch();
        List<Product> data=productDao.getLimitedQuantityProduct(start,length,sort,search);
        int size=productDao.getCountProductsWithSearch(search);
        return ListHolder.create(data, size);
    }

}
