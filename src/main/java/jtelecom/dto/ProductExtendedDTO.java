package jtelecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtelecom.dao.product.Product;

import java.math.BigDecimal;

/**
 * @Author Nikita Alistratenko
 */
public class ProductExtendedDTO {
    private Integer id;
    private Integer categoryId;
    @JsonProperty("duration")
    private Integer durationInDays;
    @JsonProperty("type_id")
    private String productType;
    private String processingStrategy;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private int status;
    @JsonProperty("base_price")
    private BigDecimal basePrice;
    @JsonProperty("customerType")
    private String customerType;

    public ProductExtendedDTO(Integer id, Integer categoryId, Integer durationInDays, String productType, String processingStrategy, String name, String description, int status, BigDecimal basePrice, String customerType) {
        this.id = id;
        this.categoryId = categoryId;
        this.durationInDays = durationInDays;
        this.productType = productType;
        this.processingStrategy = processingStrategy;
        this.name = name;
        this.description = description;
        this.status = status;
        this.basePrice = basePrice;
        this.customerType = customerType;
    }

    public ProductExtendedDTO(Product product) {
        this.id = product.getId();
        this.categoryId = product.getCategoryId();
        this.durationInDays = product.getDurationInDays();
        this.productType = product.getProductType().getName();
        this.processingStrategy = product.getProcessingStrategy().getName();
        this.name = product.getName();
        this.description = product.getDescription();
        this.status = product.getStatus().getId();
        this.basePrice = product.getBasePrice();
        this.customerType = product.getCustomerType().getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProcessingStrategy() {
        return processingStrategy;
    }

    public void setProcessingStrategy(String processingStrategy) {
        this.processingStrategy = processingStrategy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
