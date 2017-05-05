/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.entity;

import java.util.Calendar;

/**
 * @author Alistratenko Nikita
 */
public class Planned_task {

    private Integer id;
    private String status;
    private Integer orderId;
    private Calendar actionDate;

    public Planned_task() {
    }

    public Planned_task(int id, String status, int orderId, Calendar actionDate) {
        this.id = id;
        this.status = status;
        this.orderId = orderId;
        this.actionDate = actionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus_id(String status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Calendar getActionDate() {
        return actionDate;
    }

    public void setActionDate(Calendar actionDate) {
        this.actionDate = actionDate;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Planned_task{").append("id=").append(id).append(", status=").append(status).append(", orderId=").append(orderId).append(", actionDate=").append(actionDate).append('}').toString();
    }

}
