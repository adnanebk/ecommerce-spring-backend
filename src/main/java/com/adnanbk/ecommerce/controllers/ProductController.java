package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.services.FileService;
import com.adnanbk.ecommerce.services.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/products/v2")
@AllArgsConstructor
public class ProductController {

    private final FileService imageService;
    private final ProductService productService;



    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new product", notes = "This endpoint creates a product and bind its category based on category name ",
            response = Product.class)
    public CompletableFuture<Product> addProduct(@Valid @RequestPart Product product, @RequestPart MultipartFile file) {
          return this.imageService.upload(file).thenApplyAsync(res->productService.addProduct(product));
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "update product", notes = "This endpoint updates a product and bind its category based on category name"
            , response = Product.class)
    public CompletableFuture<Product> updateProduct(@Valid @RequestPart Product product, @RequestPart(required = false) MultipartFile file) {
          if(file==null || file.isEmpty())
              return CompletableFuture.completedFuture((productService.updateProduct(product)));
           return this.imageService.upload(file).thenApplyAsync(res->productService.updateProduct(product));


    }


    @PutMapping("/list")
    @ApiOperation(value = "update products", notes = "This endpoint updates  products and bind their categories by using bulk update ")
    public List<Product> updateProducts(@Valid @RequestBody List<Product> products) {
        List<Product> updatedProducts = productService.updateProducts(products);
        if (updatedProducts.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Products not found");

        return updatedProducts;
    }

    @DeleteMapping
    @ApiOperation(value = "remove list of products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProducts(@RequestParam("Ids") List<Long> listOfIds) {
        if (!listOfIds.isEmpty())
            productService.removeProducts(listOfIds);
    }

    @PostMapping("/excel")
    @ApiOperation(value = "add products from excel file", notes = "you have to download an excel file and fill it")
    public Callable<List<Product>> addProductsFromExcel(@RequestPart MultipartFile file) {
        return () -> productService.saveAllFromExcel(file);
    }

    @PostMapping("/excel/download")
    @ApiOperation(value = "download excel file of products")
    public Callable<ResponseEntity<InputStreamResource>>
    downloadExcelFromProducts(@RequestBody List<Product> products) {
        return () -> {
            String filename = "products-" + LocalDate.now() + ".xlsx";
            InputStreamResource file = new InputStreamResource(productService.loadToExcel(products));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);
        };
    }

}
