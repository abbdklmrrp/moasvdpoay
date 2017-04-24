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
    private int status_id;
    private int order_id;
    private Calendar action_date;

    public Planned_task() {
    }

    public Planned_task(int id, int status_id, int order_id, Calendar action_date) {
        this.id = id;
        this.status_id = status_id;
        this.order_id = order_id;
        this.action_date = action_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
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
                + ", status_id=" + status_id
                + ", order_id=" + order_id
                + ", action_date=" + action_date + '}';
    }

}
