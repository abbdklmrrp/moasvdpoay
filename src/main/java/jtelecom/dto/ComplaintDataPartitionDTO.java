package jtelecom.dto;


import jtelecom.dao.complaint.Complaint;

import java.util.List;

/**
 * This class created to send part of complaints and length of all in one object, need for pagination.
 *
 * @author Revniuk Aleksandr
 */
public class ComplaintDataPartitionDTO {
    private Integer amount;
    private List<Complaint> partOfComplaints;

    public ComplaintDataPartitionDTO() {
    }

    public ComplaintDataPartitionDTO(Integer amount, List<Complaint> complaints) {
        this.amount = amount;
        this.partOfComplaints = complaints;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Complaint> getPartOfComplaints() {
        return partOfComplaints;
    }

    public void setPartOfComplaints(List<Complaint> partOfComplaints) {
        this.partOfComplaints = partOfComplaints;
    }

    @Override
    public String toString() {
        return "ComplaintDataPartitionDTO{" +
                "amount=" + amount +
                ", partOfComplaints=" + partOfComplaints +
                '}';
    }
}
