package jtelecom.dto;

import jtelecom.dao.entity.OperationStatus;

/**
 * Created by Yuliya Pedash on 20.05.2017.
 */
public class PlannedTaskDTO {
    String productName;
    private Integer id;
    private OperationStatus status;
    private String actionDate;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    @Override
    public String toString() {
        return "PlannedTaskDTO{" +
                "productName='" + productName + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", actionDate='" + actionDate + '\'' +
                '}';
    }
}
