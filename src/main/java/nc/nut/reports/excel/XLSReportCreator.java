package nc.nut.reports.excel;

import nc.nut.reports.ReportData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * creates reports in form of Excel document
 *
 * @see XLSReportCreator for more details on writing reports to Excel sheet
 * Created by Yuliya Pedash on 16.04.2017.
 */

public class XLSReportCreator {
    private XSSFWorkbook excelWorkbook;
    private XLSDocWorker xlsDocWorker;

    public XLSReportCreator(String fileName) {
        this.xlsDocWorker = new XLSDocWorker(fileName);
        excelWorkbook = xlsDocWorker.getExcelWorkbook();
    }

    public XSSFWorkbook getExcelWorkbook() {
        return excelWorkbook;
    }

    public void makeReport(List<ReportData> data) {
        String sheetName = "report";
        XSSFSheet excelSheet;
        excelSheet = excelWorkbook.getSheet(sheetName);
        XLSReportDataWriter reportDataWriter = new XLSReportDataWriter(excelSheet, data);
        reportDataWriter.writeReportData();
    }


}
