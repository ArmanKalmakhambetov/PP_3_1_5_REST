package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void add(User user);

    void update(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    void remove(Long id);

    User getUserByUsername(String username);

    void removeRoleFromUser(Long userID, String roleName);

    void saveRoleToUser(Long userId, String roleName);

    User getUserByEMail(String userEMail);
}
