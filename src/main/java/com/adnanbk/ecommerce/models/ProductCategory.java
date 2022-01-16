package com.adnanbk.ecommerce.models;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "product_category")
@Data
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    @Length(min = 2, message = "{error.min}")
    @NotEmpty
    private String name;

/*    @OneToMany( mappedBy = "category",orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Product> products;*/


}
