package jtelecom.controller;

import jtelecom.dao.place.Place;
import jtelecom.dao.place.PlaceDAO;
import jtelecom.dao.user.User;
import jtelecom.dao.user.UserDAO;
import jtelecom.reports.ReportCreatingException;
import jtelecom.reports.ReportData;
import jtelecom.reports.ReportsService;
import jtelecom.reports.excel.ExcelReportCreator;
import jtelecom.reports.excel.WorkbookCreatingFailException;
import jtelecom.security.SecurityAuthenticationHelper;
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
    private static final String EXTENSION = ".xlsx";
    private static final String XLSX_CONTENT_TYPE = "application/vnd.ms-excel";
    private static final String HEADER_VAR1 = "Content-Disposition";
    private static final String HEADER_VAR2 = "attachment; filename=";

    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @RequestMapping(value = "/statistics")
    public String graph(Model model) {
        User user = userDAO.findByEmail(securityAuthenticationHelper.getCurrentUser().getUsername());
        List<Place> regions = placeDAO.getAll();
        model.addAttribute("regions", regions);
        return "newPages/" + user.getRole().getName().toLowerCase() + "/Statistics";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public List<ReportData> getGraphData(@RequestParam(name = "region") int region,
                                         @RequestParam(name = "beginDate") String beginDate,
                                         @RequestParam(name = "endDate") String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        try {
            start.setTime(simpleDateFormat.parse(beginDate));
            end.setTime(simpleDateFormat.parse(endDate));
        } catch (ParseException e) {
            logger.error("Wrong date format", e);
            return null;
        }
        if (start.after(end)) {
            logger.error("Start date {} goes after end date {}", beginDate, endDate);
            return null;
        }
        try {
             return reportsService.getDataForReport(beginDate, endDate, region);
        } catch (ReportCreatingException e) {
            logger.error("Can't get report data for web graph", e);
            return null;
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadExcelDocument(HttpServletResponse response, @RequestParam(name = "region") int region,
                                      @RequestParam(name = "beginDate") String beginDate,
                                      @RequestParam(name = "endDate") String endDate) throws IOException {
        final String fileName = beginDate + ":" + endDate + EXTENSION;
        OutputStream outputStream = response.getOutputStream();
        ExcelReportCreator reportMaker = new ExcelReportCreator(fileName);
        response.setContentType(XLSX_CONTENT_TYPE);
        response.setHeader
                (HEADER_VAR1, HEADER_VAR2 + fileName);
        try {
            reportMaker.makeReport(reportsService.getDataForReport(beginDate, endDate, region));
        } catch (WorkbookCreatingFailException | ReportCreatingException e) {
            logger.error("Error while downloading document", e);
            return;
        }
        reportMaker.getExcelWorkbook().write(outputStream);
        outputStream.close();
    }
}
