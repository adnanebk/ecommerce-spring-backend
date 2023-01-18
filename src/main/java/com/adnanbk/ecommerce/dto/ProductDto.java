package com.adnanbk.ecommerce.dto;

import com.adnanbk.ecommerce.models.Category;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.j2objc.annotations.Property;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductDto {

    private Long id;
    @Column(name = "sku")
    @NotEmpty(message = "{error.empty}")
    private String sku;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Length(min = 10, message = "{error.min}")
    @NotEmpty
    private String description;

    @DecimalMin("0.0")
    @NotNull(message = "{error.empty}")
    private BigDecimal unitPrice;

    @Column(name = "image")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String image;

    private boolean active;

    @Min(value = 0)
    @NotNull(message = "{error.empty}")
    private Integer unitsInStock;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date dateCreated;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date lastUpdated;

    @NotNull(message = "{error.choose}")
    private CategoryDto category;

}
