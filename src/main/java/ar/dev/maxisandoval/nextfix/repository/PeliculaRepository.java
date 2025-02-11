package ar.dev.maxisandoval.nextfix.repository;

import ar.dev.maxisandoval.nextfix.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    Optional<Pelicula> findByGenero(String genero);

    @Query("SELECT p FROM Pelicula p ORDER BY LOWER(p.titulo) ASC")
    List<Pelicula> findByAllByOrderByTituloIgnoreCaseAsc();

    void deleteByDirector(Director director);

    void deleteByGenero(String genero);
}