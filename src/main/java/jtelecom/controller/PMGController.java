package jtelecom.controller;

import jtelecom.dao.complaint.ComplaintDAO;
import jtelecom.dto.ComplaintDataPartitionDTO;
import jtelecom.repositories.FullComplaintInfoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({"pmg"})
public class PMGController {
    @Resource
    private ComplaintDAO complaintDAO;

    @Resource
    private FullComplaintInfoRepository fullComplaintInfoRepository;

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

    @RequestMapping(value = "complaintInfo")
    public String compalintInfo(Model model,@RequestParam(name = "id")int id){
        model.addAttribute("complaint", fullComplaintInfoRepository.getById(id));
        return "newPages/pmg/ComplaintInfo";
    }
}
