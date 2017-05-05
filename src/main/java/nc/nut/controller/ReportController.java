package nc.nut.controller;

import nc.nut.dao.place.Place;
import nc.nut.dao.place.PlaceDAO;
import nc.nut.reports.ReportCreatingException;
import nc.nut.reports.ReportData;
import nc.nut.reports.ReportsService;
import nc.nut.reports.excel.DocumentCreatingFailException;
import nc.nut.reports.excel.ExcelReportCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping(value = "report")
public class ReportController {
    @Autowired
    private PlaceDAO placeDAO;
    @Autowired
    private ReportsService reportsService;
    private static final String EXTENSION = ".xlsx";
    private static final String XLSX_CONTENT_TYPE = "application/vnd.ms-excel";
    private static final String HEADER_VAR1 = "Content-Disposition";
    private static final String HEADER_VAR2 = "attachment; filename=";

    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @RequestMapping
    public String graph(Model model) {
        List<Place> regions = placeDAO.getAll();
        model.addAttribute("regions", regions);
        return "report";
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
        }
        if (start.after(end)) {
            logger.error("Start date {} goes after end date {}", beginDate, endDate);
            return null;
        }
        List<ReportData> list = null;
        try {
            list = reportsService.getDataForReport(beginDate, endDate, region);
        } catch (ReportCreatingException e) {
            logger.error("Can't get report data for web graph", e);
        }
        return list;
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
        } //todo add error handling
        catch (DocumentCreatingFailException e) {
            return;
            //todo add error handling
        } catch (ReportCreatingException e) {
            return;

        }
        reportMaker.getExcelWorkbook().write(outputStream);
        //todo make without deleting document
        reportMaker.getXlsDocWorker().getExcelReportFile().delete();
        outputStream.close();
    }
}
