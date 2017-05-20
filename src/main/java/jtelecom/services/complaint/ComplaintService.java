package jtelecom.services.complaint;

import jtelecom.dao.complaint.Complaint;

/**
 * @author Moiseienko Petro
 * @since 20.05.2017.
 */
public interface ComplaintService {

    boolean save(Complaint complaint, int userId);

    boolean changeStatus(int complaintId, int statusId);

}
