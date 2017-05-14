package jtelecom.services;

import jtelecom.dao.price.Price;
import jtelecom.dao.product.Product;
import jtelecom.dao.product.ProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Anna Rysakova on 10.05.2017.
 */
@Component
public class PriceService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Resource
    private ProductDao productDao;

    /**
     * This method fill the <code>ArrayList</code> with the values of the product ID
     * and its prices for the region.
     *
     * @param productId     product id
     * @param placeId       place id
     * @param priceByRegion price of product by <code>placeId</code> region
     * @return <code>ArrayList</code> of product id, place id , price in this region
     */
    public ArrayList<Price> fillInListWithProductPriceByRegion(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion) {
        ArrayList<Price> listPriceByRegion = new ArrayList<>();
        logger.debug("Create list of product price by region {} ", listPriceByRegion);
        // TODO: 14.05.2017 limit array size
        for (int i = 0; i < priceByRegion.length; i++) {
            if (placeId[i] != null & priceByRegion[i] != null) {
                Price price = new Price();
                price.setProductId(productId);
                price.setPlaceId(placeId[i]);
                price.setPrice(priceByRegion[i]);
                listPriceByRegion.add(price);
                logger.debug("to list was add object Price {} ", price);
            }
        }
        return listPriceByRegion;
    }

    public boolean isValid(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion) {
        if (Objects.equals(productId, null) || Objects.equals(placeId, null) || Objects.equals(priceByRegion, null)) {
            return false;
        }
        Product product = productDao.getById(productId);
        logger.debug("Checked that the product exists {} ", product.toString());
        return !Objects.equals(product, null);
    }
}
