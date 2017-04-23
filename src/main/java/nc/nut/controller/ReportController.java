package nc.nut.controller;

import nc.nut.reports.ReportData;
import nc.nut.reports.excel.ExcelReportCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuliya Pedash on 20.04.2017.
 */
@Controller
public class ReportController {
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String showReport() {
        return "/report";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadExcelDocument(HttpServletResponse response) throws IOException {
        final String fileName = "sample.xlsx";
        OutputStream outputStream = response.getOutputStream();
        ExcelReportCreator reportMaker = new ExcelReportCreator(fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader
                ("Content-Disposition", "attachment; filename=" + fileName);
        reportMaker.makeReport(getData());
        reportMaker.getExcelWorkbook().write(outputStream);
        reportMaker.getXlsDocWorker().getExcelReportFile().delete();
        outputStream.close();
    }

    /**
     * gets data for report just for example(temporary method).
     *
     * @return data
     */
    private List<ReportData> getData() {
        List<ReportData> reportDataList = new ArrayList<>();
        reportDataList.add(new ReportData("11-11-2016", 54, 32));
        reportDataList.add(new ReportData("12-11-2016", 23, 11));
        reportDataList.add(new ReportData("13-11-2016", 53, 31));
        reportDataList.add(new ReportData("14-11-2016", 23, 11));
        reportDataList.add(new ReportData("15-11-2016", 23, 11));
        reportDataList.add(new ReportData("16-11-2016", 23, 11));
        reportDataList.add(new ReportData("17-11-2016", 23, 11));
        reportDataList.add(new ReportData("18-11-2016", 23, 11));
        reportDataList.add(new ReportData("19-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("21-11-2016", 23, 11));
        reportDataList.add(new ReportData("22-11-2016", 23, 11));
        reportDataList.add(new ReportData("23-11-2016", 23, 11));
        reportDataList.add(new ReportData("24-11-2016", 23, 11));
        reportDataList.add(new ReportData("25-11-2016", 23, 11));
        reportDataList.add(new ReportData("26-11-2016", 23, 11));
        reportDataList.add(new ReportData("27-11-2016", 23, 11));
        reportDataList.add(new ReportData("28-11-2016", 23, 11));
        reportDataList.add(new ReportData("29-11-2016", 23, 11));
        reportDataList.add(new ReportData("30-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        reportDataList.add(new ReportData("20-11-2016", 23, 11));
        return reportDataList;
    }

}