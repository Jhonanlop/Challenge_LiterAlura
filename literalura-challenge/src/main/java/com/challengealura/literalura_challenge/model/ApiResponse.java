package com.challengealura.literalura_challenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {


    @JsonAlias("results")
    List<BooksData> resultadoLibros;

    public List<BooksData> getResultadoLibros() {
        return resultadoLibros;
    }

    public void setResultadoLibros(List<BooksData> resultadoLibros) {
        this.resultadoLibros = resultadoLibros;
    }
}