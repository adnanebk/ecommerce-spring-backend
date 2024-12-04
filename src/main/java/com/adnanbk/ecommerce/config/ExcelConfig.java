package com.adnanbk.ecommerce.config;

import com.adnanbk.ecommerce.models.Category;
import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.models.Product.Fields;
import com.adnanbk.ecommerce.reposetories.ProductCategoryRepository;
import com.adnanbk.excelconverter.core.ColumnDefinition;
import com.adnanbk.excelconverter.core.excelpojoconverter.ExcelHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcelConfig {

    
    @Bean
    public ExcelHelper<Product> excelProduct(ProductCategoryRepository categoryRepository){
        return ExcelHelper.create(Product.class,
                ColumnDefinition.with(0,Fields.sku,"Sku"),
                ColumnDefinition.with(1,Fields.name,"Name"),
                ColumnDefinition.with(2,Fields.description,"Description"),
                ColumnDefinition.with(3,Fields.unitPrice,"Price"),
                ColumnDefinition.with(4,Fields.unitsInStock,"Quantity"),
                ColumnDefinition.withConverters(5,Fields.category,"Category", Category.class,
                        categoryRepository::findByNameIgnoreCase, Category::getName)
        );


    }


}
