package com.example.shareIt.item;


import com.example.shareIt.item.dto.CommentDtoRequest;
import com.example.shareIt.item.dto.CommentDtoResponse;
import com.example.shareIt.item.dto.ItemDto;
import com.example.shareIt.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @GetMapping
    public List<ItemDto> findByOwnerId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.findByOwnerId(userId);
    }

    @GetMapping("/{id}")
    public ItemDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public List<ItemDto> findByText(@RequestParam("text") String text) {
        return service.findByText(text);
    }

    @PostMapping
    public ItemDto create(@Valid @RequestBody ItemDto dto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.create(dto, userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDtoResponse addComment(@Valid @RequestBody CommentDtoRequest dto, @PathVariable Long itemId,
                                         @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.addComment(dto, itemId, userId);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestBody ItemDto newItem, @PathVariable("id") Long id,
                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.update(newItem, id, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
