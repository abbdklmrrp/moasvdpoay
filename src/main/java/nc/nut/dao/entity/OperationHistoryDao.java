package nc.nut.dao.entity;

import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 10.05.2017.
 */
public interface OperationHistoryDao {

    List<OperationHistoryRecord> getOperationHistoryByUserId(Integer userId,int start,int length, String order);

    Integer getCountOperationForUser(Integer userId);
}
