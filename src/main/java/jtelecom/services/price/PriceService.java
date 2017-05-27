package jtelecom.services.price;

import jtelecom.dao.price.Price;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Anna Rysakova
 */
public interface PriceService {

    List<Price> fillInListWithProductPriceByRegion(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion);

    boolean isValid(Integer[] placeId, BigDecimal[] priceByRegion);

    void updateProductPriceInRegions(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion);
}
