package nc.nut.dao.complaint;

/**
 * @author Revniuk Aleksandr
 */
public enum ComplaintStatus {
    Send("Send"),
    InProcessing("In processing"),
    Processed("Processed");
    private String complaintStatus;

    ComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }
}
