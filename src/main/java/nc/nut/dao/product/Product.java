package nc.nut.dao.product;

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

    public ProductType getTypeId() {
        return productType;
    }

    public void setTypeId(ProductType typeId) {
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
}
