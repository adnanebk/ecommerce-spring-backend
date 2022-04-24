package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ProductService {

    Product updateProduct(Product product);

    Product addProduct(Product product);

    List<Product> updateProducts(List<Product> products);

    void removeProducts(List<Long> productsIds);

    ByteArrayInputStream loadToExcel(List<Product> productsIds);

    List<Product> saveAllFromExcel(MultipartFile multipartFile);


}
