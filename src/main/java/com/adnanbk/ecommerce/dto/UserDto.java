package com.adnanbk.ecommerce.dto;

import com.adnanbk.ecommerce.models.Role;
import com.adnanbk.ecommerce.validations.uniqueEmail.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
public class UserDto {

    private long id;

    @UniqueEmail
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String firstName;

    private boolean enabled;
    private boolean isSocial;

    private String imageUrl;

    @Column
    @Length(min = 1, message = "{error.min}")
    private String lastName;

    @Column
    @Length(min = 4, message = "{error.min}")
    private String street;

    @Length(min = 2, message = "{error.min}")
    private String city;
    @Length(min = 2, message = "{error.min}")
    private String country;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Collection<Role> roles=new HashSet<>();


}
