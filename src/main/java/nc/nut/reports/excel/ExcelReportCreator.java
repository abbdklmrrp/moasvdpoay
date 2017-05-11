package nc.nut.reports.excel;

import nc.nut.reports.ReportData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * Creates reports in form of Excel document. Created reports consist
 * of table with three columns(date, complaints count, orders count)
 * and a graph.
 *
 * @see ExcelReportCreator for more details on writing reports to Excel sheet.
 * Created by Yuliya Pedash on 16.04.2017.
 */

public class ExcelReportCreator {

    private XSSFWorkbook excelWorkbook;
    private ExcelWorkbookWorker xlsDocWorker;

    public ExcelReportCreator(String fileName) {
        this.xlsDocWorker = new ExcelWorkbookWorker(fileName);

    }

    public XSSFWorkbook getExcelWorkbook() {
        return excelWorkbook;
    }

    public ExcelWorkbookWorker getXlsDocWorker() {
        return xlsDocWorker;
    }

    /**
     * Method which writes report to created document.
     * Report is written to excel sheet with name "report", which was copied from template.
     *
     * @param data report data
     */
    public void makeReport(List<ReportData> data) throws DocumentCreatingFailException {
        xlsDocWorker.createWorkbookFromTemplate();
        excelWorkbook = xlsDocWorker.getExcelWorkbook();
        String sheetName = "report";
        XSSFSheet excelSheet;
        excelSheet = excelWorkbook.getSheet(sheetName);
        ExcelReportDataWriter.writeReportData(excelSheet, data);
    }


}


