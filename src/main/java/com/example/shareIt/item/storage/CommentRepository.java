package com.example.shareIt.item.storage;

import com.example.shareIt.item.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByItemId(Long itemId);

    @Query("select c from Comment as c where c.item.id in :itemIds")
    List<Comment> findAllByItemId(@Param("itemIds") List<Long> itemIds);
}
