package com.example.shareIt.user.mapper;

import com.example.shareIt.user.dto.UpdateDto;
import com.example.shareIt.user.dto.UserDto;
import com.example.shareIt.user.model.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass

public class UserMapper {

    public User mapDtoToUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }

    public UserDto mapUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public void updateDtoToUser(UpdateDto updateDto, User user) {
        if (updateDto.getName() != null) {
            user.setName(updateDto.getName());
        }
        ;
        if (updateDto.getEmail() != null) {
            user.setEmail(updateDto.getEmail());
        }
    }
}
