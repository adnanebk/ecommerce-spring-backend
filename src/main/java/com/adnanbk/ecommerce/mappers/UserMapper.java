package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.UserDto;
import com.adnanbk.ecommerce.dto.UserInputDto;
import com.adnanbk.ecommerce.models.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(AppUser user);

    AppUser toEntity(UserDto userDto);
    AppUser toEntity(UserInputDto userDto);
}
