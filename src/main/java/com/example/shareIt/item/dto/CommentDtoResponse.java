package com.example.shareIt.item.dto;

import com.example.shareIt.item.model.Item;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDtoResponse {
    Long id;
    String text;
    Item item;
    String authorName;
    LocalDateTime created;
}
