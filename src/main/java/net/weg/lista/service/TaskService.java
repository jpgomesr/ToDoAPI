package net.weg.lista.service;

import net.weg.lista.model.Task;

public class TaskService {
    public boolean hasBlankFields(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException("A tarefa não pode ser nula.");
        }

        return task.getTitulo().isBlank() || task.getDescricao().isBlank();
    }

    public void validateTask(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException("A tarefa não pode ser nula.");
        }

        if (task.getTitulo().isBlank() || task.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Todos os campos da tarefa são obrigatórios.");
        }
    }
}
