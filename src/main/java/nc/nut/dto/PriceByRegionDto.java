package nc.nut.dto;

import java.math.BigDecimal;

/**
 * Created by Anna Rysakova on 10.05.2017.
 */
public class PriceByRegionDto {

    private Integer productId;
    private String productName;
    private String productDescription;
    private String placeName;
    private BigDecimal priceProduct;

    public PriceByRegionDto() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public BigDecimal getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(BigDecimal priceProduct) {
        this.priceProduct = priceProduct;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("PriceByRegionDto{")
                .append("productId=").append(productId)
                .append(", productName='").append(productName).append('\'')
                .append(", productDescription='").append(productDescription).append('\'')
                .append(", placeName='").append(placeName).append('\'')
                .append(", priceProduct=").append(priceProduct)
                .append('}').toString();
    }
}
