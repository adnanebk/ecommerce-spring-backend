package com.adnanbk.ecommerce.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

public interface ExcelHelperService<T> {
      String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    List<T> excelToList(MultipartFile file);

    ByteArrayInputStream listToExcel(List<T> list);

      static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    static  Sheet createSheet(String sheetName,Workbook workbook,String[] headers) {
            Sheet sheet = workbook.createSheet(sheetName);
            Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        headerStyle.setFont(font);
        createHeaders(headers, headerRow, headerStyle);
        return sheet;
    }

    private static void createHeaders(String[] headers, Row headerRow, CellStyle headerStyle) {
        for (int colIdx = 0; colIdx < headers.length; colIdx++) {
            Cell cell = headerRow.createCell(colIdx);
            cell.setCellValue(headers[colIdx]);
            cell.setCellStyle(headerStyle);
        }
    }

    default boolean hasAnyCell(Row currentRow) {
        return currentRow.getPhysicalNumberOfCells() > 0;
    }

     static Optional<Cell> getCell(int colIndex, Row currentRow) {
        return Optional.ofNullable(currentRow.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
    }

       default Double getValueAsNumberOrThrow(Cell cell){
        if(!cell.getCellType().equals(CellType.NUMERIC))
            throw new ValidationException(String.format("type for the cell at row %s and column %s should be in numeric format",cell.getRowIndex(),ALPHABET.charAt(cell.getColumnIndex())));
        return cell.getNumericCellValue();
    }

    default String getValueAsStringOrThrow(Cell cell){
        if(!cell.getCellType().equals(CellType.STRING))
            throw new ValidationException(String.format("type for the cell at row %s and column %s should be in string format",cell.getRowIndex(),ALPHABET.charAt(cell.getColumnIndex())));
        return cell.getStringCellValue();
    }
}
