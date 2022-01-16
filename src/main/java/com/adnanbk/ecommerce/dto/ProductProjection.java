package com.adnanbk.ecommerce.dto;

import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.models.ProductCategory;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "customProduct", types = Product.class)
public interface ProductProjection {

    public Long getId();

    public ProductCategory getCategory();

    public String getSku();

    public String getName();

    public String getDescription();

    public BigDecimal getUnitPrice();

    public String getImage();

    public boolean isActive();

    public Integer getUnitsInStock();

    public Date getDateCreated();

    public Date getLastUpdated();
}
