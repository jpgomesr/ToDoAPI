package net.weg.lista.service;

import net.weg.lista.model.User;

public class UserService {
    public boolean userTest(User user) {
        if (user.getNome().isBlank() || user.getEmail().isBlank() || user.getSenha().isBlank()) {
            return true;
        }
        return false;
    }
}
