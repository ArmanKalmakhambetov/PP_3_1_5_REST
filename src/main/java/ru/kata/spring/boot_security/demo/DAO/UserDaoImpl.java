package ru.kata.spring.boot_security.demo.DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.merge(user);
    }

    @Override
    public void saveRoleToUser(Long userId, String roleName) {

        User user = getUserById(userId);

        user.addRole(entityManager
                .createQuery("from Role r where r.roleName = :roleName", Role.class)
                .setParameter("roleName", roleName).getSingleResult());

        update(user);
    }

    @Override
    public void remove(Long id) {

        User user = getUserById(id);
        entityManager.remove(user);
    }

    @Override
    public void removeRoleFromUser(Long userID, String roleName) {

        User user = getUserById(userID);

        for (Role role : user.getRoles()) {

            if (role.getAuthority().equals(roleName)) {
                user.removeRole(role);
                break;
            }
        }

        update(user);
    }

    @Override
    public User getUserByUsername(String username) throws NoResultException {

        User user;

        user = entityManager
                .createQuery("from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();

        return user;
    }

    @Override
    public User getUserByEMail(String userEMail) {
        User user;

        user = entityManager
                .createQuery("from User u where u.email = :username", User.class)
                .setParameter("username", userEMail)
                .getSingleResult();

        return user;
    }

    @Override
    @Query(name = "User.UpdateUserWithRoles", value = "SELECT u FROM User u LEFT JOIN FETCH u.roles")
    public void update(User user) {

        entityManager.merge(user);
    }

    @Override
    @Query(name = "User.findUserWithRoles", value = "SELECT u FROM User u LEFT JOIN FETCH u.roles")
    public User getUserById(Long id) {

        return entityManager.find(User.class, id);
    }

    @Override
    @Query(name = "Users.findAllUsersWithRoles", value = "SELECT u FROM User u LEFT JOIN FETCH u.roles")
    public List<User> getAllUsers() {

        List<User> users = entityManager.createQuery("from User").getResultList();
        return users;
    }


}
