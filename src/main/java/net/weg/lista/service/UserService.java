package net.weg.lista.service;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import net.weg.lista.model.dto.UserPutDTO;
import net.weg.lista.model.entity.User;
import net.weg.lista.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Comunicação dos endpoints de usuário com o banco de dados
 *
 * @author João Paulo Gomes Rodrigues
 * @version 1.0
 * @since 21-02-2025
 */
@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;
    private PasswordEncoder encoder;

    /**
     * Registra um novo usuário em nosso sistema
     *
     * @param user body completo do usuário
     * @return ResponseEntity<User>
     */
    public ResponseEntity<User> registerUser(User user) {
        try {
            User userSave = new User(UUID.randomUUID().toString(), user.getNome(), user.getEmail(),
                    encoder.encode(user.getSenha()));
            repository.save(userSave);
            return ResponseEntity.ok(userSave);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Realiza a verificação de usuários comparando suas credenciais com alguma do sistema,
     * retornando um método de ok ou inválido
     *
     * @param user body completo do usuário
     * @return ResponseEntity<Map<String, String>>
     */
    public ResponseEntity<Map<String, String>> loginUser(User user) {
        Map<String, String> response = new HashMap<>();
        User userTest = repository.findUserByEmail(user.getEmail());

        if (userTest != null && encoder.matches(user.getSenha(), userTest.getSenha())) {
            response.put("message", "Login realizado com sucesso!");
            response.put("userId", userTest.getId());
            return ResponseEntity.ok(response);
        }
        response.put("error", "Credenciais incorretas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Busca um usuário em nosso sistema por meio de id
     *
     * @param id identificação do usuário que estamos buscando no banco de dados
     * @return ResponseEntity<User>
     */
    public ResponseEntity<User> getUser(String id) {
        try {
            return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Busca um usuário e depois atualiza suas informações
     *
     * @param userDto Dto de usuário apenas com informações pertinentes
     * @return ResponseEntity<User>
     */
    public ResponseEntity<User> editUser(UserPutDTO userDto) {
        User user = repository.findById(userDto.id()).get();
        if (!passwordMatcher(userDto.senha(), user.getSenha())) {
            throw new IllegalArgumentException("Senha atual não coincide");
        }
        user.setNome(userDto.nome());
        user.setEmail(userDto.email());
        user.setSenha(encoder.encode(userDto.novaSenha()));
        return ResponseEntity.ok(repository.save(user));
    }

    private boolean passwordMatcher(@NotBlank String senha, String senhaCripto) {
        return encoder.matches(senha, senhaCripto);
    }
}