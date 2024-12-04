package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.CustomFileException;
import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.reposetories.ProductCategoryRepository;
import com.adnanbk.ecommerce.services.ExcelHelperService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.adnanbk.ecommerce.services.ExcelHelperService.*;

@Component
public class ExcelHelperProductService implements ExcelHelperService<Product> {
    static final String[] HEADERS = {"Sku", "Name", "Description", "Price", "Quantity", "Category"};
    static final String SHEET_NAME = "Products";
    static final int SKU_CELL = 0;
    static final int NAME_CELL = 1;
    static final int DESCRIPTION_CELL = 2;
    static final int UNIT_PRICE_CELL = 3;
    static final int UNITS_IN_STOCK_CELL = 4;
    static final int CATEGORY_CELL = 5;

    private final ProductCategoryRepository categoryRepo;

    public ExcelHelperProductService(ProductCategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Product> excelToList(MultipartFile file) {
        if (!hasExcelFormat(file))
            throw new CustomFileException("Only excel formats are valid");
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            return StreamSupport.stream(sheet.spliterator(), false)
                    .filter(this::hasAnyCell)
                    .skip(1)
                    .map(this::extractProductFromRow)
                    .toList();
        } catch (IOException e) {
            throw new CustomFileException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private Product extractProductFromRow(Row currentRow) {
        return Product.builder()
                .name(getCell(NAME_CELL, currentRow).map(this::getValueAsStringOrThrow).orElse(null))
                .description(getCell(DESCRIPTION_CELL, currentRow).map(this::getValueAsStringOrThrow).orElse(null))
                .sku(getCell(SKU_CELL, currentRow).map(this::getValueAsStringOrThrow).orElse(null))
                .unitPrice(getCell(UNIT_PRICE_CELL, currentRow).map(this::getValueAsNumberOrThrow).orElse(null))
                .unitsInStock(getCell(UNITS_IN_STOCK_CELL, currentRow).map(this::getValueAsNumberOrThrow).map(Double::intValue).orElse(null))
                .category(getCell(CATEGORY_CELL, currentRow).map(this::getValueAsStringOrThrow).map(categoryRepo::findByNameIgnoreCase).orElse(null))
                .build();
    }

    public ByteArrayInputStream listToExcel(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = createSheet(SHEET_NAME, workbook, HEADERS);
            int rowIdx = 1;
            for (Product product : products) {
                fillRowWithProduct(sheet.createRow(rowIdx++), product);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new ValidationException("fail to import data to Excel file: ");
        }
    }


    private void fillRowWithProduct(Row row, Product product) {
        row.createCell(NAME_CELL).setCellValue(product.getName());
        row.createCell(DESCRIPTION_CELL).setCellValue(product.getDescription());
        row.createCell(SKU_CELL).setCellValue(product.getSku());
        row.createCell(UNIT_PRICE_CELL).setCellValue(product.getUnitPrice().doubleValue());
        row.createCell(UNITS_IN_STOCK_CELL).setCellValue(product.getUnitsInStock());
        row.createCell(CATEGORY_CELL).setCellValue(product.getCategory().getName());
    }

}