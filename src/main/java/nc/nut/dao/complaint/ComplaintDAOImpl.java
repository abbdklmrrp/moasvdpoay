package nc.nut.dao.complaint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static String GET_ALL_BY_CSR_ID_SQL = "SELECT * FROM COMPLAINTS WHERE CSR_ID = :csrId";
    private final static String GET_ALL_BY_ORDER_ID_SQL = "SELECT * FROM COMPLAINTS WHERE ORDER_ID = :orderId";
    private final static String GET_ALL_BY_STATUS_ID_SQL = "SELECT * FROM COMPLAINTS WHERE STATUS_ID = :statusId";
    private final static String GET_BY_ID_SQL = "SELECT * FROM COMPLAINTS WHERE ID = :id";
    private final static String INSERT_COMPLAINT_SQL = "INSERT INTO Complaints(ORDER_ID,CSR_ID,CREATING_DATE,STATUS_ID,DESCRIPTION) \n" +
            "VALUES(:orderId,:csrId,:creatingDate,:statusId,:description)";
    private final static String GET_ALL_BY_PLACE_ID_SQL = "SELECT " +
            "   COMPLAINTS.ID, \n" +
            "   COMPLAINTS.ORDER_ID, \n" +
            "   COMPLAINTS.CSR_ID, \n" +
            "   COMPLAINTS.CREATING_DATE, \n" +
            "   COMPLAINTS.STATUS_ID, \n" +
            "   COMPLAINTS.DESCRIPTION\n" +
            "FROM COMPLAINTS\n" +
            "   INNER JOIN ORDERS ON ORDERS.ID = COMPLAINTS.ORDER_ID\n" +
            "   INNER JOIN USERS ON USERS.ID = ORDERS.USER_ID\n" +
            "   INNER JOIN PLACES ON USERS.PLACE_ID = PLACES.ID\n" +
            "   WHERE PLACES.ID = :placeId";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private ComplaintRowMapper complaintRowMapper;

    private static Logger logger = LoggerFactory.getLogger(ComplaintDAOImpl.class);

    @Override
    public Complaint getById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.queryForObject(GET_BY_ID_SQL, params, complaintRowMapper);
    }

    @Override
    public boolean update(Complaint object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean save(Complaint object) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderId", object.getOrderId());
        params.addValue("csrId", object.getCsrId());
        params.addValue("creatingDate", object.getCreationDate());
        params.addValue("statusId", object.getStatus().getId());
        params.addValue("description", object.getDescription());
        return jdbcTemplate.update(INSERT_COMPLAINT_SQL, params) > 0;

    }

    @Override
    public boolean delete(Complaint object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Complaint> getByCSRId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("csrId", id);
        return jdbcTemplate.query(GET_ALL_BY_CSR_ID_SQL, params, complaintRowMapper);
    }

    @Override
    public List<Complaint> getByOrderId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderId", id);
        return jdbcTemplate.query(GET_ALL_BY_ORDER_ID_SQL, params, complaintRowMapper);
    }

    @Override
    public List<Complaint> getByPlaceId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("placeId", id);
        return jdbcTemplate.query(GET_ALL_BY_PLACE_ID_SQL, params, complaintRowMapper);
    }

    @Override
    public List<Complaint> getByStatusID(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("statusId", id);
        return jdbcTemplate.query(GET_ALL_BY_STATUS_ID_SQL, params, complaintRowMapper);
    }

    @Override
    public boolean changeStatus(int complaintId, int statusId) {
        return false;
    }

    @Override
    public boolean changeDescription(int complaintId, String description) {
        return false;
    }

}
