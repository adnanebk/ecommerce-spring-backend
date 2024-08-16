package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ProductPageDto;
import com.adnanbk.ecommerce.enums.Operation;
import com.adnanbk.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Product addProduct(Product product);

    Product updateProduct(Product product, Long id);

    List<Product> updateProducts(List<Product> products);

    void removeProducts(List<Long> productsIds);

    Page<Product> getAll(ProductPageDto productPage, Pageable pageable);

    ByteArrayInputStream convertToExcel(List<Long> productsIds);

    Map<Operation, List<Product>> addOrUpdateFromExcel(MultipartFile multipartFile);


    Product getBySku(String sku);

    void removeProduct(Long id);
}
