package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.CreditCardDto;
import com.adnanbk.ecommerce.models.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {

    @Mapping(target="expirationDate", source = "creditCard.expirationDate",
            dateFormat = "MM/yy")
    CreditCardDto toDto(CreditCard creditCard);

    @Mapping(target = "expirationDate", expression = "java(java.time.YearMonth.parse(creditCardDto.getExpirationDate(),DateTimeFormatter.ofPattern(\"MM/yy\")).atDay(1))")
    CreditCard toEntity(CreditCardDto creditCardDto);
}
