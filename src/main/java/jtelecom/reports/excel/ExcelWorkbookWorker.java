package jtelecom.reports.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * ExcelWorkbookWorker class implements methods for working with report documents
 * Created by Yuliya Pedash on 16.04.2017.
 */
public class ExcelWorkbookWorker {
    private XSSFWorkbook excelWorkbook;
    private String workbookName;
    private final static Logger logger = LoggerFactory.getLogger(ExcelWorkbookWorker.class);
//    final String ORDERS_TEMPLATE_ADDRESS = File.separator + "excel" + File.separator + "orders_template.xlsx";
//    final String COMPLAINT_TEMPLATE_ADDRESS = File.separator + "excel" + File.separator + "comp_template.xlsx";


    public ExcelWorkbookWorker(String workbookName) {
        this.workbookName = workbookName;
    }


    public XSSFWorkbook getExcelWorkbook() {
        return excelWorkbook;
    }

    /**
     * method which create workbook for report from existing template
     */
    public void createWorkbookFromTemplate(ReportType reportType) throws WorkbookCreatingFailException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(reportType.getTemplateFileAddress());
        try {
            excelWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            logger.error("Error while creating Workbook from template ", e);
            throw new WorkbookCreatingFailException("Excel while creating workbook from template  " + workbookName);
        }
    }

}
