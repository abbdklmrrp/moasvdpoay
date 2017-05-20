package jtelecom.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuliya Pedash on 20.05.2017.
 */
@Service
public class ReportDataDao {
    private static Logger logger = LoggerFactory.getLogger(ReportDataDao.class);
    private final static String SELECT_NUMBERS_OF_ORDERS_FOR_TIME_PERIODS_BY_PLACE_SQL = "SELECT\n" +
            "  COUNT(*)                          COUNT,\n" +
            "  to_char(OPERATION_DATE, '<step>') TIME_PERIOD\n " +
            "FROM OPERATIONS_HISTORY\n" +
            "  INNER JOIN ORDERS ON ORDERS.ID = OPERATIONS_HISTORY.ORDER_ID\n" +
            "  INNER JOIN USERS ON USERS.ID = ORDERS.USER_ID\n " +
            "  INNER JOIN PLACES ON USERS.PLACE_ID = PLACES.ID " +
            "WHERE OPERATION_DATE BETWEEN TO_DATE(:date_begin, 'YYYY/MM/DD') AND TO_DATE(:date_end, 'YYYY/MM/DD')\n " +
            "      AND (USERS.PLACE_ID = :place_id OR PLACES.PARENT_ID = :place_id) " +
            "GROUP BY to_char(OPERATION_DATE, '<step>')";
    private final static String SELECT_NUMBER_OF_COMPLAINTS_FOR_TIME_PERIOD_BY_PLACE_SQL = "SELECT\n" +
            "  COUNT(*)                          COUNT,\n" +
            "  to_char(CREATING_DATE, '<step>') TIME_PERIOD\n" +
            "FROM COMPLAINTS\n" +
            "  INNER JOIN ORDERS ON ORDERS.ID = COMPLAINTS.ORDER_ID\n" +
            "  INNER JOIN USERS ON USERS.ID = ORDERS.USER_ID\n " +
            "  INNER JOIN PLACES ON USERS.PLACE_ID = PLACES.ID " +
            "WHERE CREATING_DATE BETWEEN TO_DATE(:date_begin, 'YYYY/MM/DD') AND TO_DATE(:date_end, 'YYYY/MM/DD')\n" +
            "          AND (USERS.PLACE_ID = :place_id OR PLACES.PARENT_ID = :place_id) " +
            "GROUP BY to_char(CREATING_DATE, '<step>')";
    private final static String REGEX = "<step>";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Map<String, Integer> getOrdersReportsDataMap(String dateBeginStr, String dateEndStr, Integer placeId, String stepPattern) {
        return getReportDataMap(SELECT_NUMBERS_OF_ORDERS_FOR_TIME_PERIODS_BY_PLACE_SQL, dateBeginStr, dateEndStr, placeId, stepPattern);
    }

    public Map<String, Integer> getComplaintsReportsDataMap(String dateBeginStr, String dateEndStr, Integer placeId, String stepPattern) {
        return getReportDataMap(SELECT_NUMBER_OF_COMPLAINTS_FOR_TIME_PERIOD_BY_PLACE_SQL, dateBeginStr, dateEndStr, placeId, stepPattern);
    }

    private Map<String, Integer> getReportDataMap(String selectSql, String dateBeginStr, String dateEndStr, Integer placeId, String stepPattern) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("date_begin", dateBeginStr);
        params.addValue("date_end", dateEndStr);
        params.addValue("place_id", placeId);
        String selectResSQL = selectSql.replaceAll(REGEX, stepPattern);
        return jdbcTemplate.query(selectResSQL, params, rs -> {
            Map<String, Integer> mapRet = new HashMap<>();
            while (rs.next()) {
                mapRet.put(rs.getString("time_period"), rs.getInt("count"));
            }
            return mapRet;
        });
    }
}
