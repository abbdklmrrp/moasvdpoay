/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.price;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Alistratenko Nikita
 */
public class Price {

    private Integer placeId;
    private Integer product_id;
    private BigDecimal price;

    public Price() {
    }

    public Price(int placeId, int product_id, BigDecimal price) {
        this.placeId = placeId;
        this.product_id = product_id;
        this.price = price;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Price{" + "placeId=" + placeId
                + ", product_id=" + product_id
                + ", price=" + price + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price1 = (Price) o;
        return Objects.equals(getPlaceId(), price1.getPlaceId()) &&
                Objects.equals(getProduct_id(), price1.getProduct_id()) &&
                Objects.equals(getPrice(), price1.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceId(), getProduct_id(), getPrice());
    }
}
