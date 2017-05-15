package jtelecom.repositories;

import jtelecom.dao.complaint.ComplaintStatus;
import jtelecom.dto.FullComplaintInfoDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
}
