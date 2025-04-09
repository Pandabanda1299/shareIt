package com.example.shareIt.item.service;


import com.example.shareIt.exception.NotFoundException;
import com.example.shareIt.item.dto.ItemDto;
import com.example.shareIt.item.dto.ItemUpdateDto;
import com.example.shareIt.item.mapper.ItemMapper;
import com.example.shareIt.item.model.Item;
import com.example.shareIt.item.storage.ItemStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MemoryItemService implements ItemService {

    private static final Logger log = LoggerFactory.getLogger(MemoryItemService.class);
    private final ItemStorage storage;

    public MemoryItemService(@Qualifier("itemMemoryStorage") ItemStorage storage) {
        this.storage = storage;
    }

    @Override
    public ItemDto create(ItemDto dto, Long userId) {
        Item item = storage.create(ItemMapper.mapDtoToItem(dto), userId);
        return ItemMapper.mapItemToDto(item);
    }


    @Override
    public ItemDto update(ItemUpdateDto itemUpdateDto, Long id, Long userId) {
        Item item = storage.findById(id);
        if (!Objects.equals(item.getOwner().getId(), userId)) {
            throw new NotFoundException("Обновляемая вещь с id = " + id + " не принадлежит " +
                    "указанному пользователю с id = " + userId);
        }
        ItemMapper.updateDtoToItem(itemUpdateDto, item);
        log.info(item.toString());
        return ItemMapper.mapItemToDto(storage.update(item, id));
    }

    @Override
    public ItemDto findById(Long id) {
        return ItemMapper.mapItemToDto(storage.findById(id));
    }

    @Override
    public List<ItemDto> findAllByUserId(Long id) {
        return storage.findAllByUserId(id)
                .stream()
                .map(ItemMapper::mapItemToDto)
                .toList();
    }

    @Override
    public List<ItemDto> findByText(String text) {
        return storage.findByText(text)
                .stream()
                .map(ItemMapper::mapItemToDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        storage.delete(id);
    }
}
