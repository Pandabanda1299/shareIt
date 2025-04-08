package com.example.shareIt.item.service.converter;

import com.example.shareIt.item.dto.ItemUpdateDto;
import com.example.shareIt.item.model.Item;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ItemConverter {
    @Mapping(target = "owner", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromDto(ItemUpdateDto itemUpdateDto, @MappingTarget Item item);
}
