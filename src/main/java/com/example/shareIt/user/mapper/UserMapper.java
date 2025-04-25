package com.example.shareIt.user.mapper;

import com.example.shareIt.user.dto.UserDto;
import com.example.shareIt.user.model.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@UtilityClass
public class UserMapper {

    public User mapDtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        return user;
    }


    public UserDto mapUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        return dto;
    }

    public static List<UserDto> mapUserToDto(Iterable<User> users) {
        List<UserDto> result = new ArrayList<>();

        for (User user : users) {
            result.add(mapUserToDto(user));
        }

        return result;
    }
}