package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class Init {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {

        roleService.createRole(new Role("ROLE_ADMIN"));
        roleService.createRole(new Role("ROLE_USER"));

        User userAdmin = new User();
        userAdmin.setUsername("admin");
        userAdmin.setLastname("admin");
        userAdmin.setAge((byte) 35);
        userAdmin.setEmail("admin@mail.ru");
        userAdmin.setPassword("root");
        userAdmin.addRole(roleService.getRoleByRoleName("ROLE_ADMIN"));
        userAdmin.addRole(roleService.getRoleByRoleName("ROLE_USER"));
        userService.add(userAdmin);

        User user = new User();
        user.setUsername("user");
        user.setLastname("user");
        user.setAge((byte) 30);
        user.setEmail("user@mail.ru");
        user.setPassword("root");
        user.addRole(roleService.getRoleByRoleName("ROLE_USER"));
        userService.add(user);
    }
}
