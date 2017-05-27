package jtelecom.controller.product;

import jtelecom.dao.price.PriceDAO;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDAO;
import jtelecom.dto.PriceByRegionDTO;
import jtelecom.dto.grid.GridRequestDto;
import jtelecom.dto.grid.ListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Anna Rysakova
 */
@RestController
@RequestMapping({"admin", "csr", "pmg"})
public class ViewAllProductsController {
    private static Logger logger = LoggerFactory.getLogger(ViewAllProductsController.class);
    @Resource
    private ProductDAO productDAO;
    @Resource
    private PriceDAO priceDAO;

    @RequestMapping(value = {"all"}, method = RequestMethod.GET)
    public ListHolder servicesByTariff(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        String search = request.getSearch();
        List<Product> data = productDAO.getLimitedQuantityProduct(start, length, sort, search);
        int size = productDAO.getCountProductsWithSearch(search);
        return ListHolder.create(data, size);
    }

    @RequestMapping(value = {"getPrices"}, method = RequestMethod.GET)
    public ListHolder getPrices(@RequestParam(name = "start") int startIndex,
                                @RequestParam(name = "end") int endIndex,
                                @RequestParam(name = "productId") int productId) {
        List<PriceByRegionDTO> data = priceDAO.getLimitedQuantityProductPricesInRegions(productId, startIndex, endIndex, "", "");
        int size = priceDAO.getCountPriceByPlace("", productId);
        return ListHolder.create(data, size);
    }

}

