/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.price;

import java.math.BigDecimal;

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

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
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

}
