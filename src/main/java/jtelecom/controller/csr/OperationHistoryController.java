package jtelecom.controller.csr;

import jtelecom.dao.operationHistory.OperationHistoryDao;
import jtelecom.dao.operationHistory.OperationHistoryRecord;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
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
@RequestMapping({"csr"})
public class OperationHistoryController {
    @Resource
    private OperationHistoryDao operationHistoryDao;
    private static Logger logger = LoggerFactory.getLogger(OperationHistoryController.class);

    @RequestMapping(value = "userOrders", method = RequestMethod.POST)
    public ModelAndView viewOrders() throws IOException {
        return new ModelAndView("newPages/csr/UserOrders");
    }

    @RequestMapping(value = "getOperationHistory", method = RequestMethod.GET)
    public ListHolder getOperationHistory(@ModelAttribute GridRequestDto request, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        logger.debug("Get operation history in interval:" + start + " : " + length);
        List<OperationHistoryRecord> data = operationHistoryDao.getOperationHistoryByUserId(userId, start, length, sort);
        int size = operationHistoryDao.getCountOperationForUser(userId);
        return ListHolder.create(data, size);
    }

}
