package jtelecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtelecom.dao.product.ProductType;

/**
 * Created by Yuliya Pedash on 07.05.2017.
 */
public class OrdersRowDTO {
    @JsonProperty("order_id")
    private Integer orderId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("action_date")
    private String endDate;
    @JsonProperty("type_id")
    private ProductType productType;
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("operation_status")
    private String operationStatus;

    public OrdersRowDTO() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    @Override
    public String toString() {
        return "OrdersRowDTO{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", endDate='" + endDate + '\'' +
                ", productType=" + productType +
                ", productId=" + productId +
                ", operationStatus='" + operationStatus + '\'' +
                '}';
    }
}
