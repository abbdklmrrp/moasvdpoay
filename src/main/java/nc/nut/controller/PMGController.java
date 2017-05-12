package nc.nut.controller;

import nc.nut.dao.complaint.Complaint;
import nc.nut.dao.complaint.ComplaintDAO;
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

    @RequestMapping(value = "getLength")
    @ResponseBody
    public int getData() {
        return complaintDAO.countComplaintsWithoutPMGId();
    }

    @RequestMapping(value = "getData")
    @ResponseBody
    public List<Complaint> getData(@RequestParam(name = "start") int startIndex, @RequestParam(name = "end") int endIndex) {
        return complaintDAO.getIntervalOfComplaintsWithoutPMGId(startIndex, endIndex);
    }
}
