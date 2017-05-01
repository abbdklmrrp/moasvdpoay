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
public class OperationHistoryRecord {

    private Integer id;
    private Integer orderId;
    private Calendar operationDate;
    private String status;

    public OperationHistoryRecord() {
    }

    public OperationHistoryRecord(int id, int orderId, Calendar operationDate, String status) {
        this.id = id;
        this.orderId = orderId;
        this.operationDate = operationDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Calendar getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Calendar operationDate) {
        this.operationDate = operationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OperationHistoryRecord{" + "id=" + id + ", orderId=" + orderId + ", operationDate=" + operationDate + ", status=" + status + '}';
    }

}
