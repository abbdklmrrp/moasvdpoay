package jtelecom.dao.operationHistory;

import jtelecom.dto.FullComplaintInfoDTO;
import jtelecom.dto.FullInfoOrderDTO;

import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 10.05.2017.
 */
public interface OperationHistoryDao {

    List<FullInfoOrderDTO> getOperationHistoryByUserId(Integer userId, int start, int length, String order, String search);

    Integer getCountOperationForUser(Integer userId, String search);

    Integer getCountOperationsByOrderId(int orderId);

    List<FullInfoOrderDTO> getIntervalOfOperationsByOrderId(int startIndex, int endIndex,int orderId);
}
