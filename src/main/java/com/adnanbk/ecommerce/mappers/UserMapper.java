package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.UserInputDto;
import com.adnanbk.ecommerce.dto.UserOutputDto;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.utils.ImageUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected ImageUtil imageUtil;

    @Mapping(target = "imageUrl", expression = "java(imageUtil.toImageUrl(user.getImageName()))")
    public abstract UserOutputDto toDto(AppUser user);

    public abstract AppUser toEntity(UserOutputDto userDto);

    public abstract AppUser toEntity(UserInputDto userDto);
}
