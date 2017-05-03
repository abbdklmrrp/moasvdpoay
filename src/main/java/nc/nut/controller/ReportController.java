package nc.nut.controller;

import nc.nut.dao.place.Place;
import nc.nut.dao.place.PlaceDAO;
import nc.nut.reports.ReportCreatingException;
import nc.nut.reports.ReportData;
import nc.nut.reports.ReportsService;
import nc.nut.reports.excel.DocumentCreatingFailException;
import nc.nut.reports.excel.ExcelReportCreator;
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
import java.util.List;
import java.util.stream.Collectors;

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
        List<ReportData> list = null;
        List<ReportData> filteredList = null;
        try {
            list = reportsService.getDataForReport(beginDate, endDate, region);
            filteredList = list.stream()
                    .filter((o) -> (o.getComplaintsCount() > 0 || o.getOrdersCount() > 0))
                    .collect(Collectors.toList());
        } catch (ReportCreatingException e) {
            e.printStackTrace();
        }
        if (!filteredList.isEmpty()) {
            return list;
        } else {
            return filteredList;
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadExcelDocument(HttpServletResponse response, @RequestParam(name = "region") int region,
                                      @RequestParam(name = "beginDate") String beginDate,
                                      @RequestParam(name = "endDate") String endDate) throws IOException {
        final String extension = ".xlsx";
        final String fileName =  beginDate+"to" + endDate + extension;
        OutputStream outputStream = response.getOutputStream();
        ExcelReportCreator reportMaker = new ExcelReportCreator(fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader
                ("Content-Disposition", "attachment; filename=" + fileName);
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
        reportMaker.getXlsDocWorker().getExcelReportFile().delete();
        outputStream.close();
    }
}
