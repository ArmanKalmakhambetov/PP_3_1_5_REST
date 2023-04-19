package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
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
    public String getAdmin(Model model, Principal principal) {
        final String currentUser = principal.getName();
        model.addAttribute("authUser", userService.findByUsername(currentUser));
        model.addAttribute("new_user", new User());
        model.addAttribute("edit_user", new User());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }


    @PostMapping("/create")
    public String addUser(@ModelAttribute("new_user") User user,
                          @RequestParam(value = "rol", required = false) List<Long> roles) {
        user.setAuthority(roleService.getRoleById(roles.get(0)));
        userService.save(user, roles);
        return "redirect:/admin";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("edit_user") User updatedUser,
                             @RequestParam(name = "rol", required = false) List<Long> roles) {
        User user = userService.getById(updatedUser.getId());
        user.setAuthority(roleService.getRoleById(roles.get(0)));
        updatedUser.setAuthority(user.getAuthority());
        userService.update(user.getId(), updatedUser, roles);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }


}

