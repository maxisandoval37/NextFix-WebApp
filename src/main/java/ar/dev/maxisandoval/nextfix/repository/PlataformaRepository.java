package ar.dev.maxisandoval.nextfix.repository;

import ar.dev.maxisandoval.nextfix.model.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlataformaRepository extends JpaRepository <Plataforma, Long> {
}