package nc.nut.dao.order;

import nc.nut.dao.interfaces.Dao;

import java.util.List;

/**
 * @author Revniuk Aleksandr
 */
public interface OrderDAO extends Dao<Order> {
    List<Order> getByPlaceId(int id);

    List<Order> getByStatusID(int id);

    boolean changeStatus(int complaintId, int statusId);

    boolean changeDescription(int complaintId, String description);

    List<Order> getDateInterval(int startYear, int startMonth, int endYear, int endMonth);
}
