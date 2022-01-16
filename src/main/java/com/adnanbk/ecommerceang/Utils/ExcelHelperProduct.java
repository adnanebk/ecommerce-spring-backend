package com.adnanbk.ecommerceang.Utils;

import com.adnanbk.ecommerceang.exceptions.CustomFileException;
import com.adnanbk.ecommerceang.models.Product;
import com.adnanbk.ecommerceang.reposetories.ProductCategoryRepository;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
public class ExcelHelperProduct implements ExcelHelperI<Product> {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = {"Name", "Description", "Sku", "Price", "Quantity",
            "Category", "Active", "Image url"};

    static String SHEET = "Products";
    private final List<Product> products = new ArrayList<>();
    private ProductCategoryRepository categoryRepo;

    public ExcelHelperProduct(ProductCategoryRepository categoryRepo) {
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

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                currentRow.getFirstCellNum();
                if (currentRow.getPhysicalNumberOfCells() <= 0)
                    continue;
                Product product = new Product();
                boolean isRowExist = false;
                for (int i = 0; i < currentRow.getLastCellNum(); i++) {
                    var currentCell = currentRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (currentCell == null)
                        continue;
                    isRowExist = true;
                    try {
                        switch (i) {


                            case 0 -> product.setName(currentCell.getStringCellValue());
                            case 1 -> product.setDescription(currentCell.getStringCellValue());
                            case 2 -> product.setSku(currentCell.getStringCellValue());
                            case 3 -> product.setUnitPrice(BigDecimal.valueOf(currentCell.getNumericCellValue()));

                            case 4 -> product.setUnitsInStock((int) currentCell.getNumericCellValue());
                            case 5 -> {
                                var category = categoryRepo.findByNameIgnoreCase(currentCell.getStringCellValue());
                                if (category == null)
                                    throw new ValidationException("you must choose correct category");

                                product.setCategory(category);
                            }
                            case 6 -> product.setActive(currentCell.getBooleanCellValue());
                            case 7 -> product.setImage(currentCell.getStringCellValue());
                        }
                    } catch (IllegalStateException ex) {
                        throw new ValidationException("fail to load data from Excel file: , check if you are using valid data with correct orders");

                    }

                }


                if (isRowExist)
                    products.add(product);
            }

            // workbook.close();
            this.products.addAll(products);
            return products;
        } catch (IOException e) {
            throw new CustomFileException("fail to parse Excel file: " + e.getMessage());
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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (Product product : products) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(product.getName());
                row.createCell(1).setCellValue(product.getDescription());
                row.createCell(2).setCellValue(product.getSku());
                row.createCell(3).setCellValue(product.getUnitPrice().doubleValue());
                row.createCell(4).setCellValue(product.getUnitsInStock());
                row.createCell(5).setCellValue(product.getCategory().getName());
                row.createCell(6).setCellValue(product.isActive());
                row.createCell(7).setCellValue(product.getImage());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new ValidationException("fail to import data to Excel file: ");
        }
    }


    @Override
    public List<Product> getList() {
        return products;
    }

}
