package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.UserInputDto;
import com.adnanbk.ecommerce.dto.UserOutputDto;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.services.imp.ImageService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected ImageService imageService;

    public abstract UserOutputDto toDtoMapping(AppUser user);

    public abstract AppUser toEntity(UserOutputDto userDto);

    public abstract AppUser toEntity(UserInputDto userDto);

    public UserOutputDto toDto(AppUser user){
        UserOutputDto userOutputDto =  toDtoMapping(user);
        userOutputDto.setImageUrl(imageService.toUrl(user.getImageName()));
        return  userOutputDto;
    }
}
