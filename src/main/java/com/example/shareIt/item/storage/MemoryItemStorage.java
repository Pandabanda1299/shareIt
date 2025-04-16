package com.example.shareIt.item.storage;


import com.example.shareIt.exception.NotFoundException;
import com.example.shareIt.item.model.Item;
import com.example.shareIt.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component("itemMemoryStorage")
public class MemoryItemStorage implements ItemStorage {

    private Map<Long, Item> items = new HashMap<>();
    private Long itemId = 1L;
    private final UserService userService;

    @Override
    public Long generateId() {
        return itemId++;
    }


    public MemoryItemStorage(UserService userService) {
        this.userService = userService;
    }


    @Override
    public Item create(Item item) {
        log.info("Добавление новой вещи: {}", item);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Item newItem, Long id) {
        log.info("Обновление вещи: {}", newItem);
        items.put(id, newItem);
        return newItem;
    }

    @Override
    public Item findById(Long id) {
        log.info("Поиск вещи по id = {}", id);
        if (!items.containsKey(id)) {
            throw new NotFoundException("Вещь с id = " + id + " не существует");
        }

        return items.get(id);
    }

    @Override
    public List<Item> findAllByUserId(Long id) {
        log.info("Поиск вещей пользователя с id = {}", id);
        return items.values()
                .stream()
                .filter(item -> Objects.equals(item.getOwner().getId(), id))
                .toList();
    }

    @Override
    public List<Item> findByText(String text) {
        log.info("Поиск вещи по названию {}", text);
        return items.values()
                .stream()
                .filter(item -> !text.isBlank() &&
                        item.getName().toUpperCase().contains(text)
                        && Objects.equals(item.getAvailable(), true))
                .toList();
    }

    @Override
    public void delete(Long id) {
        log.info("Удаление вещи с id = {}", id);
        items.remove(id);
    }
}
