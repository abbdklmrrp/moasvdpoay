package jtelecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author Yuliya Pedash
 */
public class ServicesCatalogRowDTO {
    @JsonProperty("id")
    private Integer id;
    private String name;
    @JsonProperty("category_id")
    private String category;
    @JsonProperty("base_price")
    private BigDecimal price;
    @JsonProperty("status")
    private String status;

    public ServicesCatalogRowDTO(Integer id, String name, String category, BigDecimal price, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer productId) {
        this.id = productId;
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
        return "ServicesCatalogRowDTO{" +
                "productId=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}