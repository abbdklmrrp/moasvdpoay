package jtelecom.controller;

import jtelecom.dao.complaint.Complaint;
import jtelecom.dao.complaint.ComplaintDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({"pmg"})
public class PMGController {
    @Resource
    private ComplaintDAO complaintDAO;

    @RequestMapping(value = "allComplaints")
    public String getAllComplaints() {
        return "newPages/pmg/allComplaints";
    }

    @RequestMapping(value = "getData")
    @ResponseBody
    public ComplaintDataPartitionDTO getData(@RequestParam(name = "start") int startIndex, @RequestParam(name = "end") int endIndex) {
        ComplaintDataPartitionDTO dto = new ComplaintDataPartitionDTO();
        dto.setAmount(complaintDAO.countUnasignedComplaints());
        dto.setPartOfComplaints(complaintDAO.getIntervalOfUnassignedComplaints(startIndex, endIndex));
        return dto;
    }
}
