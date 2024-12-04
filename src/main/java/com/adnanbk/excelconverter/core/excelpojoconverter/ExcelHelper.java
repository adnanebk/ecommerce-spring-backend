package com.adnanbk.excelconverter.core.excelpojoconverter;

import com.adnanbk.excelconverter.core.ColumnDefinition;
import com.adnanbk.excelconverter.core.reflection.ReflectionHelper;
import com.adnanbk.excelconverter.exceptions.SheetValidationException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class ExcelHelper<T> {

    private static final String EXCEL_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ;
    private final ExcelRowsHandler<T> rowsHandler;
    private final String[] headers;

    private ExcelHelper(ExcelRowsHandler<T> rowsHandler, String[] headers) {
        this.rowsHandler = rowsHandler;
        this.headers = headers;
    }

    public static <T> ExcelHelper<T> create(Class<T> type) {
        var reflectionHelper = new ReflectionHelper<>(type);
        var rowsHandler = new ExcelRowsHandler<>(reflectionHelper);
        return new ExcelHelper<>(rowsHandler, reflectionHelper.getHeaders().toArray(String[]::new));
    }

    public static <T> ExcelHelper<T> create(Class<T> type, ColumnDefinition<?>... columnsDefinitions) {
        var headers = Arrays.stream(columnsDefinitions).map(ColumnDefinition::getTitle);
        var reflectionHelper = new ReflectionHelper<>(type, columnsDefinitions);
        var rowsHandler = new ExcelRowsHandler<>(reflectionHelper);
        return new ExcelHelper<>(rowsHandler, headers.toArray(String[]::new));
    }


    public Stream<T> toStream(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            return StreamSupport.stream(sheet.spliterator(), false)
                    .filter(this::hasAnyCell)
                    .skip(1)
                    .map(rowsHandler::convertToObject);
        } catch (Exception ex) {
            throw new SheetValidationException(ex.getMessage());
        }
    }

    public ByteArrayInputStream toExcel(List<T> list) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet();
            createHeaders(sheet, workbook);
            for (int i = 0; i < list.size(); i++) {
                this.rowsHandler.fillRowFromObject(sheet.createRow(i + 1), list.get(i));
            }
            autoSizeColumns(sheet);
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new SheetValidationException("fail to import data to Excel file: ");
        }
    }

    private void createHeaders(Sheet sheet, Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        headerStyle.setFont(font);
        Row headerRow = sheet.createRow(0);
        for (int colIdx = 0; colIdx < headers.length; colIdx++) {
            Cell cell = headerRow.createCell(colIdx);
            cell.setCellValue(headers[colIdx]);
            cell.setCellStyle(headerStyle);
        }
    }

    private boolean hasAnyCell(Row currentRow) {
        return currentRow.getPhysicalNumberOfCells() > 0;
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < sheet.getLastRowNum(); i++)
            sheet.autoSizeColumn(i);
    }

    public Stream<T> toStream(MultipartFile multipartFile) {
        try {
            if(!hasExcelFormat(multipartFile))
                throw new SheetValidationException("Not a valid excel format");
            return toStream(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new SheetValidationException(e.getMessage());
        }
    }
    private static boolean hasExcelFormat(MultipartFile file) {
        return EXCEL_TYPE.equals(file.getContentType());
    }
}