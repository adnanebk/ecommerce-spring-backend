package com.adnanbk.ecommerce.jwt;

import java.util.Date;

public record Tokens(String access, String refresh, Date expirationDate, Date refreshExpirationDate
) {
}
