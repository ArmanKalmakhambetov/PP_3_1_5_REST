package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RestContr {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestContr(UserService userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getApiUsers() {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteApiUser(@PathVariable Long id) {
        userService.remove(id);
        return new ResponseEntity<>("User with ID = " + id + " was deleted", HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<User> editApiUser(@RequestBody User user, @RequestParam(value = "roles") Set<Role> roles) {

        User edited = new User();
        edited.setId(user.getId());
        edited.setUsername(user.getUsername());
        edited.setLastname(user.getLastname());
        edited.setAge(user.getAge());
        edited.setEmail(user.getEmail());
        edited.setPassword(user.getPassword());
        edited.setRoles(roleService.getRoles(roles));

        userService.updateUser(edited);

        return new ResponseEntity<>(userService.getUserById(user.getId()), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createApiUser(@RequestBody User user, @RequestParam(value = "roles") Set<Role> roles) {

        user.setRoles(roleService.getRoles(roles));
        userService.add(user);

        return new ResponseEntity<>(userService.getUserById(user.getId()), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getApiUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/auth")
    public ResponseEntity<User> getApiAuthUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> apiUpdateUser(@PathVariable("id") long id,
                                                         @RequestBody  User user) {
            userService.updateUser(user);
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);

    }

    @GetMapping("/users/roles")
    public ResponseEntity<List<Role>> apiGetAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
}
