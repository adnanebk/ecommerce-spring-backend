package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.CreditCardDto;
import com.adnanbk.ecommerce.dto.UserOrderDto;
import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.models.UserOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    UserOrderDto toDto(UserOrder order);

    UserOrder toEntity(UserOrderDto orderDto);

    @Mapping(target="expirationDate", source = "creditCard.expirationDate",
            dateFormat = "MM/yy")
    CreditCardDto  toCardDto(CreditCard creditCard);

    @Mapping(target = "expirationDate", expression = "java(java.time.YearMonth.parse(creditCardDto.getExpirationDate(),DateTimeFormatter.ofPattern(\"MM/yy\")).atDay(1))")
    CreditCard toCardEntity(CreditCardDto creditCardDto);
}
