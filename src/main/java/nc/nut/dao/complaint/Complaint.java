/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.complaint;

import java.util.Calendar;

/**
 * @author Alistratenko Nikita
 */
public class Complaint {

    private Integer id;
    private Calendar creationDate;
    private String description;
    private ComplaintStatus status;
    private Integer csrId;
    private Integer orderId;

    public Complaint() {
    }

    public Complaint(int id, Calendar creationDate, String description, ComplaintStatus status, int csrId, int orderId) {
        this.id = id;
        this.creationDate = creationDate;
        this.description = description;
        this.status = status;
        this.csrId = csrId;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public Integer getCsrId() {
        return csrId;
    }

    public void setCsrId(Integer csrId) {
        this.csrId = csrId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Complaint{").append("id=").append(id).append(", creationDate=").append(creationDate).append(", description=").append(description).append(", status=").append(status).append(", csrId=").append(csrId).append(", orderId=").append(orderId).append('}').toString();
    }

}
