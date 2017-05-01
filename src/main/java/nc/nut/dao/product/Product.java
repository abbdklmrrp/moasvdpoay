package nc.nut.dao.product;

import java.math.BigDecimal;

/**
 * Created by Rysakova Anna on 23.04.2017.
 */
public class Product {
    private Integer id;
    private Integer categoryId;
    private Integer durationInDays;
    private ProductType productType;
    private Integer needProcessing;
    private String name;
    private String description;
    private Integer status;
    private BigDecimal basePrice;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType typeId) {
        this.productType = typeId;
    }

    public int getNeedProcessing() {
        return needProcessing;
    }

    public void setNeedProcessing(int needProcessing) {
        this.needProcessing = needProcessing;
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
}
