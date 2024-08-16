package com.adnanbk.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
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
