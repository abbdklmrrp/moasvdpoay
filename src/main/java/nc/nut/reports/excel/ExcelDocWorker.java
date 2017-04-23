package nc.nut.reports.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * ExcelDocWorker class implements methods for working with report documents
 * Created by Yuliya Pedash on 16.04.2017.
 */
public class ExcelDocWorker {
    private XSSFWorkbook excelWorkbook;
    private File excelReportFile;
    private Logger logger = LoggerFactory.getLogger(getClass());


    public ExcelDocWorker(String fileName) {
        this.excelReportFile = new File(fileName);
        createDocumentFromTemplate();
    }

    public File getExcelReportFile() {
        return excelReportFile;
    }

    public XSSFWorkbook getExcelWorkbook() {
        return excelWorkbook;
    }

    /**
     * method which create excel document for report from existing template
     */
    public void createDocumentFromTemplate() {
        final String TEMP_FILE_ADDRESS = File.separator + "excel" + File.separator + "template.xlsx";
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(TEMP_FILE_ADDRESS);
             FileOutputStream outputStream = new FileOutputStream(excelReportFile)) {
            XSSFWorkbook templExcelWorkbook = new XSSFWorkbook(inputStream);
            templExcelWorkbook.write(outputStream);
            FileInputStream fileInputStream = new FileInputStream(excelReportFile);
            excelWorkbook = new XSSFWorkbook(fileInputStream);
        } catch (FileNotFoundException e) {
            logger.error("File not found while creating report: " + e);
        } catch (IOException e) {
            logger.error("Error while creating report file for excel report" + e);
        }
    }

}
