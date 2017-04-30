package nc.nut.dao.order;

import nc.nut.dao.interfaces.Dao;

import java.util.List;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
public interface OrderDao extends Dao<Order> {
    /**
     * This method returns all orders by one customer's users in one city.
     * created by Yuliya Pedash
     * @param customerId
     * @param placeId
     * @return
     */
    public List<Order> getOrdersByCustomerIdAndPlaceId(long customerId, long placeId);

    /**
     * Method returns order id according to user id and product id with active status.
     *
     * @param userId id of user.
     * @param productId id of product.
     * @return id or order.
     */
    public Integer getOrderIdByUserIdAndProductId(Integer userId, Integer productId);

    /**
     * Method returns order id according to user id and product name with active status.
     *
     * @param userId id of user.
     * @param productName name of product.
     * @return id or order.
     */
    public Integer getOrderIdByUserIdAndProductName(Integer userId, String productName);
}
