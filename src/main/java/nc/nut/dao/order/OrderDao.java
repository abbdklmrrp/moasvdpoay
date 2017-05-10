package nc.nut.dao.order;

import nc.nut.dao.interfaces.Dao;
import nc.nut.dto.OrdersRowDTO;

import java.util.List;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
public interface OrderDao extends Dao<Order> {
    /**
     * This method returns all orders by one customer's users.
     * created by Yuliya Pedash
     *
     * @param customerId id of customer
     * @return list of Orders
     */
    List<Order> getOrdersByCustomerId(Integer customerId);

    /**
     * Method returns order id according to user id and product id with active status.
     *
     * @param userId    id of user.
     * @param productId id of product.
     * @return id of order.
     */
    Integer getOrderIdByUserIdAndProductId(Integer userId, Integer productId);

    /**
     * Method returns order id according to user id and product name with active status.
     *
     * @param userId      id of user.
     * @param productName name of product.
     * @return id of order.
     */
    Integer getOrderIdByUserIdAndProductName(Integer userId, String productName);

    /**
     * Set 'Deactivated' status for order of product for user.
     *
     * @param productId id of product to deactivate
     * @param userId    id of user
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    boolean deactivateOrderOfUserForProduct(Integer productId, Integer userId);

    /**
     * This method sets 'Suspended' status for particular order.
     *
     * @param orderId id of order
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    boolean suspendOrder(Integer orderId);

    /**
     * This method returns order by user for particular product.
     *
     * @param userId    id of user
     * @param productId id of product
     * @return Order object
     */
    Order getNotDeactivatedOrderByUserAndProduct(Integer userId, Integer productId);

    /**
     * This method returns Order Row DTO object by customer id.
     * For forming this object it gets data on all orders of users of
     * customer with given id.
     *
     * @param customerId id of customer
     * @return OrdersRowDTO object
     * @see OrdersRowDTO for details
     */
    List<OrdersRowDTO> getOrderRowsBDTOByCustomerId(Integer customerId);


//    Calendar getEndDateOfOrderActive(Integer orderId);
}
