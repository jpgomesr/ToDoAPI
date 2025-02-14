package net.weg.lista.service;

import net.weg.lista.model.User;

public class UserService {
    public boolean hasBlankFields(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }

        return user.getNome().isBlank() || user.getEmail().isBlank() || user.getSenha().isBlank();
    }

    public void validateUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }

        if (user.getNome().isBlank() || user.getEmail().isBlank() || user.getSenha().isBlank()) {
            throw new IllegalArgumentException("Todos os campos do usuário são obrigatórios.");
        }
    }
}