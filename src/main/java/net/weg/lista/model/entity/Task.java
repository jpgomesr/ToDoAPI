package net.weg.lista.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_task")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private boolean status;
    @Enumerated(EnumType.STRING)
    private Priority prioridade;
    private String userId;

    public enum Priority{Baixa, Média, Alta}
}
