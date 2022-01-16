package com.adnanbk.ecommerceang.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
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
