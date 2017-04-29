/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.order;

import nc.nut.dao.entity.OperationStatus;

/**
 * @author Alistratenko Nikita
 */
public class Order {

    private Integer id;
    private Integer productId;
    private Integer userId;
    private OperationStatus currentStatus;


    public Order(Integer id, Integer productId, Integer userId, OperationStatus currentStatus) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.currentStatus = currentStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OperationStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrent_status_id(OperationStatus current_status) {
        this.currentStatus = current_status;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id
                + ", productId=" + productId
                + ", userId=" + userId
                + ", currentStatus=" + currentStatus + '}';
    }

}
