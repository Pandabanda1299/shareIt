package com.example.shareIt.user.service.converter;

import com.example.shareIt.user.dto.UpdateDto;
import com.example.shareIt.user.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserConverter {
   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void  fromDto(UpdateDto updateDto, @MappingTarget User user);

}
