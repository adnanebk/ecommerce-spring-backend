package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.CustomFileException;
import com.adnanbk.ecommerce.mappers.ProductMapper;
import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.reposetories.ProductRepository;
import com.adnanbk.ecommerce.services.ExcelHelperService;
import com.adnanbk.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepo;
    private final ExcelHelperService<Product> excelHelper;
    private final ProductMapper productMapper;


    @Override
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepo.findById(product.getId()).map(prod->{
             productMapper.mapProduct(product, prod);
             return productRepo.save(product);
         }).orElseThrow();

    }

    @Override
    @Transactional
    public List<Product> updateProducts(List<Product> products) {
        var productsInDb = productRepo.findAllById(products.stream()
                .map(Product::getId).toList());
        productMapper.mapProducts(products, productsInDb);
        return productRepo.saveAll(productsInDb);
    }


    @Override
    public void removeProducts(List<Long> productsIds) {
        productRepo.deleteInBatch(productRepo.findAllById(productsIds));
    }

    public List<Product> saveAllFromExcel(MultipartFile multipartFile) {
        try {
            List<Product> products = excelHelper.excelToList(multipartFile.getInputStream());
            if (!products.isEmpty())
                return productRepo.saveAll(products);

        } catch (IOException e) {
            throw new CustomFileException("We can't process the file,please try again");
        }
        throw new CustomFileException("there are no product to process");

    }

    public ByteArrayInputStream loadToExcel(List<Product> products) {
        if (products != null && !products.isEmpty())
            return excelHelper.listToExcel(products);
        return excelHelper.listToExcel(productRepo.findAll());
    }


}
