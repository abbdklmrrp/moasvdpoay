package nc.nut.dao.product;

import java.math.BigDecimal;

/**
 * Created by Rysakova Anna on 23.04.2017.
 */
public class Product {
    private Integer id;
    private Integer categoryId;
    private Integer durationInDays;
    private Integer productType;
    private Integer needProcessing;
    private String name;
    private String description;
    private Integer status;
    private BigDecimal basePrice;

    public Product() {
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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getNeedProcessing() {
        return needProcessing;
    }

    public void setNeedProcessing(Integer needProcessing) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }


}
