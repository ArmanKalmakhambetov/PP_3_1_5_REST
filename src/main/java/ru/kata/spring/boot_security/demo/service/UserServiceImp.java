package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleRepository;
import ru.kata.spring.boot_security.demo.DAO.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.*;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    @Transactional
    public void save(User user,  List<Long> roleIds) {
        user.setPassword(encoder.encode(user.getPassword()));
        List<Role> roles = roleRepository.findAllById(roleIds);
        Set<Role> roleSet = new HashSet<>(roles);
        user.setRoles(roleSet);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, User updatedUser, List<Long> roleIds) {
        Optional<User> originalUser = userRepository.findById(id);

        if (originalUser.isPresent()) {
            User user = originalUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setPassword(encoder.encode(updatedUser.getPassword()));
            user.setAge(updatedUser.getAge());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setAuthority(updatedUser.getAuthority());

            Set<Role> roleSet = new HashSet<>(roleRepository.findAllById(roleIds));
            user.setRoles(roleSet);
            userRepository.save(user);
        }
    }

//    @Transactional
//    @Override
//    public void updateUser(User updateUser) {
//        User userToUpdate = userRepository.getById(updateUser.getId());
//        userToUpdate.setFirstName(updateUser.getFirstName());
//        userToUpdate.setLastName(updateUser.getLastName());
//        userToUpdate.setAge(updateUser.getAge());
//        userToUpdate.setUsername(updateUser.getUsername());
//        userToUpdate.setPassword(encoder.encode(updateUser.getPassword()));
//        userToUpdate.setRoles(updateUser.getRoles());
//        userRepository.save(userToUpdate);
//    }



}
