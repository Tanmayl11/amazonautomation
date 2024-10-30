package amazon;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DP {
    private static final String EXCEL_PATH = "C:\\Users\\tladk\\Amazon\\src\\test\\resources\\amazon.xlsx";
    
    /**
     * Provides test data from Excel sheet matching the test method name
     * @param method Test method requiring data
     * @return Two-dimensional array of test data
     * @throws IOException If Excel file cannot be read
     */
    @DataProvider(name = "data-provider")
    public Object[][] provideTestData(Method method) throws IOException {
        try (FileInputStream excelFile = new FileInputStream(new File(EXCEL_PATH));
             Workbook workbook = new XSSFWorkbook(excelFile)) {
            
            Sheet sheet = workbook.getSheet(method.getName());
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found for method: " + method.getName());
            }
            
            return extractDataFromSheet(sheet);
        }
    }
    
    /**
     * Extracts data from Excel sheet starting from row 1 and column 1
     * @param sheet Excel sheet containing test data
     * @return Two-dimensional array of test data
     */
    private Object[][] extractDataFromSheet(Sheet sheet) {
        List<List<String>> data = new ArrayList<>();
        
        // Skip header row (row 0)
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) continue;
            
            List<String> rowData = extractRowData(row);
            if (!rowData.isEmpty()) {
                data.add(rowData);
            }
        }
        
        return convertToObjectArray(data);
    }
    
    /**
     * Extracts data from a single row starting from column 1
     * @param row Excel row containing test data
     * @return List of cell values from the row
     */
    private List<String> extractRowData(Row row) {
        List<String> rowData = new ArrayList<>();
        
        // Skip first column (column 0)
        for (int colNum = 1; colNum < row.getLastCellNum(); colNum++) {
            Cell cell = row.getCell(colNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                rowData.add(getCellValueAsString(cell));
            }
        }
        
        return rowData;
    }
    
    /**
     * Converts cell value to String regardless of cell type
     * @param cell Excel cell
     * @return String value of the cell
     */
    private String getCellValueAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }
    
    /**
     * Converts List of Lists to two-dimensional Object array
     * @param data List of Lists containing test data
     * @return Two-dimensional Object array
     */
    private Object[][] convertToObjectArray(List<List<String>> data) {
        return data.stream()
                .map(list -> list.toArray(new Object[0]))
                .toArray(Object[][]::new);
    }
}