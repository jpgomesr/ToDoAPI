package net.weg.lista.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_user")
@Builder
public class User {
    @Id
    private String id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
}
