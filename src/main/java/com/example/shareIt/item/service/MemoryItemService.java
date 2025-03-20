package com.example.shareIt.item.service;

import com.example.shareIt.exception.NotFoundException;
import com.example.shareIt.exception.ValidationException;
import com.example.shareIt.item.dto.ItemDto;
import com.example.shareIt.item.mapper.ItemMapper;
import com.example.shareIt.item.model.Item;
import com.example.shareIt.item.storage.ItemStorage;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MemoryItemService implements ItemService {

    private final ItemStorage storage;

    public MemoryItemService(@Qualifier("itemMemoryStorage") ItemStorage storage) {
        this.storage = storage;
    }

    @Override
    public ItemDto create(ItemDto dto, Long userId) {
        if (dto == null) {
            throw new ValidationException("dto cannot be null");
        } else if (StringUtils.isEmpty(dto.getName())) {
            throw new ValidationException("name cannot be empty");
        } else if (StringUtils.isEmpty(dto.getDescription())) {
            throw new ValidationException("description cannot be empty");
        } else if (dto.getAvailable() == null) {
            throw new ValidationException("available cannot be null");
        }
        Item item = storage.create(ItemMapper.mapDtoToItem(dto), userId);
        return ItemMapper.mapItemToDto(item);
    }


    @Override
    public ItemDto update(ItemDto dto, Long id, Long userId) {
        Item item = storage.findById(id);

        if (!Objects.equals(item.getOwner().getId(), userId)) {
            throw new NotFoundException("Обновляемая вещь с id = " + id + " не принадлежит " +
                    "указанному пользователю с id = " + userId);
        }

        // обновляем все поля
        if (!StringUtils.isBlank(dto.getName())
                && !StringUtils.isBlank(dto.getDescription())
                && dto.getAvailable() != null) {
            item = ItemMapper.mapNewItemAllFields(item, dto);

            // обновляем имя и описание
        } else if (!StringUtils.isBlank(dto.getName())
                && !StringUtils.isBlank(dto.getDescription())
                && dto.getAvailable() == null) {
            item = ItemMapper.mapNewItemNameDescription(item, dto);

            // обновляем имя
        } else if (!StringUtils.isBlank(dto.getName())
                && StringUtils.isBlank(dto.getDescription())
                && dto.getAvailable() == null) {
            item = ItemMapper.mapNewItemName(item, dto);

            // обновляем доступность
        } else if (StringUtils.isBlank(dto.getName())
                && StringUtils.isBlank(dto.getDescription())
                && dto.getAvailable() != null) {
            item = ItemMapper.mapNewItemAvailable(item, dto);

            // обновляем описание
        } else if (StringUtils.isBlank(dto.getName())
                && !StringUtils.isBlank(dto.getDescription())
                && dto.getAvailable() == null) {
            item = ItemMapper.mapNewItemDescription(item, dto);

        } else {
            throw new ValidationException("Полученный объект обновляемой вещи не содержит полей");
        }

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
