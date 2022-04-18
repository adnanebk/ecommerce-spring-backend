package com.adnanbk.ecommerce.reposetories;


import com.adnanbk.ecommerce.dto.ProductProjection;
import com.adnanbk.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.QueryHint;
import java.util.Date;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = ProductProjection.class)
public interface ProductRepository extends JpaRepository<Product, Long> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @RestResource(path = "sku")
    Optional<Product> findBySku(String sku);

    @RestResource(path = "category")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);



    @RestResource(path = "nameOrDescription")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findByNameIgnoreCaseContainsOrDescriptionIgnoreCaseContains(String name, String description, Pageable pageable);
    @RestResource(path = "categoryAndNameOrDescription")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findByCategoryIdAndNameIgnoreCaseContainsOrDescriptionIgnoreCaseContains(Long categoryId,String name, String description, Pageable pageable);



    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findAll(Pageable pageable);

    @RestResource(path = "createdDate")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findAllByDateCreated(Date dateCreated, Pageable pageable);

    @RestResource(path = "updatedDate")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findAllByLastUpdated(Date lastUpdated, Pageable pageable);
}
