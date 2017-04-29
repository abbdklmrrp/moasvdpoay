package nc.nut.dao.order;


import nc.nut.dao.entity.OperationStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setProductId(resultSet.getInt("product_id"));
        order.setUserId(resultSet.getInt("user_id"));
        int currentStatus = resultSet.getInt("CURRENT_STATUS_ID");
        switch(currentStatus){
            case (1):{
                order.setCurrent_status_id(OperationStatus.Acitve);
            }
            case (2):{
                order.setCurrent_status_id(OperationStatus.Suspended);
            }
            case (3):{
                order.setCurrent_status_id(OperationStatus.Deactivated);
            }
            case (4):{
                order.setCurrent_status_id(OperationStatus.InProcessing);
            }
        }
        return order;
    }
}

