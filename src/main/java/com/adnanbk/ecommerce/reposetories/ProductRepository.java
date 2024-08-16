package com.adnanbk.ecommerce.reposetories;


import com.adnanbk.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Optional<Product> findBySku(String sku);



    Page<Product> findAll(Pageable pageable);


    @Query(value = """
            select p from Product p join p.category c where (:cat is null and :sr is null )
            or (:sr is null and lower(c.name) like lower(concat('%', :cat,'%')) )
            or ( ( lower(p.name) like lower(concat('%', :sr,'%'))
                 or lower(p.description) like lower(concat('%', :sr,'%')) )
               and ( :cat is null or
               (lower(c.name) like lower(concat('%', :cat,'%'))) )
             )""")
    Page<Product> findPagedProducts(@Param("cat") String category,@Param("sr") String search, Pageable pageable);

    List<Product> findAllBySkuIn(List<String> skus);
}
