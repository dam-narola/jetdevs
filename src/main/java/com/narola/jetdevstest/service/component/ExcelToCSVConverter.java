package com.narola.jetdevstest.service.component;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Iterator;

public class ExcelToCSVConverter {


    public static InputStream convertExcelToCSVInputStream(MultipartFile file) throws IOException {
        // Use ByteArrayOutputStream to store the CSV data in memory
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             InputStream excelInputStream = file.getInputStream();
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(baos))) {
            Workbook workbook;
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(excelInputStream); // For newer Excel files
            } else if (file.getOriginalFilename().endsWith(".xls")) {
                workbook = new HSSFWorkbook(excelInputStream); // For older Excel files
            } else {
                throw new IllegalArgumentException("Invalid file type. Please upload a valid Excel file.");
            }

            // Get the first sheet (you can modify this to choose specific sheets)
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate over all rows
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                StringBuilder sb = new StringBuilder();

                // Iterate over all cells in the row
                for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                    Cell cell = row.getCell(i);

                    // Append the cell value to the StringBuilder
                    sb.append(cell.toString());

                    // Add comma separator except for the last column
                    if (i < row.getPhysicalNumberOfCells() - 1) {
                        sb.append(",");
                    }
                }

                // Write the row data to the CSV OutputStream
                bw.write(sb.toString());
                bw.newLine(); // New line after each row
            }

            // Make sure to flush the output stream before converting to InputStream
            bw.flush();

            // Convert ByteArrayOutputStream to InputStream and return it
            return new ByteArrayInputStream(baos.toByteArray());
        }
    }
}

