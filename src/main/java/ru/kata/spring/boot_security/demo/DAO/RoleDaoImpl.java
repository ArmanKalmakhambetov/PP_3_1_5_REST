package ru.kata.spring.boot_security.demo.DAO;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {

        List<Role> roles = entityManager.createQuery("from Role", Role.class).getResultList();
        return roles;
    }

    @Override
    public void createRole(Role role) {

        entityManager.persist(role);

    }

    @Override
    public Role getRoleByRoleName(String roleName) {

        Role findedRole = entityManager
                .createQuery("from Role role where roleName = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
        Hibernate.initialize(findedRole.getUsers());
        return findedRole;
    }

    @Override
    public Set<Role> getRoles(Set<Role> roles) {
        Set<Role> rolesSet = new HashSet<>();
        for (Role role : roles) {
            if (role.getAuthority().contains("ROLE_")) {
                rolesSet.add(getRoleByRoleName(role.getAuthority()));
            } else {
                rolesSet.add(getRoleByRoleName("ROLE_" + role.getAuthority()));
            }
        }
        return rolesSet;
    }
}
