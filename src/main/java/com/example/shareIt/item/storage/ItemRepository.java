package com.example.shareIt.item.storage;

import com.example.shareIt.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerId(Long id);

    List<Item> findByNameContainingIgnoreCase(String text);

    List<Item> findByDescriptionContainingIgnoreCase(String text);
}