package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.dto.PageDto;
import com.adnanbk.ecommerce.dto.ProductDto;
import com.adnanbk.ecommerce.dto.ProductPageDto;
import com.adnanbk.ecommerce.enums.Operation;
import com.adnanbk.ecommerce.mappers.ProductMapper;
import com.adnanbk.ecommerce.services.FileService;
import com.adnanbk.ecommerce.services.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Validated
public class ProductController {

    private final FileService imageService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    @ApiOperation(value = "Get a page of products")
    public Page<ProductDto> getProducts(ProductPageDto productPageDto) {
        return  productService.getAll(productPageDto,buildPageable(productPageDto))
                .map(productMapper::toDto);
    }

    @GetMapping("/sku/{sku}")
    @ApiOperation(value = "Get a product by sku")
    public ProductDto getBySku(@PathVariable  String sku) {
        return  productMapper.toDto(productService.getBySku(sku));
    }
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new product", notes = "This endpoint creates a product and bind its category based on category name ",
            response = ProductDto.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDto addProduct(@Valid @RequestPart("product") ProductDto productDto, @RequestPart List<MultipartFile> files) {

                return Optional.of(productDto)
                                  .map(productMapper::toEntity)
                        .map(pr->{
                            pr.setImage(this.imageService.upload(files));
                                         return pr;
                        })
                                  .map(productService::addProduct)
                                  .map(productMapper::toDto).orElseThrow();
    }

    @PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "update product", notes = "This endpoint updates a product and bind its category based on category name"
            , response = ProductDto.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDto updateProduct(@Valid @RequestPart("product") ProductDto productDto, @RequestPart(required = false) List<MultipartFile> files,@PathVariable Long id) {
        return Optional.of(productDto)
                        .map(productMapper::toEntity)
            .map(pr->{
                if(files!=null && !files.isEmpty())
                 pr.setImage(this.imageService.upload(files));
                return pr;
            })
                        .map(pr->productService.updateProduct(pr,id))
                        .map(productMapper::toDto).orElseThrow();
    }


    @PutMapping
    @ApiOperation(value = "update products", notes = "This endpoint updates  products and bind their categories by using bulk update ")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ProductDto> updateProducts(@Valid @RequestBody List<ProductDto> products) {
        return productService.updateProducts(
                products.stream().map(productMapper::toEntity).toList()
                ).stream().map(productMapper::toDto).toList();

    }

    @DeleteMapping
    @ApiOperation(value = "remove list of products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeProducts(@RequestParam("Ids") List<Long> listOfIds) {
        if (!listOfIds.isEmpty())
            productService.removeProducts(listOfIds);
    }

    @PostMapping("/excel")
    @ApiOperation(value = "add or update products from excel file", notes = "you can download an excel file and fill it")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Map<Operation,List<ProductDto>>> addOrUpdateProductsFromExcel(@RequestPart  MultipartFile file) {
        return CompletableFuture.supplyAsync(()->{
            Map<Operation,List<ProductDto>> result = new EnumMap<>(Operation.class);
           productService.addOrUpdateFromExcel(file).forEach((op,products)->result.put(op,products.stream().map(productMapper::toDto).toList()));
           return result;
        });

    }

    @GetMapping("/{ids}/excel")
    @ApiOperation(value = "download excel file of products")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<ResponseEntity<InputStreamResource>>
    downloadExcelFromProducts(@PathVariable List<Long> ids) {
    return  CompletableFuture.supplyAsync(()->{
    String filename = "products-" + LocalDate.now() + ".xlsx";
    InputStreamResource file = new InputStreamResource(productService.convertToExcel(ids));
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(file);
});
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "remove a product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
    }
        private Pageable buildPageable(PageDto pageDto) {
        return PageRequest.of(pageDto.getNumber(),pageDto.getSize(),
                Sort.by(Sort.Direction.valueOf(pageDto.getSortDirection()),pageDto.getSortProperty()));

    }
}