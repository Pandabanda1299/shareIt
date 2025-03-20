package com.example.shareIt.user.storage;

import com.example.shareIt.user.model.User;

import java.util.List;

public interface UserStorage {

    User create(User user);

    User update(User user, Long id);

    void delete(Long id);

    List<User> findAll();

    User findById(Long id);
}
