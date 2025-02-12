package net.weg.lista.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column
    private String id;
    @Column
    private String titulo;
    @Column
    private String descricao;
    @Column
    private boolean status;
    @Column
    private Priority prioridade;
    @Column
    private String userId;

    private enum Priority{LOW, MEDIUM, HIGH}
}
