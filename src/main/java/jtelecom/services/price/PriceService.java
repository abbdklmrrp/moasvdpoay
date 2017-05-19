package jtelecom.services.price;

import jtelecom.dao.price.Price;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Anna Rysakova on 18.05.2017.
 */
public interface PriceService {

    List<Price> fillInListWithProductPriceByRegion(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion);

    boolean isValid(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion);

    void updateProductPriceInRegions(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion);
}
