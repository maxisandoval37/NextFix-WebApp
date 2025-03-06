package ar.dev.maxisandoval.nextfix.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data //toString, equals, hashcode, getters y los setters
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String contrasena;
    @Enumerated(EnumType.STRING) // Guarda el enum como string en db
    private Rol rol;
    private String nombre;
    private String apellido;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Director director;
}