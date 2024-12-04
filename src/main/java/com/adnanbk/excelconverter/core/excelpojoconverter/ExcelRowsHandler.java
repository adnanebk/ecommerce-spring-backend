package com.adnanbk.excelconverter.core.excelpojoconverter;

import com.adnanbk.excelconverter.core.reflection.ReflectedField;
import com.adnanbk.excelconverter.core.reflection.ReflectionHelper;
import com.adnanbk.excelconverter.exceptions.SheetValidationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.time.DateTimeException;
import java.time.ZoneId;

public class ExcelRowsHandler<T> {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final ReflectionHelper<T> reflectionHelper;
    private final DataFormatter dataFormat = new DataFormatter();


    public ExcelRowsHandler(ReflectionHelper<T> reflectionHelper) {
        this.reflectionHelper = reflectionHelper;
    }

    public void fillRowFromObject(Row row, T obj) {
        var fields = reflectionHelper.getFields();
        for (int i = 0; i < fields.size(); i++) {
            var field = fields.get(i);
            var fieldValue = field.getValue(obj);
            if (fieldValue == null)
                return;
            var cell = row.createCell(i);
            if (field.getTypeName().equals("number"))
                cell.setCellValue(Double.parseDouble(fieldValue.toString()));
            else
                cell.setCellValue(fieldValue.toString());
        }
    }

    public T convertToObject(Row row) {
        T obj = reflectionHelper.createInstance();
        for (var field : reflectionHelper.getFields()) {
            try {
                var cell = row.getCell(field.getCellIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if(!cell.getCellType().equals(CellType.BLANK))
                  field.setValue(getCellValue(field, cell), obj);
            } catch (IllegalStateException | NumberFormatException e) {
                throw new SheetValidationException(String.format("Cannot convert the cell value in row %s, column %s to the field %s", row.getRowNum() + 1, ALPHABET.charAt(field.getCellIndex()),field.getName()));
            } catch (DateTimeException e) {
                throw new SheetValidationException(String.format("Invalid or unsupported date pattern in row %s, column %s for the field %s", row.getRowNum() + 1, ALPHABET.charAt(field.getCellIndex()),field.getName()));
            }
        }
        return obj;
    }

    private Object getCellValue(ReflectedField<?> reflectedField,Cell cell) {
        if (cell.getCellType().equals(CellType.STRING))
            return cell.getStringCellValue();
        return switch (reflectedField.getTypeName()) {
            case "number" -> dataFormat.formatCellValue(cell);
            case "localdate" -> cell.getLocalDateTimeCellValue().toLocalDate();
            case "localdatetime" -> cell.getLocalDateTimeCellValue();
            case "zoneddatetime" -> cell.getLocalDateTimeCellValue().atZone(ZoneId.systemDefault());
            case "date" -> cell.getDateCellValue();
            default -> throw new IllegalStateException();
        };
    }

}
