package nc.nut.dto;

import nc.nut.dao.complaint.Complaint;

import java.util.List;

/**
 * Created by aleksandr on 12.05.17.
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
