package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {

    private long id;
    private String userName;

    private String email;

    private String firstName;

    private String lastName;

    private Boolean isSocial = false;

    private boolean enabled;

    private Date expirationDate;


    private String street;

    private String city;
    private String country;
}
