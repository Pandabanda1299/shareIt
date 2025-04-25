package com.example.shareIt.item.dto;

import com.example.shareIt.booking.dto.BookingDtoResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {
    Long id;

    @NotBlank
    String name;

    @NotBlank
    String description;

    @NotNull
    Boolean available;

    BookingDtoResponse lastBooking;

    BookingDtoResponse nextBooking;

    List<CommentDtoResponse> comments;
}
