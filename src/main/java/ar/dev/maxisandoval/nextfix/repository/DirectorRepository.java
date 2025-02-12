package ar.dev.maxisandoval.nextfix.repository;

import ar.dev.maxisandoval.nextfix.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository <Director, Long> {
}