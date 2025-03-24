package com.example.shareIt.item.storage;

import com.example.shareIt.item.model.Item;

import java.util.List;

public interface ItemStorage {

    Item create(Item dto, Long userId);

    Item update(Item newItem, Long id);

    Item findById(Long id);

    List<Item> findAllByUserId(Long id);

    List<Item> findByText(String text);

    void delete(Long id);
}
