package com.challengealura.literalura_challenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BooksData(
        @JsonAlias("id") Long libroId,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<Author> author,
        @JsonAlias("subjects") List<String> genero,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Long cantidadDescargas
) {
}