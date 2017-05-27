package jtelecom.services.complaint;

import jtelecom.dao.complaint.Complaint;
import jtelecom.dao.complaint.ComplaintDAO;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.services.mail.MailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Moiseienko Petro
 * @since 20.05.2017.
 */
@Service
public class ComplaintServiceImp implements ComplaintService {
    @Resource
    private ComplaintDAO complaintDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private MailService mailService;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(Complaint complaint, int userId) {
        Integer complaintId = complaintDAO.saveComplaint(complaint);
        if (complaintId != null) {
            User user = userDAO.getUserById(userId);
            mailService.sendComplaintSentEmail(user, complaintId);
        }
        return complaintId != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeStatus(int complaintId, int statusId) {
        boolean success = complaintDAO.changeStatus(complaintId, statusId);
        if (success) {
            User user = userDAO.getUserByComplaintId(complaintId);
            if (statusId == 2) {
                mailService.sendComplaintProcessingEmail(user, complaintId);
            } else if (statusId == 3) {
                mailService.sendComplaintProcessedEmail(user, complaintId);
            }
        }
        return success;
    }
}
