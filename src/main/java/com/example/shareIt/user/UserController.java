package com.example.shareIt.user;

import com.example.shareIt.user.dto.UpdateDto;
import com.example.shareIt.user.dto.UserDto;
import com.example.shareIt.user.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto user) {
        return service.create(user);
    }

    @PatchMapping("/{id}")
    public UserDto update(@Valid @RequestBody UpdateDto newUser, @PathVariable("id") Long id) {
        log.info("Updating user with id new user {}", newUser);
        return service.update(newUser, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
