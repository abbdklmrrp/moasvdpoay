package nc.nut.controller.csr;

import nc.nut.dao.entity.OperationHistoryDao;
import nc.nut.dao.entity.OperationHistoryRecord;
import nc.nut.grid.GridRequestDto;
import nc.nut.grid.ListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
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
public class UserOrdersController {
    @Resource
    private OperationHistoryDao operationHistoryDao;
    private static Logger logger = LoggerFactory.getLogger(UserOrdersController.class);

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
