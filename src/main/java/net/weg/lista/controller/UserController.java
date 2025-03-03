package net.weg.lista.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.weg.lista.model.dto.UserPutDTO;
import net.weg.lista.model.entity.Task;
import net.weg.lista.model.entity.User;
import net.weg.lista.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService service;

    @PostMapping("/register")
    @Tag(name = "Usuário", description = "Operações relacionadas a usuários")
    @Operation(summary = "Registro de usuário", description = "Cria e retorna um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso", content = @Content(schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<User> registerUser(
            @Parameter(description = "Objeto Usuário a ser criado", required = true,
                    content = @Content(schema = @Schema(implementation = User.class)))
            @RequestBody User user
    ) {
        return service.registerUser(user);
    }

    @PostMapping("/login")
    @Tag(name = "Usuário", description = "Operações relacionadas a usuários")
    @Operation(summary = "Login de usuário", description = "Login de usuário e retorno de id caso existente")
    @ApiResponse(responseCode = "200", description = "Retorna o id do usuário", content = @Content(schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "401", description = "Credenciais de login incorretas ou inexistentes")
    public ResponseEntity<Map<String, String>> loginUser(
            @Parameter(description = "Objeto Usuário a ser autenticado", required = true,
                    content = @Content(schema = @Schema(implementation = Task.class)))
            @RequestBody User user
    ) {
        return service.loginUser(user);
    }

    @GetMapping("/{id}")
    @Tag(name = "Usuário", description = "Operações relacionadas a usuários")
    @Operation(summary = "Busca de usuário", description = "Retorna caso usuário for existente no banco de dados")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<User> getUser(
            @Parameter(description = "Id do usuário a ser buscado", required = true)
            @PathVariable String id
    ) {
        return service.getUser(id);
    }

    @PutMapping("/")
    @Tag(name = "Usuário", description = "Operações relacionadas a usuários")
    @Operation(summary = "Editar um usuário", description = "Retorna o usuário atualizado após edição")
    @ApiResponse(responseCode = "200", description = "Usuário editado", content = @Content(schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<User> editUser(
            @Parameter(description = "Id do usuário a ser buscado", required = true)
            @RequestBody @Valid UserPutDTO userDto
    ) {
        return service.editUser(userDto);
    }

    @GetMapping("/teste")
    public ResponseEntity<String> testeGet() {
        return new ResponseEntity<>("teste", HttpStatus.OK);
    }
}
