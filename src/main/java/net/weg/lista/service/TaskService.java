package net.weg.lista.service;

import lombok.AllArgsConstructor;
import net.weg.lista.model.Task;
import net.weg.lista.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Comunicação dos endpoints de task com o banco de dados
 *
 * @author João Paulo Gomes Rodrigues
 * @version 1.0
 * @since 21-02-2025
 */
@Service
@AllArgsConstructor
public class TaskService {
    private TaskRepository repository;

    /**
     * Adiciona uma task no banco de dados
     *
     * @param task body completo da task
     * @return ResponseEntity<Task>
     */
    public ResponseEntity<Task> addTask(Task task) {
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

    public ResponseEntity<List<Task>> getAllTasksByUserId(String userId) {
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

    /**
     * Remove uma task do banco de dados
     *
     * @param id identificação da task
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> deleteTask(String id) {
        Task taskDelete = repository.findById(id).get();
        repository.delete(taskDelete);
        return ResponseEntity.ok(id);
    }

    /**
     * Atualiza o status da task
     *
     * @param taskId identificação da task
     * @param request status atual da task
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> updateTaskStatus(String taskId, Map<String, Boolean> request) {
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
