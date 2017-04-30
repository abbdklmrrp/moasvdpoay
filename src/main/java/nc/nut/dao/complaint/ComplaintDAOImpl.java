package nc.nut.dao.complaint;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Revniuk Aleksandr
 */
@Repository
public class ComplaintDAOImpl implements ComplaintDAO {

    private final static String GET_ALL_BY_CSR_ID_SQL = "SELECT * FROM COMPLAINTS WHERE CSR_ID=:csr_id";
    private final static String GET_ALL_BY_ORDER_ID_SQL = "SELECT * FROM COMPLAINTS WHERE ORDER_ID=:order_id";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private ComplaintRowMapper complaintRowMapper;

    @Override
    public Complaint getById(int id) {
        return null;
    }

    @Override
    public boolean update(Complaint object) {
        return false;
    }

    @Override
    public boolean save(Complaint object) {
        return false;
    }

    @Override
    public boolean delete(Complaint object) {
        return false;
    }

    @Override
    public List<Complaint> getByCSRId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("csr_id", id);
        return jdbcTemplate.query(GET_ALL_BY_CSR_ID_SQL, params,complaintRowMapper);
    }

    @Override
    public List<Complaint> getByOrderId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("order_id", id);
        return jdbcTemplate.query(GET_ALL_BY_ORDER_ID_SQL, params,complaintRowMapper);
    }

    @Override
    public List<Complaint> getByPlaceId(int id) {
        return null;
    }

    @Override
    public List<Complaint> getByStatusID(int id) {
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
    public List<Complaint> getDateInterval(int startYear, int startMonth, int endYear, int endMonth) {
        return null;
    }
}
