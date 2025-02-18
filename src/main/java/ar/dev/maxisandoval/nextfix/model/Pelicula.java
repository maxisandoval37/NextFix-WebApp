package ar.dev.maxisandoval.nextfix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data //toString, equals, hashcode, getters y los setters
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "El titulo no puede estar en blanco")
    @Size(min = 1, max = 50)
    private String titulo;

    @NotBlank(message = "El genero no puede estar en blanco")
    @Size(min = 3, max = 20)
    private String genero;

    @NotNull(message = "La fecha de estreno no puede ser nula")
    private LocalDate fechaEstreno;

    @ManyToOne
    @JoinColumn(name = "director_id")
    @ToString.Exclude
    @NotNull(message = "El director no puede ser nulo")
    private Director director;

    @ManyToMany
    @JoinTable(name = "Pelicula_Plataforma",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "plataforma_id"))
    @ToString.Exclude
    @Builder.Default
    private List<Plataforma> plataformasDisponibles = new ArrayList<>();//netflix, hbo, cine, tv, etc
}