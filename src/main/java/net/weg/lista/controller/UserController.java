package net.weg.lista.controller;

import net.weg.lista.model.User;
import net.weg.lista.repository.UserRepository;
import net.weg.lista.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User userSave = new User(UUID.randomUUID().toString(), user.getNome(), user.getEmail(),
                    encoder.encode(user.getSenha()));
            repository.save(userSave);
            return ResponseEntity.ok(userSave);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
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
}
