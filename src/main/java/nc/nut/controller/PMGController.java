package nc.nut.controller;

import nc.nut.dao.complaint.ComplaintDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author Rysakova Anna
 */
@Controller
@RequestMapping({"pmg"})
public class PMGController {
    @Resource
    private ComplaintDAO complaintDAO;

    @RequestMapping({"index"})
    public String index() {
        return "newPages/pmg/Profile";
    }

    @RequestMapping(value = "allComplaints")
    public ModelAndView getAllComplaints(){
        ModelAndView modelAndView = new ModelAndView("newPages/pmg/allComplaints");
        modelAndView.addObject("complaints", complaintDAO.getAllWithoutPMGId());
        return modelAndView;
    }
}
