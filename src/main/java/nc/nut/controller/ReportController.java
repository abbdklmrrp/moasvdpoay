package nc.nut.controller;

import nc.nut.reports.ReportCreatingException;
import nc.nut.reports.ReportsService;
import nc.nut.reports.excel.DocumentCreatingFailException;
import nc.nut.reports.excel.ExcelReportCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Yuliya Pedash on 20.04.2017.
 */
@Controller
public class ReportController {
    @Resource
    private ReportsService reportsService;

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String showReport() {
        return "/report";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadExcelDocument(HttpServletResponse response, Model model) throws IOException {
        //todo generate name from users's response
        //todo get data from server
        final String fileName = "sample.xlsx";
        OutputStream outputStream = response.getOutputStream();
        ExcelReportCreator reportMaker = new ExcelReportCreator(fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader
                ("Content-Disposition", "attachment; filename=" + fileName);
        try {
            reportMaker.makeReport(reportsService.getDataForReport("01-01-2017", "01-06-2017", 2));
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

