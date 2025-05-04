package com.example.shareIt.item.service;

import com.example.shareIt.item.dto.CommentDtoRequest;
import com.example.shareIt.item.dto.CommentDtoResponse;
import com.example.shareIt.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto create(ItemDto item, Long userId);

    CommentDtoResponse addComment(CommentDtoRequest dto, Long itemId, Long userId);

    ItemDto update(ItemDto newItem, Long id, Long userId);

    ItemDto findById(Long id);

    List<ItemDto> findByOwnerId(Long id);

    List<ItemDto> findByText(String text);

    void delete(Long id);

}
