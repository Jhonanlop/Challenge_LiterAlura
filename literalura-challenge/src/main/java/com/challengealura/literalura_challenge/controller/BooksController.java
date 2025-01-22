package com.challengealura.literalura_challenge.controller;

import com.challengealura.literalura_challenge.model.Books;
import com.challengealura.literalura_challenge.repository.BooksRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BooksController {  private final BooksRepository booksRepository;

    public BooksController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @PostMapping
    public ResponseEntity<String> agregarLibro(@RequestBody Books libro) {

        booksRepository.save(libro);


        return ResponseEntity.ok("Libro agregado con éxito: " + libro.getTitulo());
    }
}