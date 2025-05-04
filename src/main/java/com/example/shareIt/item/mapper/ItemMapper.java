package com.example.shareIt.item.mapper;

import com.example.shareIt.booking.dto.BookingDtoResponse;
import com.example.shareIt.item.dto.CommentDtoResponse;
import com.example.shareIt.item.dto.ItemDto;
import com.example.shareIt.item.model.Item;
import com.example.shareIt.user.model.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;


@UtilityClass
public class ItemMapper {

    public Item mapDtoToItem(ItemDto dto, User owner) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO is null");
        }
        Item item = new Item();
        item.setOwner(owner);
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setAvailable(dto.getAvailable());
        return item;
    }

    public ItemDto mapItemToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        return dto;
    }

    public ItemDto mapItemToDto(Item item, BookingDtoResponse lastBooking, BookingDtoResponse nextBooking,
                                List<CommentDtoResponse> comments) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        dto.setLastBooking(lastBooking);
        dto.setNextBooking(nextBooking);
        dto.setComments(comments);
        return dto;
    }

    public static List<ItemDto> mapItemToDto(Iterable<Item> items) {
        List<ItemDto> dtos = new ArrayList<>();
        for (Item item : items) {
            dtos.add(mapItemToDto(item));
        }
        return dtos;
    }
}
