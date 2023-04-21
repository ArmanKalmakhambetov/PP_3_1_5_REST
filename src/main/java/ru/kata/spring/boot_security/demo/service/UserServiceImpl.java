package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public final UserDao userDao;

    public final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void add(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
    }

    @Override
    @Transactional
    public void remove(Long id) {

        userDao.remove(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user.getPassword().length() == 0) {
            user.setPassword(getUserById(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDao.update(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEMail(String userEMail) {
        return userDao.getUserByEMail(userEMail);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {

        return userDao.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {

        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Long userID, String roleName) {

        userDao.removeRoleFromUser(userID, roleName);
    }

    @Override
    @Transactional
    public void saveRoleToUser(Long userId, String roleName) {

        userDao.saveRoleToUser(userId, roleName);
    }
}
