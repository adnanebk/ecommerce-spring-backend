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

@RepositoryRestResource(excerptProjection = ProductProjection.class)
public interface ProductRepository extends JpaRepository<Product, Long> {



    @RestResource(path = "byCategory")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findByCategoryId(Long id, Pageable pageable);

    @RestResource(path = "byCategoryAndName")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findByCategoryIdAndNameIgnoreCase(Long id, String name, Pageable pageable);

    @RestResource(path = "byNameOrDescription")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findByNameIgnoreCaseContainsOrDescriptionIgnoreCaseContains(String name, String description, Pageable pageable);

    @RestResource(path = "byName")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findAll(Pageable pageable);

    @RestResource(path = "byDate")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findAllByDateCreated(Date date, Pageable pageable);

}
