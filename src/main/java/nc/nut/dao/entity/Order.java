/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.entity;

import java.util.Objects;

/**
 * @author Alistratenko Nikita
 */
public class Order {

    private Integer id;
    private Integer product_id;
    private Integer user_id;
    private String current_status;

    public Order() {
    }

    public Order(int id, int product_id, int user_id, String current_status) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.current_status = current_status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status_id(String current_status) {
        this.current_status = current_status;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id
                + ", product_id=" + product_id
                + ", user_id=" + user_id
                + ", current_status=" + current_status + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) &&
                Objects.equals(getProduct_id(), order.getProduct_id()) &&
                Objects.equals(getUser_id(), order.getUser_id()) &&
                Objects.equals(getCurrent_status(), order.getCurrent_status());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProduct_id(), getUser_id(), getCurrent_status());
    }
}
