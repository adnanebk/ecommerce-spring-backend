package com.adnanbk.ecommerce.models;
import java.time.LocalDate;


public record ConfirmationToken(String token,LocalDate expirationDate)
{
}
