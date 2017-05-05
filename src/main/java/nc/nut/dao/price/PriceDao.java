package nc.nut.dao.price;

import nc.nut.dao.interfaces.Dao;


/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
public interface PriceDao extends Dao<Price> {
    /**
     * This method returns <code>Price</code>  object by product id and place id.
     * For more details about <code>Price</code>  object: {@link Price}
     *
     * @param productId id of product
     * @param placeId   id of place
     * @return <code>Price</code> object
     * @see Price
     */
    Price getPriceByProductIdAndPlaceId(Integer productId, Integer placeId);
}
