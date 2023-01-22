package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDto {
    private int number;

    private int size=10;
    private String sortProperty="id";
    private String sortDirection="ASC";
    private String search;
}
