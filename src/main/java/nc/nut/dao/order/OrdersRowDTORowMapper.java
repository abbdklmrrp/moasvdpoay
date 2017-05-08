package nc.nut.dao.order;

import nc.nut.dao.entity.OperationStatus;
import nc.nut.dao.product.ProductType;
import nc.nut.dto.OrdersRowDTO;
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
        Integer productId = resultSet.getInt("id");
        ProductType productType = ProductType.getProductTypeFromId(resultSet.getInt("type_id"));
        String description = resultSet.getString("description");
        OperationStatus operationStatus = OperationStatus.getOperationStatusFromId(resultSet.getInt("current_status_id"));
        finalDateCal.setTime(creationDate);
        finalDateCal.add(Calendar.DATE, duration);

        return new OrdersRowDTO(productId, name, productType, description, finalDateCal, operationStatus);
    }
}
