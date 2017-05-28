package jtelecom.controller;

import jtelecom.dao.complaint.Complaint;
import jtelecom.dao.complaint.ComplaintDAO;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.DataPartitionDTO;
import jtelecom.repositories.FullComplaintInfoRepository;
import jtelecom.security.SecurityAuthenticationHelper;
import jtelecom.services.complaint.ComplaintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * This controller has main methods for PMG.
 *
 * @author Revniuk Aleksandr
 * @author Rysakova Anna
 */
@Controller
@RequestMapping("pmg")
public class PMGController {
    @Resource
    private ComplaintDAO complaintDAO;

    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;

    @Resource
    private UserDAO userDAO;

    @Resource
    private FullComplaintInfoRepository fullComplaintInfoRepository;

    @Resource
    private ComplaintService complaintService;

    private static Logger logger = LoggerFactory.getLogger(PMGController.class);

    @RequestMapping(value = "allComplaints")
    public String getAllComplaints() {
        return "newPages/pmg/allComplaints";
    }

    @RequestMapping(value = "myComplaints")
    public String getMyComplaints() {
        return "newPages/pmg/myComplaints";
    }

    @RequestMapping(value = "getData")
    @ResponseBody
    public DataPartitionDTO getData(@RequestParam(name = "start") int startIndex, @RequestParam(name = "end") int endIndex) {
        DataPartitionDTO<Complaint> dto = new DataPartitionDTO<>();
        dto.setAmount(complaintDAO.countUnassignedComplaintsToUser());
        dto.setPartOfData(complaintDAO.getIntervalOfUnassignedComplaints(startIndex, endIndex));
        return dto;
    }

    @RequestMapping(value = "complaintInfo")
    public String compalaintInfo(Model model, @RequestParam(name = "id") int id) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        model.addAttribute("complaint", fullComplaintInfoRepository.getById(id));
        model.addAttribute("currentUserId", user.getId());
        return "newPages/pmg/complaintInfo";
    }

    @RequestMapping(value = "getDataByPMG")
    @ResponseBody
    public DataPartitionDTO getDataByPMG(@RequestParam(name = "start") int startIndex, @RequestParam(name = "end") int endIndex) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        DataPartitionDTO<Complaint> dto = new DataPartitionDTO<>();
        dto.setAmount(complaintDAO.countAssignedComplaintsToUser(user.getId()));
        dto.setPartOfData(complaintDAO.getIntervalOfAssignedComplaints(user.getId(), startIndex, endIndex));
        return dto;
    }

    @RequestMapping(value = "assignTo")
    public String assignTo(Model model, @RequestParam(name = "id") int id) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        boolean success = complaintDAO.assignToUser(id, user.getId());
        if (success) {
            logger.debug("Complaint with id {} successful assigned to user with id {}", id, user.getId());
            return "forward:complaintInfo?id=" + id;
        } else {
            logger.error("Can't assign complaint with id {} to user with id {}", id, user.getId());
            model.addAttribute("msg", "Can't be assinged to you");
            return "forward:complaintInfo?id=" + id;
        }
    }

    @RequestMapping(value = "changeStatus")
    public String changeStatus(Model model, @RequestParam(name = "id") int id, @RequestParam(name = "statusId") int statusId) {
        boolean success = complaintService.changeStatus(id, statusId);
        if (success) {
            logger.debug("Status of complaint with id {} successful changed to status {}", id, statusId);
            if (statusId == 3) {
                return "redirect:myComplaints";
            } else {
                return "forward:complaintInfo?id=" + id;
            }
        } else {
            logger.error("Can't change status for complaint with id {}", id);
            model.addAttribute("msg", "Can't change status");
            return "forward:complaintInfo?id=" + id;
        }
    }
}
