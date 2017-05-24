package jtelecom.reports;

import jtelecom.dao.reports.ReportDataDao;
import jtelecom.dao.reports.ReportDataDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(ReportsService.class);
    private final static String DAY_PERIOD_PATTERN = "dd-MM-yyyy";
    private final static String MONTH_PERIOD_PATTERN = "MM-yyyy";
    private final static String YEAR_PERIOD_PATTERN = "yyyy";
    private final static Integer DAYS_IN_MONTH = 30;
    private final static Integer MAX_NUMB_OF_MONTHS_FOR_DAY_PATTERN = 36;
    private final static Integer MAX_NUMB_OF_MONTHS_FOR_MONTH_PATTERN = 36;
    private String stepPattern;
    private int periodToAdd;
    @Resource
    private ReportDataDao reportDataDao;


    /**
     * This method takes <code>Map</code> with key <code>String</code> - time period and value <code>Integer</code> - number, begin
     * date and end date. <code>Mape</code> returned from {@link ReportDataDaoImpl} may miss some dates, which don't
     * have any statisticks values. Anyways, they  are needed for creating reports, so the goal of this method is to
     * put in final <code>List</code> of <code>ReportData</code> objects with these missed dates with value 0 and to
     * transform <code>Map</code> returned from {@link ReportDataDaoImpl} to list of <code>ReportData</code> objects.
     *
     * @param reportDataMap Map with key String<- time period and value Integer - number
     * @param beginDate     begin date
     * @param endDate       end date
     * @return list of ReportData objects
     * @see ReportData
     * @see ReportDataDaoImpl
     */
    private List<ReportData> formFullReportData(Map<String, Integer> reportDataMap, Calendar beginDate, Calendar endDate) {
        List<ReportData> reportDataList = new ArrayList<>();
        Calendar currDate = (Calendar) beginDate.clone();
        while (currDate.compareTo(endDate) <= 0 || (periodToAdd == Calendar.MONTH && currDate.get(Calendar.MONTH) == endDate.get(Calendar.MONTH) &&
                currDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) || (periodToAdd == Calendar.YEAR && currDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR))) {
            ReportData reportData = new ReportData();
            String currDateStr = getCurrentDateByPeriodToAdd(currDate, periodToAdd);
            reportData.setTimePeriod(currDateStr);
            reportData.setAmount(reportDataMap.getOrDefault(currDateStr, 0));
            currDate.add(periodToAdd, 1);
            reportDataList.add(reportData);
        }
        return reportDataList;

    }

    /**
     * This method takes date and depending on period returns formatted string. For example, if period equals
     * {@link Calendar#YEAR} formatted returned <code>String</code> will be formatted like yyyy.
     * If period equals {@link Calendar#MONTH} <code>String</code> will be formatted like MM-yyyy and so on.
     * To get more information on types of periods see
     * {@link Calendar#DATE}
     * {@link Calendar#MONTH}
     * {@link Calendar#YEAR}
     *
     * @param date   date
     * @param PERIOD period
     * @return formatted date
     */
    private String getCurrentDateByPeriodToAdd(Calendar date, final int PERIOD) {
        switch (PERIOD) {
            case (Calendar.DATE):
                return (String.format("%02d", date.get(Calendar.DATE)) + "-" + String.format("%02d", date.get(Calendar.MONTH) + 1) + "-" + String.format("%04d", date.get(Calendar.YEAR)));
            case (Calendar.MONTH):
                return (String.format("%02d", date.get(Calendar.MONTH) + 1) + "-" + String.format("%04d", date.get(Calendar.YEAR)));
            case (Calendar.YEAR):
                return String.format("%04d", date.get(Calendar.YEAR));
        }
        return null;
    }

    /**
     * This method returns list of <code>ReportData</code> objects for forming reports on orders;
     *
     * @param beginDateStr begin date String
     * @param endDateStr   end date String
     * @param placeId      id of place
     * @return list of <code>ReportData</code> objects
     * @throws ReportCreatingException if {@link #parseDates(String, String, Calendar, Calendar)} threw this Exception
     * @see ReportData
     * @see #formFullReportData(Map, Calendar, Calendar)
     * @see ReportDataDaoImpl#getOrdersReportsDataMap(String, String, Integer, String)
     */
    public List<ReportData> getOrdersReportData(String beginDateStr, String endDateStr, int placeId) throws ReportCreatingException {
        Calendar beginDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        parseDates(beginDateStr, endDateStr, beginDate, endDate);
        determineStepPatternAndPeriodToAdd(beginDate, endDate);
        Map<String, Integer> ordersReportsDataMap = reportDataDao.getOrdersReportsDataMap(beginDateStr, endDateStr, placeId, stepPattern);
        return formFullReportData(ordersReportsDataMap, beginDate, endDate);
    }

    /**
     * This method returns List of <code>ReportData</code> objects for forming reports on complaint.
     *
     * @param beginDateStr begin date String
     * @param endDateStr   end date String
     * @param placeId      id of place
     * @return list of <code>ReportData</code> objects
     * @throws ReportCreatingException if {@link #parseDates(String, String, Calendar, Calendar)} threw this Exception
     * @see #formFullReportData(Map, Calendar, Calendar)
     * @see ReportDataDaoImpl#getComplaintsReportsDataMap(String, String, Integer, String)
     * @see ReportData
     */
    public List<ReportData> getComplaintsReportData(String beginDateStr, String endDateStr, int placeId) throws ReportCreatingException {
        Calendar beginDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        parseDates(beginDateStr, endDateStr, beginDate, endDate);
        determineStepPatternAndPeriodToAdd(beginDate, endDate);
        Map<String, Integer> complaintsReportsDataMap = reportDataDao.getComplaintsReportsDataMap(beginDateStr, endDateStr, placeId, stepPattern);
        return formFullReportData(complaintsReportsDataMap, beginDate, endDate);
    }

    /**
     * This method parses begin date in form of string and end date in form of string to Calendar data type.
     * It takes four params, but params <code>Calendar</code> beginDate and <code>Calendar</code> are actually
     * used like out params in this case. They modified in the method for future use.
     *
     * @param beginDateStr begin date to parse
     * @param endDateStr   end date to parse
     * @param beginDate    parsed begin date
     * @param endDate      parsed end date
     * @throws ReportCreatingException if dates were of wrong format
     */
    private void parseDates(String beginDateStr, String endDateStr, Calendar beginDate, Calendar endDate) throws ReportCreatingException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            beginDate.setTime(simpleDateFormat.parse(beginDateStr));
            endDate.setTime(simpleDateFormat.parse(endDateStr));
        } catch (ParseException e) {
            logger.error(String.format("Unable to parse beginDate(%s) or endDate(%s) strings", beginDateStr, endDateStr));
            throw new ReportCreatingException("Unable to parse begin or end date");
        }
    }

    /**
     * This method takes begin date and end date. Then it finds out number of days between them
     * and depending on this number determines right pattern for querying results(by days,
     * by months or by years)
     *
     * @param beginDate begin date
     * @param endDate   end date
     */
    private void determineStepPatternAndPeriodToAdd(Calendar beginDate, Calendar endDate) {
        long daysDifference = ChronoUnit.DAYS.between(beginDate.toInstant(), endDate.toInstant());
        if (daysDifference < DAYS_IN_MONTH * MAX_NUMB_OF_MONTHS_FOR_DAY_PATTERN) {
            stepPattern = DAY_PERIOD_PATTERN;
            periodToAdd = Calendar.DATE;
        } else if (daysDifference < DAYS_IN_MONTH * MAX_NUMB_OF_MONTHS_FOR_MONTH_PATTERN) {
            stepPattern = MONTH_PERIOD_PATTERN;
            periodToAdd = Calendar.MONTH;
        } else {
            stepPattern = YEAR_PERIOD_PATTERN;
            periodToAdd = Calendar.YEAR;
        }
    }

}





