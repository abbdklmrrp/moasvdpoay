/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.entity;

import java.math.BigDecimal;

/**
 *
 * @author Alistratenko Nikita
 */
public class Price {

    private int place_id;
    private int product_id;
    private BigDecimal price;

    public Price() {
    }

    public Price(int place_id, int product_id, BigDecimal price) {
        this.place_id = place_id;
        this.product_id = product_id;
        this.price = price;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
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
        return "Price{" + "place_id=" + place_id
                + ", product_id=" + product_id
                + ", price=" + price + '}';
    }

}
