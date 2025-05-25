package com.demonstration.demo.services.validators;

import com.demonstration.demo.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validateInputData(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        validateName(user.getName());
        validateEmail(user.getEmail());
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

}
