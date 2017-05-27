package jtelecom.services.complaint;

import jtelecom.dao.complaint.Complaint;

/**
 * @author Moiseienko Petro
 * @since 20.05.2017.
 */
public interface ComplaintService {

    /**
     * Method saves complaint and gets it's id.
     * After method sends mail with info about complaint
     *
     * @param complaint this complaint
     * @param userId    user's id
     * @return success of the operation
     */
    boolean save(Complaint complaint, int userId);

    /**
     * Method for changing status of the complaint.
     * It updates complaint and sends email to user
     * with info about new status
     *
     * @param complaintId complaint's id
     * @param statusId    id of new status
     * @return success of the operation
     */
    boolean changeStatus(int complaintId, int statusId);

}
