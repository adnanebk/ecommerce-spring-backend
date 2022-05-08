package com.adnanbk.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserInfoDto {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String firstName;


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
}
