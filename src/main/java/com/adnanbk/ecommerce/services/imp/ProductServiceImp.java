package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.specifications.ProductSpecifications;
import com.adnanbk.ecommerce.dto.ProductPageDto;
import com.adnanbk.ecommerce.enums.Operation;
import com.adnanbk.ecommerce.exceptions.ProductNotFoundException;
import com.adnanbk.ecommerce.exceptions.ProductSkuAlreadyExistException;
import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.reposetories.ProductRepository;
import com.adnanbk.ecommerce.services.ProductService;
import com.adnanbk.excelconverter.core.excelpojoconverter.ExcelHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepo;
    private final ExcelHelper<Product> excelHelper;
    private final ImageService imageService;


    @Override
    public Product addProduct(Product product, List<MultipartFile> fileImages) {
        product.setImageNames(imageService.upload(fileImages));
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Product product, List<MultipartFile> fileImages, Long id) {
        if(fileImages!=null && !fileImages.isEmpty())
            product.setImageNames(imageService.upload(fileImages));
        return productRepo.findById(id).map(pr -> mapPropertiesAndGet(pr, product))
                .map(productRepo::save).orElseThrow();
    }

    @Override
    public List<Product> updateProducts(List<Product> products) {
        return productRepo.saveAll(mapProductsInDb(products));
    }

    @Override
    public void removeProducts(List<Long> productsIds) {
        productRepo.deleteAllById(productsIds);
    }

    public Map<Operation, List<Product>> addOrUpdateFromExcel(MultipartFile multipartFile) {
        Map<String, Product> productsMap = excelHelper.toStream(multipartFile)
                .collect(Collectors.toMap(Product::getSku, Function.identity(), (existed, newest) -> {
            throw new ProductSkuAlreadyExistException("Product sku " + existed.getSku() + " already exists");
        }));
        List<Product> updatableProducts = productRepo.findAllBySkuIn(new ArrayList<>(productsMap.keySet()))
                .stream().map(product -> mapPropertiesAndGet(product, productsMap.remove(product.getSku()))).toList();
        return Map.of(Operation.ADDED, productRepo.saveAll(productsMap.values()), Operation.UPDATED, productRepo.saveAll(updatableProducts));

    }

    @Override
    public void removeProduct(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public ImageDto addImage(MultipartFile imageFile, Long id) {
        return productRepo.findById(id).map(product -> {
            String imageName = imageService.upload(imageFile);
            List<String> imageNames = new ArrayList<>(product.getImageNames());
            imageNames.add(imageName);
            product.setImageNames(imageNames);
            productRepo.save(product);
            return new ImageDto(imageService.toUrl(imageName));
        }).orElseThrow();
    }

    @Override
    public void replaceImages(List<String> imageUrls, Long id){
        productRepo.findById(id).ifPresent(product -> {
            product.setImageNames(imageUrls.stream().map(imageService::toImageName).toList());
            productRepo.save(product);
        });
    }
    @Override
    @Transactional(readOnly = true)
    public Product getBySku(String sku) {
        return productRepo.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("product not found with sku" + sku));
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchBy(ProductPageDto productPage, Pageable pageable) {
        var specification = ProductSpecifications.search(productPage.getCategory(),productPage.getSearch());
        return productRepo.findAll(specification,pageable);
    }

    @Override
    public ByteArrayInputStream convertToExcel(List<Long> productIds) {
        return excelHelper.toExcel(productRepo.findAllById(productIds));
    }

    private List<Product> mapProductsInDb(List<Product> products) {
        Map<Long, Product> productsMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        return productRepo.findAllById(products.stream().map(Product::getId).toList())
                .stream()
                .map(product -> mapPropertiesAndGet(product, productsMap.get(product.getId()))).toList();

    }

    private Product mapPropertiesAndGet(Product target, Product src) {
        target.setSku(src.getSku());
        target.setUnitPrice(src.getUnitPrice());
        target.setName(src.getName());
        target.setCategory(src.getCategory());
        target.setUnitsInStock(src.getUnitsInStock());
        target.setDescription(src.getDescription());
        target.setActive(src.isActive());
        if (src.getImageNames()!=null && !src.getImageNames().isEmpty())
            target.setImageNames(src.getImageNames());
        return target;
    }
}

