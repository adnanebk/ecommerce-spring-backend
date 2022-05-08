package com.adnanbk.ecommerce.dto;

import com.adnanbk.ecommerce.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String email;

    private String firstName;

    private boolean enabled;
    private boolean isSocial;

    private String imageUrl;


    private String lastName;


    private String street;

    private String city;
    private String country;

    private String password;

    private Collection<Role> roles=new HashSet<>();


}
