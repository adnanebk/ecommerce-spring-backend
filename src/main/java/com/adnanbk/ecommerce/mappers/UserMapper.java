package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.UserInputDto;
import com.adnanbk.ecommerce.dto.UserOutputDto;
import com.adnanbk.ecommerce.models.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserOutputDto toDto(AppUser user);
    AppUser toEntity(UserOutputDto userDto);
    AppUser toEntity(UserInputDto userDto);
}
