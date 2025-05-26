package com.demonstration.demo.services.interfaces;


import com.demonstration.demo.dto.user.UserCreateDTO;
import com.demonstration.demo.dto.user.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO insert(UserCreateDTO userCreateDTO);

    UserResponseDTO findById(Long id);

    List<UserResponseDTO> findAll();

    UserResponseDTO edit(Long id, UserCreateDTO userCreateDTO);

    void delete(Long id);
}
