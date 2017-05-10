/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;

/**
 * @author Alistratenko Nikita
 */
public class OperationHistoryRecord {

    private Integer id;
    @JsonProperty("order_id")
    private Integer orderId;
    @JsonProperty("operation_date")
    private Calendar operationDate;
    @JsonProperty("status_id")
    private OperationStatus status;

    public OperationHistoryRecord() {
    }

    public OperationHistoryRecord(int id, int orderId, Calendar operationDate, OperationStatus status) {
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

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("OperationHistoryRecord{").append("id=").append(id).append(", orderId=").append(orderId).append(", operationDate=").append(operationDate).append(", status=").append(status).append('}').toString();
    }

}
