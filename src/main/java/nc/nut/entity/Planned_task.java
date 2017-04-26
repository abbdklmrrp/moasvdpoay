/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.entity;

import java.util.Calendar;

/**
 *
 * @author Alistratenko Nikita
 */
public class Planned_task {

    private int id;
    private String status;
    private int order_id;
    private Calendar action_date;

    public Planned_task() {
    }

    public Planned_task(int id, String status, int order_id, Calendar action_date) {
        this.id = id;
        this.status = status;
        this.order_id = order_id;
        this.action_date = action_date;
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

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Calendar getAction_date() {
        return action_date;
    }

    public void setAction_date(Calendar action_date) {
        this.action_date = action_date;
    }

    @Override
    public String toString() {
        return "Planned_task{" + "id=" + id
                + ", status=" + status
                + ", order_id=" + order_id
                + ", action_date=" + action_date + '}';
    }

}
