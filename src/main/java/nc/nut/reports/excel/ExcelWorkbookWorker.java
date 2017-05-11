package nc.nut.reports.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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


    public ExcelWorkbookWorker(String workbookName) {
        this.workbookName = workbookName;
    }


    public XSSFWorkbook getExcelWorkbook() {
        return excelWorkbook;
    }

    /**
     * method which create workbook for report from existing template
     */
    public void createWorkbookFromTemplate() throws WorkbookCreatingFailException {
        final String TEMP_FILE_ADDRESS = File.separator + "excel" + File.separator + "template.xlsx";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(TEMP_FILE_ADDRESS);
        try {
            excelWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            logger.error("Error while creating Workbook from template ", e);
            throw new WorkbookCreatingFailException("Excel while creating workbook from template  " + workbookName);
        }
    }

}
