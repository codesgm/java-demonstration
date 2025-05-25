package com.demonstration.demo.services;

import com.demonstration.demo.entities.User;
import com.demonstration.demo.repositories.UserRepository;
import com.demonstration.demo.services.validators.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Transactional
    public User insert(User user) {
        userValidator.validateInputData(user);
        return userRepository.save(user);
    }




}

