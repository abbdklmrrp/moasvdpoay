package jtelecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author Anna Rysakova
 */
public class PriceByRegionDTO {

    private Integer productId;
    private String productName;
    private String productDescription;
    private String placeName;
    private BigDecimal priceProduct;
    private Integer placeId;
    @JsonProperty("type_id")
    private String productType;
    @JsonProperty("status")
    private String productStatus;

    public PriceByRegionDTO() {
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

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("PriceByRegionDTO{")
                .append("productId=").append(productId)
                .append(", productName='").append(productName).append('\'')
                .append(", productDescription='").append(productDescription).append('\'')
                .append(", placeName='").append(placeName).append('\'')
                .append(", priceProduct=").append(priceProduct)
                .append(", placeId=").append(placeId)
                .append(", productType='").append(productType).append('\'')
                .append(", productStatus='").append(productStatus).append('\'')
                .append('}').toString();
    }
}
