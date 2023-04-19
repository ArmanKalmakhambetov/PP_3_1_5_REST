package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(long id);

    User findByUsername(String username);

    void save(User user, List<Long> roleIds);

    void save(User user);

    void deleteUserByUsername(String username);

    void deleteById(Long id);

    public void update(Long id, User updatedUser, List<Long> roleIds);

//    public void updateUser(User updateUser);

}
