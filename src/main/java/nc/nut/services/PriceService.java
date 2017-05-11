package nc.nut.services;

import nc.nut.dao.price.Price;
import nc.nut.dao.product.Product;
import nc.nut.dao.product.ProductDao;
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

    @Resource
    private ProductDao productDao;

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

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
        for (int i = 0, j = 0; i < placeId.length; i++, j++) {
            Price price = new Price();
            price.setProductId(productId);
            price.setPlaceId(placeId[i]);
            price.setPrice(priceByRegion[i + 1]);
            listPriceByRegion.add(price);
            logger.debug("to list was add object Price {} ", price);
        }
        return listPriceByRegion;
    }

    public boolean isValidate(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion) {
        if (Objects.equals(productId, null) || Objects.equals(placeId, null) || Objects.equals(priceByRegion, null)) {
            return false;
        }
        Product product = productDao.getById(productId);
        logger.debug("Checked that the product exists {} ", product.toString());
        if (Objects.equals(product, null)) {
            return false;
        }
        if (placeId.length != priceByRegion.length) {
            return false;
        }
        for (int i = 0; i < placeId.length; i++) {
            if (Objects.equals(placeId[i], null) || Objects.equals(priceByRegion[i], null)) {
                return false;
            }
        }

        return true;
    }
}
