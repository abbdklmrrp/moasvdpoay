package jtelecom.controller.user;

import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.FullComplaintInfoDTO;
import jtelecom.dto.FullInfoOrderDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.repositories.FullComplaintInfoRepository;
import jtelecom.security.SecurityAuthenticationHelper;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 16.05.2017.
 */
@RestController
@RequestMapping({"business","residential"})
public class ComplaintHistoryController {
    @Resource
    private UserDAO userDAO;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private FullComplaintInfoRepository fullComplaintInfoRepository;

    @RequestMapping(value="complaintHistory",method=RequestMethod.GET)
    public ModelAndView complaintHistory(){
        User user=userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        return new ModelAndView("newPages/"+user.getRole().getName().toLowerCase()+"/ComplaintHistory");
    }
    @RequestMapping(value="getComplaintHistory",method=RequestMethod.GET)
    public ListHolder getComplaintHistory(@ModelAttribute GridRequestDto request){
        User user=userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        Integer userId=user.getId();
        String sort = request.getSort();
        String search=request.getSearch();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        System.out.println(request.toString());
        List<FullComplaintInfoDTO> data = fullComplaintInfoRepository.getIntervalComplaintsByUserId(start,length,sort,search,userId);
        int size = fullComplaintInfoRepository.getCountComplaintsByUserId(userId,search);
        System.out.println(size+" "+data.size());
        return ListHolder.create(data, size);
    }

}
