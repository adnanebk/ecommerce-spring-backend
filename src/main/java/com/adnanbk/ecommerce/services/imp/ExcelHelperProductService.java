package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.CustomFileException;
import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.reposetories.ProductCategoryRepository;
import com.adnanbk.ecommerce.services.ExcelHelperService;
import org.apache.poi.ss.usermodel.Cell;
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
import java.math.BigDecimal;
import java.util.*;


@Component
public class ExcelHelperProductService implements ExcelHelperService<Product> {
    static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static final String[] HEADERS = {"Id","Name", "Description", "Sku", "Price", "Quantity",
            "Category"};

    static final String SHEET = "Products";
    public static final String DEFAULT_IMAGE = "newProduct.jpg";


    private final ProductCategoryRepository categoryRepo;

    public ExcelHelperProductService(ProductCategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    @Override
    public List<Product> excelToList(InputStream is) {
        List<Product> products = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            skipHeader(rows);
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (!hasAnyCell(currentRow))
                    continue;
                Product product = extractProductFromRow(currentRow);
                    products.add(product);
            }

            return products;
        } catch (IOException e) {
            throw new CustomFileException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private boolean hasAnyCell(Row currentRow) {
        return currentRow.getPhysicalNumberOfCells() > 0;
    }

    private Product extractProductFromRow(Row currentRow) {
        Product product=new Product();
        getCell(0,currentRow).map(Cell::getNumericCellValue).map(Double::longValue)
                    .ifPresent(product::setId);
        getCell(1,currentRow).map(Cell::getStringCellValue)
                    .ifPresent(product::setName);
        getCell(2,currentRow).map(Cell::getStringCellValue)
                    .ifPresent(product::setDescription);
        getCell(3,currentRow).map(Cell::getStringCellValue)
                    .ifPresent(product::setSku);
        getCell(4,currentRow).map(Cell::getNumericCellValue).map(BigDecimal::valueOf)
                    .ifPresent(product::setUnitPrice);
        getCell(5,currentRow).map(Cell::getNumericCellValue).map(Double::intValue)
                    .ifPresent(product::setUnitsInStock);
        getCell(6,currentRow).map(Cell::getStringCellValue)
                    .ifPresent(cat->product.setCategory(categoryRepo.findByNameIgnoreCase(cat)));
        if(product.getId()==null)
            product.setImage(DEFAULT_IMAGE);
        return product;
    }

    private Optional<Cell> getCell(int colIdx, Row currentRow) {
        return Optional.ofNullable(currentRow.getCell(colIdx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
    }


    public ByteArrayInputStream listToExcel(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            createHeaders(sheet);
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

    private  void createHeaders(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        for (int colIdx = 0; colIdx < HEADERS.length; colIdx++) {
            Cell cell = headerRow.createCell(colIdx);
            cell.setCellValue(HEADERS[colIdx]);
        }
    }

    private void fillRowWithProduct( Row row, Product product) {
        row.createCell(0).setCellValue(product.getId());
        row.createCell(1).setCellValue(product.getName());
        row.createCell(2).setCellValue(product.getDescription());
        row.createCell(3).setCellValue(product.getSku());
        row.createCell(4).setCellValue(product.getUnitPrice().doubleValue());
        row.createCell(5).setCellValue(product.getUnitsInStock());
        row.createCell(6).setCellValue(product.getCategory().getName());
    }

    private void skipHeader(Iterator<Row> rows) {
        if (rows.hasNext())
            rows.next();
    }


}
