package com.challengealura.literalura_challenge.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long booksId;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")

    private Author author;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String idioma;
    private Long cantidadDescargas;


    public Books() {}

    public Books(BooksData booksData) {
        this.booksId = booksData.libroId();
        this.titulo = booksData.titulo();

        if (booksData.author() != null && !booksData.author().isEmpty()) {
            this.author = new Author(booksData.author().get(0));
        } else {
            this.author = null;
        }

        this.genero = generoDesconocido(booksData.genero());
        this.idioma = idiomaDesconocido(booksData.idioma());
        this.cantidadDescargas = booksData.cantidadDescargas();
    }

    private Genero generoDesconocido(List<String> generos) {
        if (generos == null || generos.isEmpty()) {
            return Genero.DESCONOCIDO;
        }

        Optional<String> firstGenero = generos.stream()
                .map(g -> {
                    int index = g.indexOf("--");
                    return index != -1 ? g.substring(index + 2).trim() : null;
                })
                .filter(Objects::nonNull)
                .findFirst();

        return firstGenero.map(Genero::fromString).orElse(Genero.DESCONOCIDO);
    }

    private String idiomaDesconocido(List<String> idioma) {

        if (idioma == null || idioma.isEmpty()){
            return "Desconocido";
        }
        return idioma.get(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBooksId() {
        return booksId;
    }

    public void setBooksId(Long booksId) {
        this.booksId = booksId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Long cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    @Override
    public String toString() {
        return
                "  \nid = " + id +
                        ", \nbooksId = " + booksId +
                        ", \ntitulo = ' " + titulo + '\'' +
                        ", \nauthor = " + (author != null ? author.getNombre() : "N/A") +
                        ", \ngenero = " + genero +
                        ", \nidioma = ' " + idioma + '\'' +
                        ", \ncantidadDescargas = " + cantidadDescargas;
    }
}