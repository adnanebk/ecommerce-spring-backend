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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
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


    private String getRootUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath(null).toUriString();
    }
    private String getRootUrl(HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromRequest(request).replacePath(null).toUriString();
    }
    @PostMapping(value = "/images", consumes = "multipart/form-data")
    @ApiOperation(value = "Create product image", notes = "this endpoint uploads an image and return the created image url", response = String.class)
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> uploadProductImage(@RequestPart("image") MultipartFile file,HttpServletRequest request) {
        return this.imageService.upload(file).thenApplyAsync(path->getRootUrl(request)+"/"+path);
    }

    @GetMapping("/images/{imageName:.+}")
    @ApiOperation(value = "get product image url")
    public String getImageUrl(@PathVariable String imageName) {
        return getRootUrl() + "/" + imageService.load(imageName);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new product", notes = "This endpoint creates a product and bind its category based on category name ",
            response = Product.class)
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product prod = productService.addProduct(product);
        URI location = URI.create(getRootUrl() + "/products/" + prod.getId());

        return ResponseEntity.created(location).body(prod);
    }

    @PutMapping
    @ApiOperation(value = "update product", notes = "This endpoint updates a product and bind its category based on category name"
            , response = Product.class)
    public Product updateProduct(@Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product);
        if (updatedProduct == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Products not found");
        return updatedProduct;
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

    @GetMapping("/products/excel")
    @ApiOperation(value = "download excel file of products")
    public Callable<ResponseEntity<InputStreamResource>>
    loadProducts(@RequestParam(value = "Ids", required = false) List<Long> listOfIds) {
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
