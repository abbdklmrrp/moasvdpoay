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
public class ComplaintHistoryRecord {

    private int id;
    private int complaint_id;
    private Calendar operation_date;
    private String status;

    public ComplaintHistoryRecord() {
    }

    public ComplaintHistoryRecord(int id, int complaint_id, Calendar operation_date, String status) {
        this.id = id;
        this.complaint_id = complaint_id;
        this.operation_date = operation_date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(int complaint_id) {
        this.complaint_id = complaint_id;
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
        return "ComplaintHistoryRecord{" + "id=" + id + ", complaint_id=" + complaint_id + ", operation_date=" + operation_date + ", status=" + status + '}';
    }

}
