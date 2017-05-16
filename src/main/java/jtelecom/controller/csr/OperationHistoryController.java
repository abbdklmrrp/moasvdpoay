package jtelecom.controller.csr;

import jtelecom.dao.operationHistory.OperationHistoryDao;
import jtelecom.dao.operationHistory.OperationHistoryRecord;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.dto.FullInfoOrderDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import jtelecom.security.SecurityAuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 11.05.2017.
 */
@RestController
@RequestMapping({"csr","residential","business"})
public class OperationHistoryController {
    @Resource
    private OperationHistoryDao operationHistoryDao;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;
    private static Logger logger = LoggerFactory.getLogger(OperationHistoryController.class);

    @RequestMapping(value = "userOrders", method = RequestMethod.GET)
    public ModelAndView viewOrders() throws IOException {
        return new ModelAndView("newPages/csr/UserOrders");
    }

    @RequestMapping(value = "getOperationHistory", method = RequestMethod.GET)
    public ListHolder getOperationHistory(@ModelAttribute GridRequestDto request, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return getList(userId,request);
    }
    @RequestMapping(value="operationsHistory",method=RequestMethod.GET)
    public ModelAndView operationsHistory(){
        User user=userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        return new ModelAndView("newPages/"+user.getRole().getName().toLowerCase()+"/OperationHistory");
    }

    @RequestMapping(value="getOrderHistory",method=RequestMethod.GET)
    public ListHolder getOrderHistory(@ModelAttribute GridRequestDto request){
        User user=userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        return getList(user.getId(),request);
    }

    private ListHolder getList(Integer userId,GridRequestDto request){
        String sort = request.getSort();
        String search=request.getSearch();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        System.out.println(request.toString());
        logger.debug("Get operation history in interval:" + start + " : " + length);
        List<FullInfoOrderDTO> data = operationHistoryDao.getOperationHistoryByUserId(userId, start, length, sort,search);
        int size = operationHistoryDao.getCountOperationForUser(userId,search);
        System.out.println(size+" "+data.size());
        return ListHolder.create(data, size);
    }

}
