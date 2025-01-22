package com.challengealura.literalura_challenge.repository;

import com.challengealura.literalura_challenge.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAll();

    List<Author> findByFechaNacimientoLessThanOrFechaFallecimientoGreaterThanEqual(int fechaBuscada, int fechaBuscada1);

    Optional<Author> findFirstByNombreContainsIgnoreCase(String escritor);
}
