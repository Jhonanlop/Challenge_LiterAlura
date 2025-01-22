package com.challengealura.literalura_challenge.model;
import com.challengealura.literalura_challenge.model.BooksData;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")

public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Books> books;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public List<Books> getLibros() {
        return books;
    }

    public void setLibros(List<Books> libros) {
        this.books = libros;
    }

    public Author(com.challengealura.literalura_challenge.model. Author author) {
        this.nombre = author.getNombre();
        this.fechaNacimiento = author.getFechaNacimiento();
        this.fechaFallecimiento = author.getFechaFallecimiento();
    }

    public String toString(){
        return
                "nombre = ' " + nombre + '\'' +
                        ", fechaNacimiento = " + fechaNacimiento +
                        ", fechaFallecimiento = " + fechaFallecimiento;
    }
}