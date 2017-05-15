package jtelecom.dao.operationHistory;

import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 10.05.2017.
 */
public interface OperationHistoryDao {

    List<OperationHistoryRecord> getOperationHistoryByUserId(Integer userId,int start,int length, String order);

    Integer getCountOperationForUser(Integer userId);

    Integer getCountOperationsByOrderId(int orderId);

    List<OperationHistoryRecord> getIntervalOfOperationsByOrderId(int startIndex, int endIndex,int orderId);
}
