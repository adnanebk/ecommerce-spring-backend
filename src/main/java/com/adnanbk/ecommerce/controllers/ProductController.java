package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.services.ImageService;
import com.adnanbk.ecommerce.services.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {

    private final ImageService imageService;
    private final ProductService productService;


    @PostMapping(value = "/products/images", consumes = "multipart/form-data")
    @ApiOperation(value = "Create product image", notes = "this endpoint uploads an image", response = String.class, consumes = "multipart/form-data")
    public CompletableFuture<ResponseEntity<String>> uploadProductImage(@RequestParam("image") MultipartFile file) {
        return this.imageService.createImage(file)
                .thenApplyAsync((fileName -> ResponseEntity.created(URI.create(fileName)).body(fileName)));

    }

    @GetMapping("/products/images/{filename:.+}")
    @ApiOperation(value = "get product image")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = imageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/products/v2")
    @ApiOperation(value = "Add new product", notes = "This endpoint creates a product and bind its category based on category name ",
            response = Product.class)
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product prod = productService.addProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(prod.getId()).toUri();

        return ResponseEntity.created(location).body(prod);
    }

    @PutMapping("/products/v2")
    @ApiOperation(value = "update product", notes = "This endpoint updates a product and bind its category based on category name"
            , response = Product.class)
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product);
        if (updatedProduct == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Products not found");
        return ResponseEntity.ok(updatedProduct);
    }


    @PutMapping("/products/list")
    @ApiOperation(value = "update products", notes = "This endpoint updates  products and bind their categories by using bulk update ")
    public ResponseEntity<List<Product>> updateProducts(@Valid @RequestBody List<Product> products) {
        List<Product> updatedProducts = productService.updateProducts(products);
        if (updatedProducts.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Products not found");

        return ResponseEntity.ok(updatedProducts);
    }

    @DeleteMapping("/products/v2")
    @ApiOperation(value = "remove list of products")
    public ResponseEntity<String> removeProducts(@RequestParam("Ids") List<Long> listOfIds) {
        if (!listOfIds.isEmpty())
            productService.removeProducts(listOfIds);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products/excel")
    @ApiOperation(value = "add products from excel file", notes = "you have to download an excel file and fill it")
    public Callable<ResponseEntity<List<Product>>> addProductsFromExcel(MultipartFile file) {
        List<Product> products = productService.saveAllFromExcel(file);
        return () -> new ResponseEntity<>(products, HttpStatus.CREATED);
    }

    @GetMapping("/products/excel")
    @ApiOperation(value = "download excel file of products")
    public Callable<ResponseEntity<InputStreamResource>>
    loadProducts(@RequestParam(required = false) List<Long> listOfIds) {
        return () -> {
            String filename = "products-" + LocalDate.now() + ".xlsx";
            InputStreamResource file = new InputStreamResource(productService.loadToExcel(listOfIds));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);
        };
    }

}
