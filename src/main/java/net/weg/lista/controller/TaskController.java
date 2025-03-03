package net.weg.lista.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.weg.lista.model.entity.Task;
import net.weg.lista.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskController {
    private TaskService service;

    @PostMapping
    @Tag(name = "Task", description = "Operações relacionadas a task")
    @Operation(summary = "Criação de task", description = "Retorna a task que foi criado")
    @ApiResponse(responseCode = "200", description = "Task criada com sucesso", content = @Content(schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<Task> addTask(
            @Parameter(description = "Objeto Task a ser criado", required = true,
                    content = @Content(schema = @Schema(implementation = Task.class)))
            @RequestBody Task task
    ) {
        return service.addTask(task);
    }

    @GetMapping("/{userId}")
    @Tag(name = "Task", description = "Operações relacionadas a task")
    @Operation(summary = "Busca de task", description = "Retorna as task de determinado usuário")
    @ApiResponse(responseCode = "200", description = "Todas as tasks do usuário", content = @Content(schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "400", description = "User id não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<List<Task>> getAllTasksByUserId(
            @Parameter(description = "Id do usuário a ser retornada todas tasks", required = true)
            @PathVariable String userId
    ) {
        return service.getAllTasksByUserId(userId);
    }

    @DeleteMapping("/{id}")
    @Tag(name = "Task", description = "Operações relacionadas a task")
    @Operation(summary = "Remoção de task", description = "Deleta a task")
    @ApiResponse(responseCode = "200", description = "Deleta a task", content = @Content(schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<String> deleteTask(
            @Parameter(description = "Id da task a ser deletada", required = true)
            @PathVariable String id
    ) {
        return service.deleteTask(id);
    }

    @PutMapping("/{taskId}")
    @Tag(name = "Task", description = "Operações relacionadas a task")
    @Operation(summary = "Atualização de status da task", description = "Muda status da task")
    @ApiResponse(responseCode = "200", description = "Task editada com sucesso", content = @Content(schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<String> updateTaskStatus(
            @Parameter(description = "Id da task a ser alterado o status", required = true)
            @PathVariable String taskId,
            @Parameter(description = "Status atual da task", required = true)
            @RequestBody Map<String, Boolean> request
    ) {
        return service.updateTaskStatus(taskId, request);
    }
}
