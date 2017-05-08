package nc.nut.dto;

import nc.nut.dao.entity.OperationStatus;
import nc.nut.dao.product.ProductType;

import java.util.Calendar;

/**
 * Created by Yuliya Pedash on 07.05.2017.
 */
public class OrdersRowDTO {
    private final Integer productId;
    private final String name;
    private final ProductType productType;
    private final String description;
    private final Calendar endDate;
    private final OperationStatus operationStatus;

    public OrdersRowDTO(Integer productId, String name, ProductType productType, String description, Calendar endDate, OperationStatus operationStatus) {
        this.productId = productId;
        this.name = name;
        this.productType = productType;
        this.description = description;
        this.endDate = endDate;
        this.operationStatus = operationStatus;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getDescription() {
        return description;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }
}
