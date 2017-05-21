package jtelecom.dao.complaint;

import jtelecom.dao.user.User;
import jtelecom.dao.user.UserRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Revniuk Aleksandr
 */
@Repository
public class ComplaintDAOImpl implements ComplaintDAO {

    private final static String GET_ALL_BY_ORDER_ID_SQL = "SELECT * FROM COMPLAINTS WHERE ORDER_ID = :orderId";
    private final static String GET_ALL_BY_STATUS_ID_SQL = "SELECT * FROM COMPLAINTS WHERE STATUS_ID = :statusId";
    private final static String SET_PMG_ID_SQL = "UPDATE COMPLAINTS SET PMG_ID = :pmgId WHERE ID = :id AND PMG_ID IS NULL";
    private final static String GET_INTERVAL_WHERE_PMG_ID_IS_NULL_SORTED_BY_DATE_SQL = "SELECT * FROM\n" +
            "  (SELECT ID, ORDER_ID, CREATING_DATE, STATUS_ID, DESCRIPTION, PMG_ID, ROW_NUMBER() OVER (ORDER BY CREATING_DATE) R FROM COMPLAINTS WHERE PMG_ID IS NULL)\n" +
            "WHERE R > :startIndex AND R <= :endIndex";
    private final static String GET_INTERVAL_BY_PMG_ID_SQL = "SELECT * FROM\n" +
            "  (SELECT ID, ORDER_ID, CREATING_DATE, STATUS_ID, DESCRIPTION, PMG_ID, ROW_NUMBER() OVER (ORDER BY CREATING_DATE) R FROM COMPLAINTS WHERE PMG_ID = :pmgId)\n" +
            "WHERE R > :startIndex AND R <= :endIndex";
    private final static String GET_BY_ID_SQL = "SELECT * FROM COMPLAINTS WHERE ID = :id";
    private final static String INSERT_COMPLAINT_SQL = "INSERT INTO Complaints(ORDER_ID, CREATING_DATE, STATUS_ID, DESCRIPTION) \n" +
            "VALUES(:orderId, :creatingDate, :statusId, :description)";
    private final static String UPDATE_STATUS_ID_SQL = "UPDATE COMPLAINTS SET STATUS_ID = :statusId WHERE ID = :id";
    private final static String UPDATE_DESCRIPTION_SQL = "UPDATE COMPLAINTS SET DESCRIPTION = :description WHERE ID = :id";
    private final static String GET_ALL_BY_PLACE_ID_SQL = "SELECT " +
            "   COMPLAINTS.ID, \n" +
            "   COMPLAINTS.ORDER_ID, \n" +
            "   COMPLAINTS.PMG_ID, \n" +
            "   COMPLAINTS.CREATING_DATE, \n" +
            "   COMPLAINTS.STATUS_ID, \n" +
            "   COMPLAINTS.DESCRIPTION\n" +
            "FROM COMPLAINTS\n" +
            "   INNER JOIN ORDERS ON ORDERS.ID = COMPLAINTS.ORDER_ID\n" +
            "   INNER JOIN USERS ON USERS.ID = ORDERS.USER_ID\n" +
            "   INNER JOIN PLACES ON USERS.PLACE_ID = PLACES.ID\n" +
            "   WHERE PLACES.ID = :placeId";
    private final static String COUNT_COMPLAINTS_WHERE_PMG_ID_IS_NULL_SQL = "SELECT COUNT(ID)\n" +
            "  FROM COMPLAINTS\n" +
            "WHERE PMG_ID is NULL";
    private final static String COUNT_COMPLAINTS_BY_PMG_ID_SQL = "SELECT COUNT(ID)\n" +
            "  FROM COMPLAINTS\n" +
            "WHERE PMG_ID = :pmgId";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private ComplaintRowMapper complaintRowMapper;


    /**
     * This method returns complaint by id.
     *
     * @param id id of complaint
     * @return complaint
     */
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

    /**
     * This method save complaint.
     *
     * @param object object of complaint
     * @return <code>true</code> if operation was successful, <code>false</code> otherwise.
     */
    @Override
    public boolean save(Complaint object) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderId", object.getOrderId());
        params.addValue("creatingDate", object.getCreationDate());
        params.addValue("statusId", object.getStatus().getId());
        params.addValue("description", object.getDescription());
        return jdbcTemplate.update(INSERT_COMPLAINT_SQL, params) > 0;

    }

    @Override
    public Integer saveComplaint(Complaint complaint) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderId", complaint.getOrderId());
        params.addValue("creatingDate", complaint.getCreationDate());
        params.addValue("statusId", complaint.getStatus().getId());
        params.addValue("description", complaint.getDescription());
        KeyHolder key = new GeneratedKeyHolder();
        Integer productID = jdbcTemplate.update(INSERT_COMPLAINT_SQL, params, key, new String[]{"ID"});
        return key.getKey().intValue();
    }

    @Override
    public boolean delete(Complaint object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Complaint> getIntervalOfAssignedComplaints(int pmgId, int startIndex, int endIndex) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startIndex", startIndex);
        params.addValue("endIndex", endIndex);
        params.addValue("pmgId", pmgId);
        return jdbcTemplate.query(GET_INTERVAL_BY_PMG_ID_SQL, params, complaintRowMapper);
    }

    @Override
    public int countAssignedComplaintsToUser(int pmgId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pmgId", pmgId);
        return jdbcTemplate.queryForObject(COUNT_COMPLAINTS_BY_PMG_ID_SQL, params, Integer.class);
    }

    @Override
    public List<Complaint> getByOrderId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderId", id);
        return jdbcTemplate.query(GET_ALL_BY_ORDER_ID_SQL, params, complaintRowMapper);
    }

    @Override
    public List<Complaint> getIntervalOfUnassignedComplaints(int startIndex, int endIndex) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startIndex", startIndex);
        params.addValue("endIndex", endIndex);
        return jdbcTemplate.query(GET_INTERVAL_WHERE_PMG_ID_IS_NULL_SORTED_BY_DATE_SQL, params, complaintRowMapper);
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
    @Transactional
    public boolean assignToUser(int complaintId, int pmgId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pmgId", pmgId);
        params.addValue("id", complaintId);
        return jdbcTemplate.update(SET_PMG_ID_SQL, params) > 0;

    }

    @Override
    public boolean changeStatus(int complaintId, int statusId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("statusId", statusId);
        params.addValue("id", complaintId);
        return jdbcTemplate.update(UPDATE_STATUS_ID_SQL, params) > 0;
    }


    @Override
    public boolean changeDescription(int complaintId, String description) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("description", description);
        params.addValue("id", complaintId);
        return jdbcTemplate.update(UPDATE_DESCRIPTION_SQL, params) > 0;
    }

    @Override
    public int countUnassignedComplaintsToUser() {
        return jdbcTemplate.queryForObject(COUNT_COMPLAINTS_WHERE_PMG_ID_IS_NULL_SQL, new MapSqlParameterSource(), Integer.class);//TODO ask again
    }

}
