package nc.nut.dao;

import nc.nut.entity.Customer;
import nc.nut.entity.Order;
import nc.nut.entity.Place;

import java.util.List;

/**
 * Created by Yuliya Pedash on 26.04.2017.
 */
public interface OrderDao extends Dao<Order> {
    List<Order> getOrdersByCustomerIdAndPlace(int customerId, String place);
}
