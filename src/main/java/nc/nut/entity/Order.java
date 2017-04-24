/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.entity;

/**
 *
 * @author Alistratenko Nikita
 */
public class Order {

    private int id;
    private int product_id;
    private int user_id;
    private int current_status_id;

    public Order() {
    }

    public Order(int id, int product_id, int user_id, int current_status_id) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.current_status_id = current_status_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCurrent_status_id() {
        return current_status_id;
    }

    public void setCurrent_status_id(int current_status_id) {
        this.current_status_id = current_status_id;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id
                + ", product_id=" + product_id
                + ", user_id=" + user_id
                + ", current_status_id=" + current_status_id + '}';
    }

}
