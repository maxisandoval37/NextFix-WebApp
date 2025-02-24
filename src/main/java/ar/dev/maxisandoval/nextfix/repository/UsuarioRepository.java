package ar.dev.maxisandoval.nextfix.repository;

import ar.dev.maxisandoval.nextfix.model.Director;
import ar.dev.maxisandoval.nextfix.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);
    Usuario findByDirector(Director director);
    List<Usuario> findByDirectorIsNotNull();

}