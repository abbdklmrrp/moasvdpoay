package nc.nut.dao.complaint;

import java.util.Objects;

/**
 * @author Revniuk Aleksandr
 */
public enum ComplaintStatus {
    Send(1, "Send"),
    InProcessing(2, "In processing"),
    Processed(3, "Processed");

    private int id;
    private String complaintStatus;

    ComplaintStatus(int id, String complaintStatus) {
        this.id = id;
        this.complaintStatus = complaintStatus;
    }

    public int getId() {
        return id;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public static ComplaintStatus getOperationStatusById(Integer id) {
        ComplaintStatus[] complaintStatuses = values();
        for (ComplaintStatus complaintStatus : complaintStatuses) {
            if (complaintStatus.getId()==id) {
                return complaintStatus;
            }
        }
        throw new IllegalArgumentException();
    }
}
