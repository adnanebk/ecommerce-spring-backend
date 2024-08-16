package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.Category;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.List;

public interface ProductCategoryRepository extends CrudRepository<Category, Long> {

    Category findByNameIgnoreCase(String categoryName);


    @Override
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<Category> findAll();
}
