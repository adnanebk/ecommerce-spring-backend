package com.adnanbk.ecommerceang.models;

import com.adnanbk.ecommerceang.validations.ConfirmPassword;
import com.adnanbk.ecommerceang.validations.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@ConfirmPassword
@NoArgsConstructor
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(unique = true,name = "user_name")
	private String userName;

	@Column(unique = true)
	@UniqueEmail
	@Email
	@NotEmpty
	private String email;

    @OneToMany( mappedBy = "appUser",orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CreditCard> creditCards=new HashSet<>();

	@Column
	@NotEmpty
	private String firstName;

	@Column
	@NotEmpty
	private String lastName;

	@Column
	private String street;

	private String city;
	private String country;
	private boolean enabled;

	@Column
	@NotEmpty
	@Length(min = 4,message = "{error.min}")
	private String password;

	@Length(min = 4,message = "{error.min}")
	@Transient
	private String confirmPassword;



	public AppUser(String userName, String email, String firstName, String lastName, String password) {
		this.userName = userName;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.confirmPassword = password;
	}








}