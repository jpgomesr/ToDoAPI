package net.weg.lista.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @Column
    private String id;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    private String senha;
}
