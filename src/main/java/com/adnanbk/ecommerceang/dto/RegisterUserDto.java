package com.adnanbk.ecommerceang.dto;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String userName;

    private String email;

    private String firstName;

    private String lastName;

    private boolean enabled;


    private String street;

    private String city;
    private String country;
}
