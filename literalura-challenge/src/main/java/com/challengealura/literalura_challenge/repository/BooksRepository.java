package com.challengealura.literalura_challenge.repository;

import com.challengealura.literalura_challenge.model.Books;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books, Long> {

    boolean existsByTitulo(String titulo);

    static Books findByTituloContainsIgnoreCase(String titulo) {
        return null;
    }

    List<Books> findByIdioma(String idioma);

    @Query("SELECT b FROM Books b ORDER BY b.cantidadDescargas DESC")
    List<Books> findTop20ByCantidadDescargas(Pageable pageable);

    List<Books> findByTituloContainingIgnoreCaseOrAuthorNombreContainingIgnoreCase(String termino, String termino1);
}
