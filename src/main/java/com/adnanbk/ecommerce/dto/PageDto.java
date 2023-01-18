package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageDto {
    private int number;

    private int size=10;
    private String sortProperty="id";
    private String sortDirection="ASC";
    private String search;
    private Pageable pageable;

    public void buildPageable() {
        this.pageable = PageRequest.of(number,size,
                Sort.by(Sort.Direction.valueOf(sortDirection),sortProperty));

    }
}
