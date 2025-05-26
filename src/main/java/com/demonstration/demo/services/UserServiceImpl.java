package com.demonstration.demo.services;

import com.demonstration.demo.cache.LRUCache;
import com.demonstration.demo.dto.user.UserCreateDTO;
import com.demonstration.demo.dto.user.UserResponseDTO;
import com.demonstration.demo.entities.User;
import com.demonstration.demo.repositories.UserRepository;
import com.demonstration.demo.services.interfaces.UserService;
import com.demonstration.demo.services.validators.UserValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final LRUCache<Long, User> userCache = new LRUCache<>(100);

    @Transactional
    @Override
    public UserResponseDTO insert(UserCreateDTO userCreateDTO) {
        User user = modelMapper.map(userCreateDTO, User.class);

        userValidator.validateInputData(user);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDTO findById(Long id) {
        User user = userCache.getValue(id);
        if (user != null) {
            return modelMapper.map(user, UserResponseDTO.class);
        }

        user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado"));

        userCache.putValue(id, user);

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserResponseDTO edit(Long id, UserCreateDTO userCreateDTO) {
        User existingUser = findUserEntityById(id);

        User updatedUserData = modelMapper.map(userCreateDTO, User.class);

        processUserData(existingUser, updatedUserData);
        userValidator.validateInputData(existingUser);

        User savedUser = userRepository.save(existingUser);
        userCache.putValue(id, savedUser);

        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    private User findUserEntityById(Long id) {
        User user = userCache.getValue(id);
        if (user != null) {
            return user;
        }

        user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado"));

        userCache.putValue(id, user);
        return user;
    }

    void processUserData(User user, User newUser){
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado"));
        userRepository.deleteById(id);
        userCache.removeValue(id);
    }

}

