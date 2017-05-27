package jtelecom.services.price;

import jtelecom.dao.price.Price;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Anna Rysakova
 */
public interface PriceService {

    /**
     * This method fill the {@code ArrayList} with the values of the product ID
     * and its prices for the region.
     *
     * @param productId     product id
     * @param placeId       place id
     * @param priceByRegion price of product by <code>placeId</code> region
     * @return <code>ArrayList</code> of product id, place id , price in this region
     * @see java.util.ArrayList
     */
    List<Price> fillInListWithProductPriceByRegion(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion);

    /**
     * Methods verify that arrays with {@code Place ID} and price by this place
     * are not {@code null}
     *
     * @param placeId       {@code Place} ID
     * @param priceByRegion price by place
     * @return {@code true} if data is correct
     * return {@code false} if placeId
     * or priceByRegion is {@code null}
     */
    boolean isValid(Integer[] placeId, BigDecimal[] priceByRegion);

    /**
     * <p>This method update price in regions by product ID. For this, the method takes out old prices
     * in regions from the database to {@link List} and compare with {@link List} of new prices in regions.</p>
     * <p>First, prices in regions that are not included in the new list are deleted
     * Then new prices in regions are inserted into the database</p>
     *
     * @param productId     product ID
     * @param placeId       array of ID places
     * @param priceByRegion array of prices
     */
    void updateProductPriceInRegions(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion);
}
