package com.adnanbk.ecommerce.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "product_category", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "uniqueKey_name__")})
@Getter
@Setter
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Length(min = 2, message = "{error.min}")
    @NotEmpty
    private String name;


}
