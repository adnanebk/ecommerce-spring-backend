package com.adnanbk.ecommerceang.reposetories;


import com.adnanbk.ecommerceang.dto.ProductProjection;
import com.adnanbk.ecommerceang.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.QueryHint;
import java.util.Date;

@RepositoryRestResource(excerptProjection = ProductProjection.class)
public interface ProductRepository extends JpaRepository<Product, Long> {





    @RestResource(path="byCategoryAndName")
    //@Query("select prod from Product as prod where prod.category.Id = ?1 and lower(prod.name) like lower(concat('%', ?2,'%')) ")
    Page<Product>  findByCategoryIdAndNameIgnoreCase(Long id,String name, Pageable pageable);

    @RestResource(path="byNameOrDescription")
    Page<Product> findByNameIgnoreCaseContainsOrDescriptionIgnoreCaseContains(String name,String description, Pageable pageable);

    @RestResource(path="byName")
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<Product> findAll(Pageable pageable);

    @RestResource(path="byDate")
    Page<Product> findAllByDateCreated(Date date, Pageable pageable);


    @Override
    @RestResource(exported = false)
    <S extends Product> S save(S s);



    @Override
    void delete(Product product);

    @Override
    void deleteById(Long id);
}
