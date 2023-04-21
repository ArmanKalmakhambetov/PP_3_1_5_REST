package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsers(Model model) {

        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("authorizeUser", authUser);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "AdminPage";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user) {

        userService.remove(user.getId());
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user, @RequestParam(value = "roles") Set<Role> roles) {

        User editedUser = new User();
        editedUser.setId(user.getId());
        editedUser.setUsername(user.getUsername());
        editedUser.setLastname(user.getLastname());
        editedUser.setAge(user.getAge());
        editedUser.setEmail(user.getEmail());
        editedUser.setPassword(user.getPassword());
        editedUser.setRoles(roleService.getRoles(roles));

        userService.updateUser(editedUser);

        return "redirect:/admin";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("newUser") User user, @RequestParam(value = "roles") Set<Role> roles) {

        user.setRoles(roleService.getRoles(roles));

        userService.add(user);

        return "redirect:/admin";
    }
}
