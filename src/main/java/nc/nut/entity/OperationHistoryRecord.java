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
public class OperationHistoryRecord {

    private int id;
    private int order_id;
    private Calendar operation_date;
    private String status;

    public OperationHistoryRecord() {
    }

    public OperationHistoryRecord(int id, int order_id, Calendar operation_date, String status) {
        this.id = id;
        this.order_id = order_id;
        this.operation_date = operation_date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Calendar getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(Calendar operation_date) {
        this.operation_date = operation_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OperationHistoryRecord{" + "id=" + id + ", order_id=" + order_id + ", operation_date=" + operation_date + ", status=" + status + '}';
    }

}
