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


    public ExcelDocWorker(String fileName) {
        this.excelReportFile = new File(fileName);
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
    public void createDocumentFromTemplate() throws DocumentCreatingFailException {
        final String TEMP_FILE_ADDRESS = File.separator + "excel" + File.separator + "template.xlsx";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(TEMP_FILE_ADDRESS);
        try (FileOutputStream outputStream = new FileOutputStream(excelReportFile)) {
            XSSFWorkbook templExcelWorkbook = new XSSFWorkbook(inputStream);
            templExcelWorkbook.write(outputStream);
            FileInputStream fileInputStream = new FileInputStream(excelReportFile);
            excelWorkbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            throw new DocumentCreatingFailException("Excel Document with fileName  " + excelReportFile.getName() +
                    "was not created");

        }
    }

}
