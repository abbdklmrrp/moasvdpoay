package nc.nut.dto;

import nc.nut.dao.entity.OperationStatus;
import nc.nut.dao.product.ProductType;

import java.util.Calendar;

/**
 * Created by Yuliya Pedash on 07.05.2017.
 */
public class OrdersRowDTO {
    private final Integer orderId;
    private final String name;
    private final ProductType productType;
    private final Integer productId;
    private final Calendar endDate;
    private final OperationStatus operationStatus;

    public OrdersRowDTO(Integer orderId, String name, ProductType productType, Integer productId, Calendar endDate, OperationStatus operationStatus) {
        this.orderId = orderId;
        this.name = name;
        this.productType = productType;
        this.productId = productId;
        this.endDate = endDate;
        this.operationStatus = operationStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Integer getProductId() {
        return productId;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }
}
