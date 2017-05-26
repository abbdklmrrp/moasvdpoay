package jtelecom.controller.csr;

import jtelecom.dao.operationHistory.OperationHistoryDAO;
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
@RequestMapping({"csr", "residential", "business"})
public class OperationHistoryController {
    @Resource
    private OperationHistoryDAO operationHistoryDAO;
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;
    private static Logger logger = LoggerFactory.getLogger(OperationHistoryController.class);

    @RequestMapping(value = "userHistory", method = RequestMethod.GET)
    public ModelAndView viewOrders() throws IOException {
        return new ModelAndView("newPages/csr/UserHistory");
    }

    /**
     * This method gets GridRequestDto( see the {@link jtelecom.grid.GridRequestDto}. <br>
     * After method gets list with all operations from database.<br>
     * This user's id method gets from session.
     *
     * @param request contains indexes for first element and last elements and patterns for search and sort
     * @return class which contains number of all elements with such parameters and some interval of the data
     */
    @RequestMapping(value = "getOperationHistory", method = RequestMethod.GET)
    public ListHolder getOperationHistory(@ModelAttribute GridRequestDto request, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return getList(userId, request);
    }

    @RequestMapping(value = "operationsHistory", method = RequestMethod.GET)
    public ModelAndView operationsHistory() {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        return new ModelAndView("newPages/" + user.getRole().getName().toLowerCase() + "/OperationHistory");
    }

    /**
     * This method gets GridRequestDto( see the {@link jtelecom.grid.GridRequestDto}. <br>
     * After method gets list with all operations from database.<br>
     * This user's id method gets from the security current user.
     *
     * @param request contains indexes for first element and last elements and patterns for search and sort
     * @return class which contains number of all elements with such parameters and some interval of the data
     */
    @RequestMapping(value = "getOrderHistory", method = RequestMethod.GET)
    public ListHolder getOrderHistory(@ModelAttribute GridRequestDto request) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        return getList(user.getId(), request);
    }

    /**
     * Base method for {@link jtelecom.controller.csr.OperationHistoryController#getOrderHistory(GridRequestDto)} <br>
     * and {@link jtelecom.controller.csr.OperationHistoryController#getOperationHistory(GridRequestDto, HttpSession)}
     *
     * @param userId  id of the user
     * @param request contains indexes for first element and last elements and patterns for search and sort
     * @return class which contains number of all elements with such parameters and some interval of the data
     */
    private ListHolder getList(Integer userId, GridRequestDto request) {
        String sort = request.getSort();
        String search = request.getSearch();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        logger.debug("Get operation history in interval:" + start + " : " + length);
        List<FullInfoOrderDTO> data = operationHistoryDAO.getOperationHistoryByUserId(userId, start, length, sort, search);
        int size = operationHistoryDAO.getCountOperationForUser(userId, search);
        return ListHolder.create(data, size);
    }

}
