package com.example.shareIt.user.service;

import com.example.shareIt.exception.ValidationException;
import com.example.shareIt.user.dto.UserDto;
import com.example.shareIt.user.mapper.UserMapper;
import com.example.shareIt.user.model.User;
import com.example.shareIt.user.storage.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemoryUserService implements UserService {
    private final UserStorage storage;

    public MemoryUserService(@Qualifier("userMemoryStorage") UserStorage storage) {
        this.storage = storage;
    }

    @Override
    public UserDto create(UserDto dto) {
        User user = storage.create(UserMapper.mapDtoToUser(dto));
        return UserMapper.mapUserToDto(user);
    }

    @Override
    public UserDto update(UserDto dto, Long id) {
        User user = storage.findById(id);
        if (dto.getEmail() != null && dto.getName() != null) {
            user = User.builder()
                    .id(id)
                    .email(dto.getEmail())
                    .name(dto.getName())
                    .build();

        } else if (dto.getEmail() == null && dto.getName() != null) {
            user = User.builder()
                    .id(id)
                    .email(user.getEmail())
                    .name(dto.getName())
                    .build();

        } else if (dto.getEmail() != null) {
            user = User.builder()
                    .id(id)
                    .email(dto.getEmail())
                    .name(user.getName())
                    .build();
        } else {
            log.error("Полученный UserDto не содержит имя и email");
            throw new ValidationException("Полученный UserDto не содержит имя и email");
        }

        return UserMapper.mapUserToDto(storage.update(user, id));
    }

    @Override
    public void delete(Long id) {
        storage.delete(id);
    }

    @Override
    public List<UserDto> findAll() {
        return storage.findAll()
                .stream()
                .map(UserMapper::mapUserToDto)
                .toList();
    }

    @Override
    public UserDto findById(Long id) {
        return UserMapper.mapUserToDto(storage.findById(id));
    }
}
