package com.adnanbk.ecommerce.dto;


import javax.validation.constraints.NotEmpty;


public record LoginUserDto(
    @NotEmpty String email,
    @NotEmpty String password
){}
