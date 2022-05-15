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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
public class ExcelHelperProductService implements ExcelHelperService<Product> {
    static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static final String[] HEADERS = {"Name", "Description", "Sku", "Price", "Quantity",
            "Category"};

    static final String SHEET = "Products";
    public static final String DEFAULT_IMAGE = "https://images.rawpixel.com/image_800/czNmcy1wcml2YXRlL3Jhd3BpeGVsX2ltYWdlcy93ZWJzaXRlX2NvbnRlbnQvbHIvcm0yNTEtbWluZC1pbnN0Z3JhbS0wMy5qcGc.jpg";

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

            // skip header
            skipHeader(rows);
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                currentRow.getFirstCellNum();
                if (currentRow.getPhysicalNumberOfCells() > 0) {
                    Product product = extractProductFromRow(currentRow);
                    if (StringUtils.hasLength(product.getImage()))
                        products.add(product);
                }
            }

            return products;
        } catch (IOException e) {
            throw new CustomFileException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private Product extractProductFromRow(Row currentRow) {
        Product product=new Product();
        for (int i = 0; i < currentRow.getLastCellNum(); i++) {
            var currentCell = currentRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            setProductPropertiesFromCell(currentCell,i, product);
        }
        return product;
    }

    private void setProductPropertiesFromCell(Cell currentCell, int cellIndex, Product product) {
        product.setImage(DEFAULT_IMAGE);
        if (currentCell != null) {
            try {
                switch (cellIndex) {
                    case 0 -> product.setName(currentCell.getStringCellValue());
                    case 1 -> product.setDescription(currentCell.getStringCellValue());
                    case 2 -> product.setSku(currentCell.getStringCellValue());
                    case 3 -> product.setUnitPrice(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                    case 4 -> product.setUnitsInStock((int) currentCell.getNumericCellValue());
                    case 5 -> {
                        var category = categoryRepo.findByNameIgnoreCase(currentCell.getStringCellValue());
                        if (category == null)
                            throw new ValidationException("category not found");
                        product.setCategory(category);
                    }
                }
            } catch (IllegalStateException ex) {
                throw new ValidationException("fail to load data from Excel file: , check if you are using valid data with correct orders");

            }

        }
    }


    public ByteArrayInputStream listToExcel(List<Product> products) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1;
            for (Product product : products) {
                fillRowWithProduct(sheet, rowIdx, product);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new ValidationException("fail to import data to Excel file: ");
        }
    }

    private int fillRowWithProduct(Sheet sheet, int rowIdx, Product product) {
        Row row = sheet.createRow(rowIdx++);
        row.createCell(0).setCellValue(product.getName());
        row.createCell(1).setCellValue(product.getDescription());
        row.createCell(2).setCellValue(product.getSku());
        row.createCell(3).setCellValue(product.getUnitPrice().doubleValue());
        row.createCell(4).setCellValue(product.getUnitsInStock());
        row.createCell(5).setCellValue(product.getCategory().getName());
        return rowIdx;
    }

    private void skipHeader(Iterator<Row> rows) {
        if (rows.hasNext())
            rows.next();
    }


}
