package jtelecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtelecom.dao.entity.OperationStatus;
import jtelecom.dao.product.ProductType;

import java.util.Calendar;

/**
 * Created by Yuliya Pedash on 07.05.2017.
 */
public class OrdersRowDTO {
    @JsonProperty("id")
    private final Integer orderId;
    private final String name;
    private String description;
    @JsonProperty("type_id")
    private final ProductType productType;
    private  Integer productId;
    private  Calendar endDate;
    @JsonProperty("current_status_id")
    private final OperationStatus operationStatus;

    public OrdersRowDTO(Integer orderId, String name, ProductType productType, Integer productId, Calendar endDate, OperationStatus operationStatus) {
        this.orderId = orderId;
        this.name = name;
        this.productType = productType;
        this.productId = productId;
        this.endDate = endDate;
        this.operationStatus = operationStatus;
    }

    public OrdersRowDTO(Integer orderId, String name, String description, ProductType productType, OperationStatus operationStatus) {
        this.orderId = orderId;
        this.name = name;
        this.description = description;
        this.productType = productType;
        this.operationStatus = operationStatus;
    }

    public String getDescription() {
        return description;
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
