package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    public final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {

        return roleDao.getAllRoles();
    }

    @Override
    @Transactional
    public void createRole(Role role) {
        roleDao.createRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByRoleName(String roleName) {
        return roleDao.getRoleByRoleName(roleName);
    }

    @Override
    public Set<Role> getRoles(Set<Role> roles) {
        return roleDao.getRoles(roles);
    }
}
