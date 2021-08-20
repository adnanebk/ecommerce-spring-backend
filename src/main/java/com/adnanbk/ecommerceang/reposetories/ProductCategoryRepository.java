package com.adnanbk.ecommerceang.reposetories;

import com.adnanbk.ecommerceang.models.ProductCategory;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.QueryHint;

@RepositoryRestResource(collectionResourceRel = "productCategory", path = "product-category")
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

    ProductCategory findByNameIgnoreCase(String categoryName);
    boolean existsByName(String name);

    @Override
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Iterable<ProductCategory> findAll();
}
