package jtelecom.controller;

import jtelecom.dao.complaint.ComplaintDAO;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.ComplaintDataPartitionDTO;
import jtelecom.repositories.FullComplaintInfoRepository;
import jtelecom.security.SecurityAuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Rysakova Anna, Revniuk Aleksandr
 */
@Controller
@RequestMapping({"pmg"})
public class PMGController {
    @Resource
    private ComplaintDAO complaintDAO;

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    @Resource
    private UserDAO userDAO;

    @Resource
    private FullComplaintInfoRepository fullComplaintInfoRepository;

    private static Logger logger = LoggerFactory.getLogger(PMGController.class);

    @RequestMapping(value = "allComplaints")
    public String getAllComplaints() {
        return "newPages/pmg/allComplaints";
    }

    @RequestMapping(value = "myComplaints")
    public String getMyComplaints() {
        return "newPages/pmg/MyComplaints";
    }

    @RequestMapping(value = "getData")
    @ResponseBody
    public ComplaintDataPartitionDTO getData(@RequestParam(name = "start") int startIndex, @RequestParam(name = "end") int endIndex) {
        ComplaintDataPartitionDTO dto = new ComplaintDataPartitionDTO();
        dto.setAmount(complaintDAO.countUnassignedComplaintsToUser());
        dto.setPartOfComplaints(complaintDAO.getIntervalOfUnassignedComplaints(startIndex, endIndex));
        return dto;
    }

    @RequestMapping(value = "complaintInfo")
    public String compalintInfo(Model model, @RequestParam(name = "id") int id) {
        model.addAttribute("complaint", fullComplaintInfoRepository.getById(id));
        return "newPages/pmg/ComplaintInfo";
    }

    @RequestMapping(value = "getDataByPMG")
    @ResponseBody
    public ComplaintDataPartitionDTO getDataByPMG(@RequestParam(name = "start") int startIndex, @RequestParam(name = "end") int endIndex) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        ComplaintDataPartitionDTO dto = new ComplaintDataPartitionDTO();
        dto.setAmount(complaintDAO.countAssignedComplaintsToUser(user.getId()));
        dto.setPartOfComplaints(complaintDAO.getIntervalOfAssignedComplaints(user.getId(), startIndex, endIndex));
        return dto;
    }

    @RequestMapping(value = "assignTo")
    public String assignTo(Model model, @RequestParam(name = "id") int id) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        boolean success = complaintDAO.assignToUser(id, user.getId());
        if (!success) {
            logger.error("Can't assign complaint with id {} to user with id {}", id, user.getId());
            model.addAttribute("msg", "Can't be assinged to you");
        }
        logger.debug("Complaint with id {} successful assigned to user with id {}", id, user.getId());
        model.addAttribute("complaint", fullComplaintInfoRepository.getById(id));
        return "newPages/pmg/ComplaintInfo";
    }

    @RequestMapping(value = "changeStatus")
    public String changeStatus(Model model, @RequestParam(name = "id") int id, @RequestParam(name = "statusId") int statusId) {
        boolean success = complaintDAO.changeStatus(id, statusId);
        if (success) {
            logger.debug("Status of complaint with id {} successful changed to status {}", id, statusId);
            if (statusId == 3) {
                //TODO send email
                return "newPages/pmg/MyComplaints";
            } else {
                model.addAttribute("complaint", fullComplaintInfoRepository.getById(id));
                return "newPages/pmg/ComplaintInfo";
            }
        } else {
            logger.error("Can't change status for complaint with id {}", id);
            model.addAttribute("msg", "Can't change status");
            model.addAttribute("complaint", fullComplaintInfoRepository.getById(id));
            return "newPages/pmg/ComplaintInfo";
        }
    }
}
