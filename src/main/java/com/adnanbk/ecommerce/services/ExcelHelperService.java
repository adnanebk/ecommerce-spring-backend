package com.adnanbk.ecommerce.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface ExcelHelperService<T> {
      String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    List<T> excelToList(MultipartFile file);

    ByteArrayInputStream listToExcel(List<T> list);

      static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    static   Sheet createSheet(String sheetName,Workbook workbook,String[] headers) {
            Sheet sheet = workbook.createSheet(sheetName);
            Row headerRow = sheet.createRow(0);
            for (int colIdx = 0; colIdx < headers.length; colIdx++) {
                Cell cell = headerRow.createCell(colIdx);
                cell.setCellValue(headers[colIdx]);
            }
         return sheet;
    }
    static boolean hasAnyCell(Row currentRow) {
        return currentRow.getPhysicalNumberOfCells() > 0;
    }

     static Optional<Cell> getCell(int colIndex, Row currentRow) {
        return Optional.ofNullable(currentRow.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
    }
    static void skipHeader(Iterator<Row> rows) {
        if (rows.hasNext())
            rows.next();
    }




}
