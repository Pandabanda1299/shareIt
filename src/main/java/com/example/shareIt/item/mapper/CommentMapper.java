package com.example.shareIt.item.mapper;

import com.example.shareIt.item.dto.CommentDtoRequest;
import com.example.shareIt.item.dto.CommentDtoResponse;
import com.example.shareIt.item.model.Comment;
import com.example.shareIt.item.model.Item;
import com.example.shareIt.user.model.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommentMapper {
    public Comment mapDtoToComment(CommentDtoRequest dto, User owner, Item item) {
        Comment comment = new Comment();
        comment.setItem(item);
        comment.setText(dto.getText());
        comment.setOwner(owner);
        comment.setCreated(LocalDateTime.now());
        return comment;
    }

    public CommentDtoResponse mapCommentToDto(Comment comment) {
        CommentDtoResponse dto = new CommentDtoResponse();
        dto.setId(comment.getId());
        dto.setItem(comment.getItem());
        dto.setText(comment.getText());
        dto.setAuthorName(comment.getOwner().getName());
        dto.setCreated(comment.getCreated());
        return dto;
    }

    public static List<CommentDtoResponse> mapCommentToDto(Iterable<Comment> comments) {
        List<CommentDtoResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            dtos.add(mapCommentToDto(comment));
        }
        return dtos;
    }
}
