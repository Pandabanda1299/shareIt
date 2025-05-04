package com.example.shareIt.booking.dto;

import com.example.shareIt.booking.model.enums.Status;
import com.example.shareIt.item.model.Item;
import com.example.shareIt.user.model.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoResponse {
    Long id;
    Item item;
    User booker;
    Status status;
    LocalDateTime start;
    LocalDateTime end;
}
