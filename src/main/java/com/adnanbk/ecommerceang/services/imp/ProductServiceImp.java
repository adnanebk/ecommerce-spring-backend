package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.Utils.ExcelHelperI;
import com.adnanbk.ecommerceang.exceptions.CustomFileException;
import com.adnanbk.ecommerceang.models.Product;
import com.adnanbk.ecommerceang.reposetories.ProductRepository;
import com.adnanbk.ecommerceang.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepo;
    private final ExcelHelperI<Product> excelHelper;

    @Value("${api.url}")
    private String baseUrl;

    @Override
    public Product addProduct(Product product) {
           mapProductImage(product);
        return productRepo.save(product);
    }

    @Override
    public  Product updateProduct(Product product) {
        Product prod= productRepo.getOne(product.getId());
        mapProduct(product, prod);
        return   productRepo.save(prod);
    }

    @Override
    public List<Product> updateProducts(List<Product> products) {
        var updatedProducts = mapProducts(products);
        return productRepo.saveAll(updatedProducts);
    }


    @Override
    public void removeProducts(List<Long> productsIds) {
        productRepo.deleteInBatch(productRepo.findAllById(productsIds));
    }

    public List<Product> saveAllFromExcel(MultipartFile multipartFile) {
        try {
            List<Product> products = excelHelper.excelToList(multipartFile.getInputStream())
                                    .stream().map(this::mapProductImage).toList();
                 if(products.size()>0)
                return productRepo.saveAll(products);
            throw new CustomFileException("We can't process the file,please try again");

        } catch (IOException e) {
            throw new CustomFileException("We can't process the file,please try again");

        }

    }



    public ByteArrayInputStream loadToExcel(List<Long> Ids) {
        if (Ids != null && !Ids.isEmpty())
            return excelHelper.listToExcel(productRepo.findAllById(Ids));
        return excelHelper.listToExcel(productRepo.findAll());
    }

    private void mapProduct(Product productSrc, Product productDest) {
        productDest.setFromProduct(productSrc);
            mapProductImage(productDest);
    }
    private Product mapProductImage(Product productSrc) {
        if (!productSrc.getImage().startsWith("http") && !productSrc.getImage().startsWith("assets"))
            productSrc.setImage(baseUrl + "/products/images/" + productSrc.getImage());
        return  productSrc;
    }
    private List<Product> mapProducts(List<Product> products) {
        var productsInDb= productRepo.findAllById(products.stream()
                .map(Product::getId).collect(Collectors.toList()));
        for (Product prod : productsInDb) {
            var product = products.stream().filter(p -> p.getId().equals(prod.getId())).findFirst();
            product.ifPresent(value -> mapProduct(value, prod));
        }
         return productsInDb;
    }
}
