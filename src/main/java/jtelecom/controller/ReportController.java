package jtelecom.controller;

import jtelecom.dao.place.Place;
import jtelecom.dao.place.PlaceDAO;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.reports.ReportCreatingException;
import jtelecom.reports.ReportData;
import jtelecom.reports.ReportsService;
import jtelecom.reports.excel.ExcelReportCreator;
import jtelecom.reports.excel.ReportType;
import jtelecom.reports.excel.WorkbookCreatingFailException;
import jtelecom.security.SecurityAuthenticationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Revniuk Aleksandr
 */
@Controller
@RequestMapping({"csr", "pmg"})
public class ReportController {
    @Resource
    private SecurityAuthenticationHelper securityAuthenticationHelper;
    @Resource
    private UserDAO userDAO;
    @Autowired
    private PlaceDAO placeDAO;
    @Autowired
    private ReportsService reportsService;
    private static final String ORDERS_REP_FILE_NAME = "orders%sto%s.xlsx";
    private static final String COMPLAINTS_REP_FILE_NAME = "complaints%sto%s.xlsx";
    private static final String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
    private static final String HEADER_VAR1 = "Content-Disposition";
    private static final String HEADER_VAR2 = "attachment; filename=";
    private static final String DATA_FORMAT = "yyyy-MM-dd";

    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    private boolean validateData(String beginDate, String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_FORMAT);
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        try {
            start.setTime(simpleDateFormat.parse(beginDate));
            end.setTime(simpleDateFormat.parse(endDate));
        } catch (ParseException e) {
            logger.error("Wrong date format", e);
            return false;
        }
        if (start.after(end)) {
            logger.error("Start date {} goes after end date {}", beginDate, endDate);
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/statistics")
    public String graph(Model model) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        List<Place> regions = placeDAO.getAll();
        model.addAttribute("regions", regions);
        return "newPages/" + user.getRole().getName().toLowerCase() + "/Statistics";
    }

    @RequestMapping(value = "/getComplaintsReport")
    @ResponseBody
    public List<ReportData> getComplaintReport(@RequestParam(name = "region") int region,
                                               @RequestParam(name = "beginDate") String beginDate,
                                               @RequestParam(name = "endDate") String endDate) {
        boolean valid = validateData(beginDate, endDate);
        if (valid) {
            try {
                return reportsService.getComplaintsReportData(beginDate, endDate, region);
            } catch (ReportCreatingException e) {
                logger.error("Can't get complaints data for web graph", e);
                return null;
            }
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/getOrdersReport")
    @ResponseBody
    public List<ReportData> getOrderReport(@RequestParam(name = "region") int region,
                                           @RequestParam(name = "beginDate") String beginDate,
                                           @RequestParam(name = "endDate") String endDate) {
        boolean valid = validateData(beginDate, endDate);
        if (valid) {
            try {
                return reportsService.getOrdersReportData(beginDate, endDate, region);
            } catch (ReportCreatingException e) {
                logger.error("Can't get orders data for web graph", e);
                return null;
            }
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/downloadOrdersReport", method = RequestMethod.GET)
    public void downloadOrdersExcelReport(HttpServletResponse response, @RequestParam(name = "region") int regionId,
                                          @RequestParam(name = "beginDate") String beginDate,
                                          @RequestParam(name = "endDate") String endDate) throws IOException {
        logger.debug("Preparing to make excel complaints report for begin date {}, end date {], place id {}", beginDate, endDate, regionId);
        final String fileName = String.format(ORDERS_REP_FILE_NAME, beginDate, endDate);
        ExcelReportCreator reportMaker = new ExcelReportCreator(fileName);
        try {
            List<ReportData> reportData = reportsService.getOrdersReportData(beginDate, endDate, regionId);
            reportMaker.makeOrdersReport(reportData, ReportType.OrdersStatistics);
        } catch (ReportCreatingException e) {
            logger.error("Error while getting orders data for excel report {}", e);
        } catch (WorkbookCreatingFailException e) {
            logger.error("Error while creating order report in Excel {}", e);
        }
        downloadReport(response, fileName, reportMaker.getExcelWorkbook());
    }

    @RequestMapping(value = "/downloadComplaintsReport", method = RequestMethod.GET)
    public void downloadComplaintsExcelReport(HttpServletResponse response, @RequestParam(name = "region") int regionId,
                                              @RequestParam(name = "beginDate") String beginDate,
                                              @RequestParam(name = "endDate") String endDate) throws IOException {
        logger.debug("Preparing to make excel complaints report for begin date {}, end date {], place id {}", beginDate, endDate, regionId);
        final String fileName = String.format(COMPLAINTS_REP_FILE_NAME, beginDate, endDate);
        ExcelReportCreator reportMaker = new ExcelReportCreator(fileName);
        try {
            List<ReportData> reportData = reportsService.getComplaintsReportData(beginDate, endDate, regionId);
            reportMaker.makeOrdersReport(reportData, ReportType.ComplaintsStatistics);
        } catch (ReportCreatingException e) {
            logger.error("Error while getting complaints data for excel report {}", e);
            return;
        } catch (WorkbookCreatingFailException e) {
            logger.error("Error while creating complaints report in Excel {}", e);
            return;
        }
        downloadReport(response, fileName, reportMaker.getExcelWorkbook());
    }

    /**
     * @param response
     * @param fileName
     * @param excelWorkBookWithReport
     * @throws IOException
     */
    private void downloadReport(HttpServletResponse response, String fileName, Workbook excelWorkBookWithReport) throws IOException {
        OutputStream responseOutputStream = response.getOutputStream();
        response.setContentType(EXCEL_CONTENT_TYPE);
        response.setHeader
                (HEADER_VAR1, HEADER_VAR2 + fileName);
        excelWorkBookWithReport.write(responseOutputStream);
        responseOutputStream.close();
    }
}
