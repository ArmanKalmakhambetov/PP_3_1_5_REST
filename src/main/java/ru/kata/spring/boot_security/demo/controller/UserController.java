package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final RoleService roleService;

    @Autowired
    public UserController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUserPage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("authorizeUser", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "UserPage";
    }
}
