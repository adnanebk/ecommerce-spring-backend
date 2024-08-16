package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CategoryDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 2, message = "{error.min}")
    @NotEmpty
    private String name;

}
