package com.adnanbk.ecommerce.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "{error.choose}")
    private ProductCategory category;

    @Column(name = "sku", unique = true)
    @NotEmpty(message = "{error.empty}")
    private String sku;

    @Column(name = "name", unique = true)
    @NotEmpty
    private String name;

    @Column(name = "description", length = 500)
    @Length(min = 10, message = "{error.min}")
    @NotEmpty
    private String description;

    @Column(name = "unit_price")
    @DecimalMin("0.0")
    @NotNull(message = "{error.empty}")
    private BigDecimal unitPrice;

    @Column(name = "image")
    @NotEmpty(message = "{error.upload}")
    private String image;

    @Column(name = "active")
    private boolean active;

    @Column(name = "units_in_stock")
    @Min(value = 0)
    @NotNull(message = "{error.empty}")
    private Integer unitsInStock;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    private Date expirationDate;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;

}
