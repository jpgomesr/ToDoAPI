package net.weg.lista.controller;

import net.weg.lista.model.Task;
import net.weg.lista.repository.TaskRepository;
import net.weg.lista.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasksByUserId(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(repository.findTasksByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        Task taskDelete = repository.findById(id).get();
        repository.delete(taskDelete);
        return ResponseEntity.ok(id);
    }
}
