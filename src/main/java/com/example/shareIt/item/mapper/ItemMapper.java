package com.example.shareIt.item.mapper;

import com.example.shareIt.item.dto.ItemDto;
import com.example.shareIt.item.dto.ItemUpdateDto;
import com.example.shareIt.item.model.Item;
import lombok.experimental.UtilityClass;


@UtilityClass
public class ItemMapper {
    public ItemDto mapItemToDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }


    public void updateDtoToItem(ItemUpdateDto dto, Item item) {
        if (dto.getName() != null) {
            item.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            item.setDescription(dto.getDescription());
        }
        if (dto.getAvailable() != null) {
            item.setAvailable(dto.getAvailable());
        }
    }
}
