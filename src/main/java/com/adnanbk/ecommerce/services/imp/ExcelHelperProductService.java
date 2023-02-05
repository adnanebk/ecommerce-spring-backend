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
import java.util.concurrent.CompletableFuture;
import static com.adnanbk.ecommerce.services.ExcelHelperService.*;

@Component
public class ExcelHelperProductService implements ExcelHelperService<Product> {
    static final String[] HEADERS = {"Id","Name", "Description", "Sku", "Price", "Quantity",
            "Category"};

    static final String SHEET_NAME = "Products";
    public static final String DEFAULT_IMAGE = "newProduct.jpg";


    private final ProductCategoryRepository categoryRepo;

    public ExcelHelperProductService(ProductCategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }




    @Override
    public List<Product> excelToList(MultipartFile file) {
        if(!hasExcelFormat(file))
            throw new CustomFileException("Only excel formats are valid");
        try(InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is)
        ) {
            List<Product> products = new ArrayList<>();
            Iterator<Row> rows = workbook.getSheet(SHEET_NAME).iterator();
            skipHeader(rows);
            rows.forEachRemaining(currentRow->{
                if (hasAnyCell(currentRow))
                    CompletableFuture.runAsync(()->products.add(extractProductFromRow(currentRow)));
            });
            return products;
        } catch (IOException e) {
            throw new CustomFileException("fail to parse Excel file: " + e.getMessage());
        }
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
                    .map(categoryRepo::findByNameIgnoreCase)
                    .ifPresent(product::setCategory);
        if(product.getId()==null)
            product.setImage(DEFAULT_IMAGE);
        return product;
    }


    public ByteArrayInputStream listToExcel(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
              Sheet sheet = createSheet(SHEET_NAME,workbook,HEADERS);
            int rowIdx = 1;
            for(Product product:products){
                fillRowWithProduct(sheet.createRow(rowIdx++), product);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new ValidationException("fail to import data to Excel file: ");
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

    private Optional<Cell> getCell(int colIndex, Row currentRow) {
        return Optional.ofNullable(currentRow.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
    }

    private boolean hasAnyCell(Row currentRow) {
        return currentRow.getPhysicalNumberOfCells() > 0;
    }


}
