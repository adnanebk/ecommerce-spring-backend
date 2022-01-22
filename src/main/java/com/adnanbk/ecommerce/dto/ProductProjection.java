package com.adnanbk.ecommerce.dto;

import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.models.ProductCategory;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "customProduct", types = Product.class)
public interface ProductProjection {

    Long getId();

    ProductCategory getCategory();

    String getSku();

    String getName();

    String getDescription();

    BigDecimal getUnitPrice();

    String getImage();

    boolean isActive();

    Integer getUnitsInStock();

    Date getDateCreated();

    Date getLastUpdated();
}
