package jtelecom.controller.csr;

import jtelecom.dao.operationHistory.OperationHistoryDao;
import jtelecom.dao.operationHistory.OperationHistoryRecord;
import jtelecom.dao.order.OrderDao;
import jtelecom.dto.OperationHistoryDataPartitionDTO;
import jtelecom.dto.OrdersRowDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 14.05.2017.
 */
@RestController
@RequestMapping({"pmg"})
public class PmgOrdersController {
    @Resource
    private OrderDao orderDao;
    @Resource
    private OperationHistoryDao operationHistoryDao;

    @RequestMapping(value = "getOrders", method = RequestMethod.GET)
    public ListHolder getOrders(@ModelAttribute GridRequestDto request, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        String sort = request.getSort();
        String search = request.getSearch();
        Integer count = orderDao.getCountOrdersByUserId(userId, search);
        List<OrdersRowDTO> orders = orderDao.getIntervalOrdersBuUserId(start, length, sort, search, userId);
        return ListHolder.create(orders, count);
    }

    @RequestMapping(value = "getHistory",method = RequestMethod.GET)
    public OperationHistoryDataPartitionDTO getData(@RequestParam(name = "start") int startIndex,
                                             @RequestParam(name = "end") int endIndex,
                                             @RequestParam(name="orderId") int orderId) {
        Integer amount=operationHistoryDao.getCountOperationsByOrderId(orderId);
        List<OperationHistoryRecord> history=operationHistoryDao.getIntervalOfOperationsByOrderId(startIndex,endIndex,orderId);
        OperationHistoryDataPartitionDTO historyDto=new OperationHistoryDataPartitionDTO(amount,history);
        System.out.println(historyDto);
        return historyDto;


    }
}
