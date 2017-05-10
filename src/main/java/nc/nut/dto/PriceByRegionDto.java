package nc.nut.dto;

import java.math.BigDecimal;

/**
 * Created by Anna Rysakova on 10.05.2017.
 */
public class PriceByRegionDto {

    private Integer productId;
    private Integer [] placeId;
    private BigDecimal[] priceByRegion;

    public PriceByRegionDto() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer[] getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer[] placeId) {
        this.placeId = placeId;
    }

    public BigDecimal[] getPriceByRegion() {
        return priceByRegion;
    }

    public void setPriceByRegion(BigDecimal[] priceByRegion) {
        this.priceByRegion = priceByRegion;
    }
}
