package nc.nut.dao.order;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Revniuk Aleksandr
 */
@Repository
public class OrderDAOImpl implements OrderDAO {

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Order getById(int id) {
        return null;
    }

    @Override
    public boolean update(Order object) {
        return false;
    }

    @Override
    public boolean save(Order object) {
        return false;
    }

    @Override
    public List<Order> getByPlaceId(int id) {
        return null;
    }

    @Override
    public boolean delete(Order object) {
        return false;
    }

    @Override
    public List<Order> getByStatusID(int id) {
        return null;
    }

    @Override
    public boolean changeStatus(int complaintId, int statusId) {
        return false;
    }

    @Override
    public boolean changeDescription(int complaintId, String description) {
        return false;
    }

    @Override
    public List<Order> getDateInterval(int startYear, int startMonth, int endYear, int endMonth) {
        return null;
    }
}
