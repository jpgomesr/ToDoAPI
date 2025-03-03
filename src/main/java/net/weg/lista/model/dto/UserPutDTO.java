package net.weg.lista.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.weg.lista.model.entity.User;
import net.weg.lista.service.UserService;

public record UserPutDTO(
        @NotNull
        String id,
        @NotBlank
        String nome,
        @NotBlank @Email
        String email,
        @NotBlank
        String senha,
        @NotBlank
        String novaSenha
) {
    public User convert() {
        return User.builder().id(id).nome(nome).email(email).senha(novaSenha).build();
    }
}
