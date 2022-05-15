package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    Product updateProduct(Product product, Long id);

    List<Product> updateProducts(List<Product> products);

    void removeProducts(List<Long> productsIds);

    ByteArrayInputStream loadToExcel(List<Long> productsIds);

    List<Product> saveAllFromExcel(MultipartFile multipartFile);


}
