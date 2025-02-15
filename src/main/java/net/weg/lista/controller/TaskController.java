package net.weg.lista.controller;

import net.weg.lista.model.Task;
import net.weg.lista.repository.TaskRepository;
import net.weg.lista.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskRepository repository;
    private TaskService service;

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        try {
            if (!Arrays.asList(Task.Priority.values()).contains(task.getPrioridade())) {
                return ResponseEntity.badRequest().build();
            }

            try {
                UUID.fromString(task.getUserId());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }

            Task taskSave = new Task(UUID.randomUUID().toString(), task.getTitulo(), task.getDescricao(),
                    false, task.getPrioridade(), task.getUserId());
            repository.save(taskSave);
            return ResponseEntity.ok(taskSave);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Task>> getAllTasksByUserId(@PathVariable String userId) {
        try {
            UUID.fromString(userId);

            List<Task> tasks = repository.findTasksByUserId(userId);

            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        Task taskDelete = repository.findById(id).get();
        repository.delete(taskDelete);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTaskStatus(
            @PathVariable String taskId,
            @RequestBody Map<String, Boolean> request
    ) {
        try {
            boolean newStatus = request.get("status");
            Task task = repository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

            task.setStatus(newStatus);
            repository.save(task);

            return ResponseEntity.ok("Status atualizado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
