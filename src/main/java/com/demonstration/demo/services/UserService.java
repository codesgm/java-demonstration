package com.demonstration.demo.services;

import com.demonstration.demo.cache.LRUCache;
import com.demonstration.demo.entities.User;
import com.demonstration.demo.repositories.UserRepository;
import com.demonstration.demo.services.validators.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final LRUCache<Long, User> userCache = new LRUCache<>(100);

    @Transactional
    public User insert(User user) {
        userValidator.validateInputData(user);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        User user = userCache.getValue(id);
        if (user != null) {
            return user;
        }

        user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado"));

        userCache.putValue(id, user);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User edit(Long id, User newUser) {
        User user = findById(id);

        processUserData(user, newUser);
        userValidator.validateInputData(user);

        userRepository.save(user);
        userCache.putValue(id, user);

        return user;
    }

    void processUserData(User user, User newUser){
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado"));
        userRepository.deleteById(id);
        userCache.removeValue(id);
    }

}

