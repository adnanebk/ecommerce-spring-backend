package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Getter
@Setter
public class ProductPageDto extends PageDto {
    private String category;

}
