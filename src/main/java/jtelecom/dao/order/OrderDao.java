package jtelecom.dao.order;

import jtelecom.dao.interfaces.Dao;
import jtelecom.dto.FullInfoOrderDTO;
import jtelecom.dto.OrdersRowDTO;

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
     * This method sets 'Activated' status for particular order.
     *
     * @param orderId id of order
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    boolean activateOrder(Integer orderId);


    /**
     * This method returns order by user for particular product.
     *
     * @param userId    id of user
     * @param productId id of product
     * @return Order object
     */
    Order getNotDeactivatedOrderByUserAndProduct(Integer userId, Integer productId);

    Integer getCountOrdersByUserId(Integer userId, String search);

    List<FullInfoOrderDTO> getIntervalOrdersBuUserId(int start, int length, String sort, String search, int userId);

    Integer getCountOrdersWithoutCsr(String search);

    List<FullInfoOrderDTO> getIntervalOrdersWithoutCsr(int start, int length, String sort, String search);

    FullInfoOrderDTO getOrderInfoByOrderId(Integer orderId);

    boolean assignToUser(int csrId, int orderId);

    /**
     * Yuliya
     *
     * @param order
     * @return
     */
    Integer saveAndGetGeneratedId(Order order);

    List<OrdersRowDTO> getLimitedOrderRowsDTOByCustomerId(Integer start, Integer length, String search, String sort, Integer customerId);

    Integer getCountOrderRowsDTOByCustomerId(String search, String sort, Integer customerId);


}
