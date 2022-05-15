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
import java.util.Optional;

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
    public Product updateProduct(Product product,Long id) {
        return productRepo.findById(id).map(prod->{
               productMapper.mapProductProperties(product, prod);
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
        productRepo.deleteAllInBatch(productRepo.findAllById(productsIds));
    }

    public List<Product> saveAllFromExcel(MultipartFile multipartFile) {
        try {
            List<Product> products = excelHelper.excelToList(multipartFile.getInputStream());
            return Optional.ofNullable(products)
                    .map(productRepo::saveAll)
                    .orElseThrow();
        } catch (IOException e) {
            throw new CustomFileException("We can't process the file,please try again");
        }

    }

    @Override
    public ByteArrayInputStream loadToExcel(List<Long> productIds) {
         return   Optional.ofNullable(productIds)
                   .map(productRepo::findAllById)
                   .map(excelHelper::listToExcel)
                   .orElseThrow();
    }


}
