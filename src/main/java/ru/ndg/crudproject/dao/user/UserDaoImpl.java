package ru.ndg.crudproject.dao.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.ndg.crudproject.dao.role.RoleDao;
import ru.ndg.crudproject.model.Role;
import ru.ndg.crudproject.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    // TODO: 23.01.2021  
    @Override
    public User saveUser(User user) {
        passwordEncoder.encode(user.getPassword());
        entityManager.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        User userFromDB = entityManager.find(User.class, user.getId());
        BeanUtils.copyProperties(user, userFromDB, "password");
        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userFromDB.setPassword(user.getPassword());
        }
        return userFromDB;
    }

    @Override
    public void deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("FROM User WHERE nickname = :username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }
}
