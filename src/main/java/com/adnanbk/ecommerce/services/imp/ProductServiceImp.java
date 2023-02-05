package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ProductPageDto;
import com.adnanbk.ecommerce.exceptions.ProductNotFoundException;
import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.reposetories.ProductRepository;
import com.adnanbk.ecommerce.services.ExcelHelperService;
import com.adnanbk.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "uploads")
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepo;
    private final ExcelHelperService<Product> excelHelper;


    @Override
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Product product,Long id) {
             return   productRepo.findById(id).map(pr-> mapPropertiesAndGet(pr,product))
                     .map(productRepo::save).orElseThrow();
    }

    @Override
    public List<Product> updateProducts(List<Product> products) {
       Map<Long,Product> productsMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
       var currentProductsInDb =  productRepo.findAllById(products.stream().map(Product::getId).toList())
               .stream()
               .map(product -> mapPropertiesAndGet(product,productsMap.get(product.getId()))).toList();
        return productRepo.saveAll(currentProductsInDb);

    }


    @Override
    public void removeProducts(List<Long> productsIds) {
        productRepo.deleteAllById(productsIds);
    }

    @Transactional
    public List<Product> addOrUpdateFromExcel(MultipartFile multipartFile) {
            return Optional.ofNullable(excelHelper.excelToList(multipartFile))
                    .map(productsList-> {
                        List<Product> updatableProducts = new ArrayList<>();
                        List<Product> addableProducts = new ArrayList<>();
                                productsList.forEach(pr -> {
                                    if (isNewProduct(pr))
                                        addableProducts.add(pr);
                                    else
                                        updatableProducts.add(pr);
                                });
                                var currentProductsInDb = productRepo.findAllById(updatableProducts.stream().map(Product::getId).toList());
                        List<Product> union = ListUtils.union(addableProducts, mapPropertiesAndGet(currentProductsInDb, updatableProducts));
                        return productRepo.saveAll(union);
                            }
                    ).orElse(new ArrayList<>());


    }



    @Override
    public Product getBySku(String sku) {
        return productRepo.findBySku(sku)
                  .orElseThrow(()->new ProductNotFoundException("product not found with sku"+sku));
    }

    @Override
    public Page<Product> getAll(ProductPageDto productPage, Pageable pageable) {
        return productRepo.findPagedProducts
                                (productPage.getCategory(),productPage.getSearch()
                                        ,pageable);
    }

    @Override
    public ByteArrayInputStream convertToExcel(List<Long> productIds) {
         return   Optional.ofNullable(productIds)
                   .map(productRepo::findAllById)
                   .map(excelHelper::listToExcel)
                   .orElseThrow();
    }
    private  boolean isNewProduct(Product pr) {
        return Optional.ofNullable(pr.getId()).isEmpty();
    }

private  Product mapPropertiesAndGet(Product target, Product src) {
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
    private  List<Product> mapPropertiesAndGet(List<Product> targets, List<Product> srcs) {
        Map<Long,Product> productsMap = srcs.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        return  targets.stream().map(target-> mapPropertiesAndGet(target,productsMap.get(target.getId()))).toList();
    }
}
