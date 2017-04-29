package nc.nut.dao.price;

import nc.nut.dao.entity.Price;
import nc.nut.dao.interfaces.Dao;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
public interface PriceDao extends Dao<Price> {
    /**
     * @param productId
     * @param placeId
     * @return
     */
    public Price getPriceByProductIdAndPlaceId(int productId, long placeId);
}
