package nc.nut.reports;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by Yuliya Pedash on 01.05.2017.
 */
@Service
public class ReportsService {
    private final static String SELECT_NUMBERS_OF_ORDERS_FOR_TIME_PERIODS_BY_PLACE_SQL = "SELECT\n" +
            "  COUNT(*)                          COUNT,\n" +
            "  to_char(OPERATION_DATE, '<step>') TIME_PERIOD\n " +
            "FROM OPERATIONS_HISTORY\n" +
            "  INNER JOIN ORDERS ON ORDERS.ID = OPERATIONS_HISTORY.ORDER_ID\n" +
            "  INNER JOIN USERS ON USERS.ID = ORDERS.USER_ID\n " +
            "WHERE OPERATION_DATE BETWEEN TO_DATE(:date_begin, 'DD/MM/YYYY') AND TO_DATE(:date_end, 'DD/MM/YYYY')\n " +
            "      AND USERS.PLACE_ID = :place_id\n " +
            "GROUP BY to_char(OPERATION_DATE, '<step>')";
    private final static String SELECT_NUMBER_OF_COMPLAINTS_FOR_TIME_PERIOD_BY_PLACE_SQL = "SELECT\n" +
            "  COUNT(*)                          COUNT,\n" +
            "  to_char(CREATING_DATE, '<step>') TIME_PERIOD\n" +
            "FROM COMPLAINTS\n" +
            "  INNER JOIN ORDERS ON ORDERS.ID = COMPLAINTS.ORDER_ID\n" +
            "  INNER JOIN USERS ON USERS.ID = ORDERS.USER_ID\n " +
            "WHERE CREATING_DATE BETWEEN TO_DATE(:date_begin, 'DD/MM/YYYY') AND TO_DATE(:date_end, 'DD/MM/YYYY')\n" +
            "      AND USERS.PLACE_ID = :place_id\n " +
            "GROUP BY to_char(CREATING_DATE, '<step>')";
    private final static String dayPeriodPattern = "dd-MM-yyyy";
    private final static String monthPeriodPattern = "MM-yyyy";
    private final static String yearPeriodPattern = "yyyy";
    private final static String REGEX = "<step>";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    private Map<String, Integer> getOrdersReportsDataMap(String dateBeginStr, String dateEndStr, Integer placeId, String stepPattern) {
        return getReportDataMap(SELECT_NUMBERS_OF_ORDERS_FOR_TIME_PERIODS_BY_PLACE_SQL, dateBeginStr, dateEndStr, placeId, stepPattern);
    }

    private Map<String, Integer> getComplaintsReportsDataMap(String dateBeginStr, String dateEndStr, Integer placeId, String stepPattern) {
        return getReportDataMap(SELECT_NUMBER_OF_COMPLAINTS_FOR_TIME_PERIOD_BY_PLACE_SQL, dateBeginStr, dateEndStr, placeId, stepPattern);
    }

    private Map<String, Integer> getReportDataMap(String selectSql, String dateBeginStr, String dateEndStr, Integer placeId, String stepPattern) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("date_begin", dateBeginStr);
        params.addValue("date_end", dateEndStr);
        params.addValue("place_id", placeId);
        //   params.addValue("step", stepPattern);
        String selectResSQL = selectSql.replaceAll(REGEX, stepPattern);
        return jdbcTemplate.query(selectResSQL, params, rs -> {
            Map<String, Integer> mapRet = new HashMap<>();
            while (rs.next()) {
                mapRet.put(rs.getString("time_period"), rs.getInt("count"));
            }
            return mapRet;
        });
    }

    private List<ReportData> formFullReportData(Map<String, Integer> complaintsData, Map<String, Integer> ordersData, Calendar beginDate, Calendar endDate, final int PERIOD_TO_ADD) {
        List<ReportData> reportDataList = new ArrayList<>();
        Calendar currDate = (Calendar) beginDate.clone();
        while (currDate.compareTo(endDate) <= 0 || (PERIOD_TO_ADD == Calendar.MONTH && currDate.get(Calendar.MONTH) == endDate.get(Calendar.MONTH) && currDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) || (PERIOD_TO_ADD == Calendar.YEAR && currDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR))) {
            ReportData reportData = new ReportData();
            String currDateStr = getCurrentDateByPeriodToAdd(currDate, PERIOD_TO_ADD);
            reportData.setTimePeriod(currDateStr);
            reportData.setComplaintsCount(complaintsData.getOrDefault(currDateStr, 0));
            reportData.setOrdersCount(ordersData.getOrDefault(currDateStr, 0));
            currDate.add(PERIOD_TO_ADD, 1);
            reportDataList.add(reportData);
        }
        return reportDataList;

    }

    private String getCurrentDateByPeriodToAdd(Calendar date, final int PERIOD) {

        switch (PERIOD) {
            case (Calendar.DATE):
                return (String.format("%02d", date.get(Calendar.DATE)) + "-" + String.format("%02d", date.get(Calendar.MONTH)) + "-" + String.format("%04d", date.get(Calendar.YEAR)));
            case (Calendar.MONTH):
                return (String.format("%02d", date.get(Calendar.MONTH)) + "-" + String.format("%04d", date.get(Calendar.YEAR)));
            case (Calendar.YEAR):
                return String.format("%04d", date.get(Calendar.YEAR));
        }
        return null;
    }

    public List<ReportData> getDataForReport(String beginDateStr, String endDateStr, int placeId) throws ReportCreatingException {
        String stepPattern;
        final int PERIOD_TO_ADD;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dayPeriodPattern);
        Calendar beginDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        //todo determine which one is wrong for error msg
        try {
            beginDate.setTime(simpleDateFormat.parse(beginDateStr));
            endDate.setTime(simpleDateFormat.parse(endDateStr));
        } catch (ParseException e) {
            throw new ReportCreatingException("Unable to parse begin or end date.");
        }
        long daysDifference = ChronoUnit.DAYS.between(beginDate.toInstant(), endDate.toInstant());
        if (daysDifference < 30 * 2) {
            stepPattern = dayPeriodPattern;
            PERIOD_TO_ADD = Calendar.DATE;
        } else if (daysDifference < 30 * 36) {
            stepPattern = monthPeriodPattern;
            PERIOD_TO_ADD = Calendar.MONTH;
        } else {
            stepPattern = yearPeriodPattern;
            PERIOD_TO_ADD = Calendar.YEAR;
        }
        Map<String, Integer> orderData = getOrdersReportsDataMap(beginDateStr, endDateStr, placeId, stepPattern);
        Map<String, Integer> complaintsData = getComplaintsReportsDataMap(beginDateStr, endDateStr, placeId, stepPattern);
        return formFullReportData(complaintsData, orderData, beginDate, endDate, PERIOD_TO_ADD);
    }
}





