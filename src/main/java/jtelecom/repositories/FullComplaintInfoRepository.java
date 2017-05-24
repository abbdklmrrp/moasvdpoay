package jtelecom.repositories;

import jtelecom.dao.complaint.ComplaintStatus;
import jtelecom.dto.FullComplaintInfoDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class need to get full information about concrete complaint.
 *
 * @author Aleksandr Revniuk
 */
@Repository
public class FullComplaintInfoRepository {

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_FULL_INFO_ABOUT_COMPLAINT_BY_ID_SQL = "SELECT COMPLAINTS.ID, " +
            "COMPLAINTS.CREATING_DATE, \n" +
            "  COMPLAINTS.DESCRIPTION, \n" +
            "  COMPLAINTS.STATUS_ID , \n" +
            "  COMPLAINTS.PMG_ID , \n" +
            "  PRODUCTS.NAME PRODUCT_NAME, \n" +
            "  USERS.NAME USER_NAME, \n" +
            "  USERS.SURNAME, \n" +
            "  USERS.PHONE\n" +
            "FROM COMPLAINTS\n" +
            "  INNER JOIN ORDERS ON COMPLAINTS.ORDER_ID = ORDERS.ID\n" +
            "  INNER JOIN PRODUCTS ON ORDERS.PRODUCT_ID = PRODUCTS.ID\n" +
            "  INNER JOIN USERS ON ORDERS.USER_ID = USERS.ID\n" +
            "WHERE COMPLAINTS.ID = :complaintId";

    private static final String SELECT_FULL_INFO_COMPLAINT_BY_USER_ID = "Select * from ( \n" +
            "Select creatingDate,DESCRIPTION,STATUS_ID,productName, ROW_NUMBER() OVER (ORDER BY %s) R from ( \n" +
            "SELECT \n" +
            " COMPLAINTS.CREATING_DATE creatingDate, \n" +
            "  COMPLAINTS.DESCRIPTION description , \n" +
            "  COMPLAINTS.STATUS_ID status_id, \n" +
            "  PRODUCTS.NAME productName \n" +
            "FROM COMPLAINTS \n" +
            "  INNER JOIN ORDERS ON COMPLAINTS.ORDER_ID = ORDERS.ID \n" +
            "  INNER JOIN PRODUCTS ON ORDERS.PRODUCT_ID = PRODUCTS.ID \n" +
            "  INNER JOIN USERS ON ORDERS.USER_ID = USERS.ID \n" +
            "WHERE USERS.ID IN (SELECT ID FROM USERS WHERE CUSTOMER_ID=(SELECT CUSTOMER_ID FROM USERS WHERE ID=:userId)))) \n" +
            "where R>:start and R<=:length AND (productName like :pattern)";
    private static final String SELECT_COUNT_OF_COMPLAINT_BY_USER_ID = "Select count(*) from ( \n" +
            " Select creatingDate,DESCRIPTION,STATUS_ID,productName, ROW_NUMBER() OVER (ORDER BY creatingDate) R from ( \n" +
            " SELECT \n" +
            " COMPLAINTS.CREATING_DATE creatingDate, \n" +
            "  COMPLAINTS.DESCRIPTION description ,\n" +
            "  COMPLAINTS.STATUS_ID status_id, \n" +
            "  PRODUCTS.NAME productName \n" +
            " FROM COMPLAINTS \n" +
            "  INNER JOIN ORDERS ON COMPLAINTS.ORDER_ID = ORDERS.ID \n" +
            "  INNER JOIN PRODUCTS ON ORDERS.PRODUCT_ID = PRODUCTS.ID \n" +
            "  INNER JOIN USERS ON ORDERS.USER_ID = USERS.ID \n" +
            " WHERE USERS.ID IN (SELECT ID FROM USERS WHERE CUSTOMER_ID=(SELECT CUSTOMER_ID FROM USERS WHERE ID=:userId)))) \n" +
            " where productName like :pattern";


    /**
     * This method return complaint by id.
     *
     * @param id id of complaint
     * @return complaint
     */
    public FullComplaintInfoDTO getById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("complaintId", id);
        return jdbcTemplate.queryForObject(GET_FULL_INFO_ABOUT_COMPLAINT_BY_ID_SQL, params, (rs, rowNum) -> {
            FullComplaintInfoDTO complaint = new FullComplaintInfoDTO();
            complaint.setId(rs.getInt("ID"));
            Calendar date = new GregorianCalendar();
            rs.getDate("CREATING_DATE", date);
            complaint.setCreatingDate(date);
            complaint.setDescription(rs.getString("DESCRIPTION"));
            Integer statusId = rs.getInt("STATUS_ID");
            complaint.setStatus(ComplaintStatus.getOperationStatusById(statusId));
            complaint.setPmgId(rs.getInt("PMG_ID"));
            complaint.setProductName(rs.getString("PRODUCT_NAME"));
            complaint.setUserName(rs.getString("USER_NAME"));
            complaint.setUserSurname(rs.getString("SURNAME"));
            complaint.setUserPhone(rs.getString("PHONE"));
            return complaint;
        });
    }

    public Integer getCountComplaintsByUserId(int userId, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_COUNT_OF_COMPLAINT_BY_USER_ID, params, Integer.class);
    }

    public List<FullComplaintInfoDTO> getIntervalComplaintsByUserId(int start, int length, String sort, String search, int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sort.isEmpty()) {
            sort = "creatingDate";
        }
        params.addValue("start", start);
        params.addValue("length", length);
        params.addValue("pattern", "%" + search + "%");
        params.addValue("userId", userId);
        String sql = String.format(SELECT_FULL_INFO_COMPLAINT_BY_USER_ID, sort);
        return jdbcTemplate.query(sql, params, (rs, rownum) -> {
            FullComplaintInfoDTO complaint = new FullComplaintInfoDTO();
            Calendar date = new GregorianCalendar();
            rs.getDate("creatingDate", date);
            complaint.setCreatingDate(date);
            complaint.setProductName(rs.getString("productName"));
            complaint.setDescription(rs.getString("description"));
            Integer statusId = rs.getInt("status_id");
            complaint.setStatus(ComplaintStatus.getOperationStatusById(statusId));
            return complaint;
        });

    }
}
