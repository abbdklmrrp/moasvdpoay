package jtelecom.dao.order;

import jtelecom.dao.interfaces.DAO;
import jtelecom.dto.FullInfoOrderDTO;
import jtelecom.dto.OrdersRowDTO;

import java.util.List;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
public interface OrderDAO extends DAO<Order> {
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
     * This method sets 'Deactivated' status for particular order.
     *
     * @param orderId id of order
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    boolean deactivateOrder(Integer orderId);


    /**
     * This method returns order by user for particular product.
     *
     * @param userId    id of user
     * @param productId id of product
     * @return Order object
     */
    Order getNotDeactivatedOrderByUserAndProduct(Integer userId, Integer productId);

    /**
     * Method finds count all orders which connected to the user
     * and satisfy to search pattern
     *
     * @param userId id of the user
     * @param search search pattern
     * @return total count of all orders
     */
    Integer getCountOrdersByUserId(Integer userId, String search);

    /**
     * Method finds all orders which connected to the user,
     * satisfy to search pattern and ordering by column name
     *
     * @param start  first element
     * @param length last element
     * @param sort   column name to ordering
     * @param search search pattern
     * @param userId id of the user
     * @return list of the orders
     */
    List<FullInfoOrderDTO> getIntervalOrdersByUserId(int start, int length, String sort, String search, int userId);

    /**
     * Method finds count all orders which not connected to the csr
     * and satisfy to search pattern
     *
     * @param search search pattern
     * @return total count of all orders
     */
    Integer getCountOrdersWithoutCsr(String search);

    /**
     * Method finds all orders which not connected to the csr,
     * satisfy to search pattern and ordering by column name
     *
     * @param start  first element
     * @param length last element
     * @param sort   column name to ordering
     * @param search search pattern
     * @return list of the orders
     */
    List<FullInfoOrderDTO> getIntervalOrdersWithoutCsr(int start, int length, String sort, String search);

    /**
     * Method finds full info about order bu its id
     *
     * @param orderId id of the order
     * @return full info about order
     */
    FullInfoOrderDTO getOrderInfoByOrderId(Integer orderId);

    /**
     * Method set csr id to the order
     *
     * @param csrId   id of the csr
     * @param orderId id of the order
     * @return success of the operation
     */
    boolean assignToUser(int csrId, int orderId);

    /**
     * Method finds count of the orders which connected to the csr
     * and its status is InProcessing. Also orders satisfy to search pattern
     *
     * @param csrId  id of the csr
     * @param search search pattern
     * @return
     */
    Integer getCountOfInprocessingOrdersByCsrId(int csrId, String search);

    /**
     * This method saves Order object to database and returns
     * generated primary key.
     *
     * @param order Order object
     * @return generated primary key
     * @see Order
     */
    Integer saveAndGetGeneratedId(Order order);

    /**
     * This method returns list of limited OrdersRowsDTO object. If search
     * is not empty returned objects are filtered by name determined in search pattern.
     * If sort is not empty it sorts returned objects are sorted by column
     * deteermined in sort, othrwise, they are sorted by name.
     *
     * @param start      index of begin of list
     * @param end        index of end of list
     * @param search     search pattern(name of order's product)
     * @param sort       column to sort on
     * @param customerId id of customer
     * @return list of OrdersRowDTO
     */
    List<OrdersRowDTO> getLimitedOrderRowsDTOByCustomerId(Integer start, Integer end, String search, String sort, Integer customerId);

    /**
     * Gets number of orders of customer.
     *
     * @param search     search patter
     * @param sort       column to sort on
     * @param customerId id of customer
     * @return number of orders
     */
    Integer getCountOrdersByCustomerId(String search, String sort, Integer customerId);

    /**
     * Method finds all orders which connected to the csr,
     * its status is InProcessing and
     * satisfy to search pattern and ordering by column name
     *
     * @param start  first element
     * @param length last element
     * @param sort   column name to ordering
     * @param search search pattern
     * @return list of the orders
     */
    List<FullInfoOrderDTO> getIntervalProcessingOrdersByCsrId(int start, int length, String sort, String search, int csrId);

    /**
     * Method finds all processed orders which connected to the csr,
     * satisfy to search pattern and ordering by column name
     *
     * @param start  first element
     * @param length last element
     * @param sort   column name to ordering
     * @param search search pattern
     * @return list of the orders
     */
    List<FullInfoOrderDTO> getIntervalProccesedOrdersByCsrId(int start, int length, String sort, String search, int csrId);

    /**
     * Method finds count of the processed orders which connected to the csr
     * and its status is InProcessing. Also orders satisfy to search pattern
     *
     * @param csrId  id of the csr
     * @param search search pattern
     * @return
     */
    Integer getCountOfProcessedOrdersByCsrId(int csrId, String search);
}
