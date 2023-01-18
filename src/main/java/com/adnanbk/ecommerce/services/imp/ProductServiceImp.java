package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ProductPageDto;
import com.adnanbk.ecommerce.exceptions.CustomFileException;
import com.adnanbk.ecommerce.exceptions.ProductNotFoundException;
import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.reposetories.ProductRepository;
import com.adnanbk.ecommerce.services.ExcelHelperService;
import com.adnanbk.ecommerce.services.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "uploads")
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepo;
    private final ExcelHelperService<Product> excelHelper;

    @Getter
    @Setter
    private  String imagesPathUrl;
    @Getter
    @Setter
    private  String externalImagesPathUrl;


    @Override
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Product product,Long id) {
             return   productRepo.findById(id).map(pr->mapProperties(pr,product))
                     .map(productRepo::save).orElseThrow();
    }



    @Override
    public List<Product> updateProducts(List<Product> products) {
       List<Product> productList = productRepo.findAllById(products.stream().map(Product::getId).toList())
                .parallelStream().map(product -> mapProperties(product,
                                   products.stream().filter(pr->pr.getId().equals(product.getId())).findFirst().orElseThrow()))
               .toList();
     return   productRepo.saveAll(productList);

    }


    @Override
    public void removeProducts(List<Long> productsIds) {
        productRepo.deleteAllById(productsIds);
    }

    public List<Product> addOrUpdateFromExcel(MultipartFile multipartFile) {
        try {
            List<Product> updatableProducts = new ArrayList<>();
            List<Product> addableProducts = new ArrayList<>();
             Optional.ofNullable(excelHelper.excelToList(multipartFile.getInputStream()))
                    .ifPresent(productsList->productsList.forEach(pr->{
                        if(pr.getId()==null || pr.getId()==0)
                          addableProducts.add(pr);
                        else
                          updatableProducts.add(pr);
                    }));

            List<Product> savedProducts =productRepo.saveAll(addableProducts);
                          savedProducts.addAll(updateProducts(updatableProducts));
               return  savedProducts;

        } catch (IOException e) {
            throw new CustomFileException("We can't process the file,please try again");
        }

    }

    @Override
    public Product getBySku(String sku) {
        return productRepo.findBySku(sku)
                  .orElseThrow(()->new ProductNotFoundException("product not found with sku"+sku));
    }

    @Override
    public Page<Product> getAll(ProductPageDto productPage) {
        return productRepo.findPagedProducts
                                (productPage.getCategory(),productPage.getSearch()
                                        ,productPage.getPageable());
    }

    @Override
    public ByteArrayInputStream convertToExcel(List<Long> productIds) {
         return   Optional.ofNullable(productIds)
                   .map(productRepo::findAllById)
                   .map(excelHelper::listToExcel)
                   .orElseThrow();
    }

private  Product mapProperties(Product target, Product src) {
    target.setSku(src.getSku());
    target.setUnitPrice(src.getUnitPrice());
    target.setName(src.getName());
    target.setCategory(src.getCategory());
    target.setUnitsInStock(src.getUnitsInStock());
    target.setDescription(src.getDescription());
    target.setActive(src.isActive());
    if(StringUtils.hasLength(src.getImage()))
      target.setImage(src.getImage());
    return target;
}
}
