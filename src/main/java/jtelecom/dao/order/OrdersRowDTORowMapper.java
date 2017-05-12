package jtelecom.dao.order;

import jtelecom.dao.entity.OperationStatus;
import jtelecom.dao.product.ProductType;
import jtelecom.dto.OrdersRowDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by Yuliya Pedash on 07.05.2017.
 */
public class OrdersRowDTORowMapper implements RowMapper<OrdersRowDTO> {
    @Override
    public OrdersRowDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Date creationDate = resultSet.getDate("operation_date");
        Calendar finalDateCal = new GregorianCalendar();
        int duration = resultSet.getInt("duration");
        String name = resultSet.getString("name");
        Integer orderId = resultSet.getInt("id");
        ProductType productType = ProductType.getProductTypeFromId(resultSet.getInt("type_id"));
        String description = resultSet.getString("description");
        OperationStatus operationStatus = OperationStatus.getOperationStatusFromId(resultSet.getInt("current_status_id"));
        finalDateCal.setTime(creationDate);
        finalDateCal.add(Calendar.DATE, duration);

        return new OrdersRowDTO(orderId, name, productType, description, finalDateCal, operationStatus);
    }
}
