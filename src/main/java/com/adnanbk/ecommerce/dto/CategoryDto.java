package com.adnanbk.ecommerce.dto;

import com.adnanbk.ecommerce.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class CategoryDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 2, message = "{error.min}")
    @NotEmpty
    private String name;

}
