package jtelecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Yuliya Pedash on 29.04.2017.
 */
public class ProductCatalogRowDTO {
    @JsonProperty("productId")
    private Integer productId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("category")
    private String category;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("status")
    private String status;

    public ProductCatalogRowDTO(Integer productId, String name, String category, BigDecimal price, String status) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.status = status;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductCatalogRowDTO{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}