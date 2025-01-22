package com.challengealura.literalura_challenge.principal;

import com.challengealura.literalura_challenge.model.ApiResponse;
import com.challengealura.literalura_challenge.model.Author;
import com.challengealura.literalura_challenge.model.Books;
import com.challengealura.literalura_challenge.model.BooksData;
import com.challengealura.literalura_challenge.repository.AuthorRepository;
import com.challengealura.literalura_challenge.repository.BooksRepository;
import com.challengealura.literalura_challenge.service.APIConsumption;
import com.challengealura.literalura_challenge.service.ConvertData;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.awt.print.Book;
import java.util.*;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books?search=";
    private APIConsumption consumoAPI = new APIConsumption();
    private ConvertData conversor = new ConvertData();
    private Scanner sc = new Scanner(System.in);
    private List<Books>  booksData = new ArrayList<>();
    private AuthorRepository authorRepository;
    private BooksRepository booksRepository;

    public Principal(AuthorRepository authorRepository, BooksRepository booksRepository) {
        this.authorRepository = authorRepository;
        this.booksRepository = booksRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                
                **************************************************
                         ***   LITERALURA CHALLENGE   ***
                **************************************************
                
                1 - Agregar libro por nombre
                2 - Buscar libro por nombre
                3 - Buscar libro por idioma
                4 - Buscar autores por nombre
                5 - Buscar autores por año
                6 - Top 20 libros con más descargas
                7 - Libros buscados
                8 - Autores de libros buscados
                
                0 - Salir
                
                ************************************************
                         ***   INGRESE UNA OPCIÓN   ***
                ************************************************
                """;

            try {
                System.out.println(menu);
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar una opción correcta.");
                sc.next();
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarLibro();
                    break;
                case 2:
                    buscarLibroPorNombre();
                    break;
                case 3:
                    buscarLibroPorIdioma();
                    break;
                case 4:
                    buscarAutoresPorNombre();
                    break;
                case 5:
                    buscarAutoresPorAnio();
                    break;
                case 6:
                    top20LibrosConMasDescargas();
                    break;
                case 7:
                    librosBuscados();
                    break;
                case 8:
                    autoresDeLibrosBuscados();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }

        }
    }


    private Books datosLibro() {
        System.out.println("Ingresar el nombre del libro: ");
        var nombreLibro = sc.nextLine().toLowerCase();
        String url = URL_BASE + nombreLibro.replace(" ", "%20");
        System.out.println("URL generada: " + url);

        var json = consumoAPI.obtenerDatos(url);

        if (json == null || json.isBlank()) {
            System.out.println("Advertencia: La API no devolvió datos. Verifica la URL o el título del libro.");
            return null;
        }
        System.out.println("Respuesta JSON: " + json);

        ApiResponse datos;
        try {
            datos = conversor.obtenerDatos(json, ApiResponse.class);
        } catch (Exception e) {
            System.out.println("Error al procesar el JSON: " + e.getMessage());
            return null;
        }

        if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
            BooksData libro1 = datos.getResultadoLibros().get(0);
            return new Books(libro1);
        } else {
            System.out.println("No se encontraron resultados.");
            return null;
        }
    }


    private void agregarLibro() {
        Books books = datosLibro();

        if (books == null) {
            System.out.println("Libro no encontrado.");
            return;
        }
        try {
            boolean booksExists = booksRepository.existsByTitulo(books.getTitulo());
            if (booksExists){
                System.out.println("Libro existente en la base de datos");
            } else {
                booksRepository.save(books);
                System.out.println(books.toString());
            }
        } catch (InvalidDataAccessApiUsageException e) {
            System.out.println("No es posible almacenar los datos del libro solicitado.");
        }
    }

    private void buscarLibroPorNombre() {
        System.out.println("Ingrese el nombre del libro que desee buscar: ");
        var titulo = sc.nextLine();
        Books libroBuscado = BooksRepository.findByTituloContainsIgnoreCase(titulo);

        if (libroBuscado != null) {
            System.out.println("El libro solicitado es: " + libroBuscado);
        } else {
            System.out.println("El libro con el nombre de " + titulo + " no fué encontrado.");
        }
    }

    private void buscarLibroPorIdioma() {
        System.out.println("Ingresar el idioma que deseas buscar: \n");
        System.out.println("""
            1 - Libros en español.
            2- Libros en ingles.
            """);
        var idioma = sc.nextLine();
        List<Books> librosPorIdioma = booksRepository.findByIdioma(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se han encontrado libros en la biblioteca.");
        } else {
            System.out.println("Libros en " + idioma + ":");
            librosPorIdioma.forEach(System.out::println);
        }
    }

    private void buscarAutoresPorNombre() {
        List<Author> authors = authorRepository.findAll();

        if (authors.isEmpty()) {
            System.out.println("No se han encontrado autores en la biblioteca.");
        }else {
            System.out.println("Autores de la biblioteca: \n");
            Set<String> autores = new HashSet<>();
            authors.forEach(author -> autores.add(author.getNombre()));
            System.out.println("author.getNombre() + \n");
        }
    }

    private void buscarAutoresPorAnio() {
        System.out.println("Ingresa el año que deseas buscar: \n");
        var anioBuscado = sc.nextInt();
        sc.nextLine();
        List<Author> authors = authorRepository.findByFechaNacimientoLessThanOrFechaFallecimientoGreaterThanEqual(anioBuscado, anioBuscado);

        if (authors.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anioBuscado + "indicado.");
        }else {
            System.out.println("Autores vivos en el año " + anioBuscado + " son :");
            Set<String> autores = new HashSet<>();

            for (Author author : authors) {
                if (author.getFechaNacimiento() != null && author.getFechaFallecimiento() != null) {
                    if (author.getFechaNacimiento() <= anioBuscado && author.getFechaFallecimiento() >= anioBuscado) {
                        if (autores.add(author.getNombre())) {
                            System.out.println(author.getNombre());
                        }
                    }
                }
            }
        }
    }

    private void top20LibrosConMasDescargas() {
        Pageable top20 = PageRequest.of(0, 20); // Página 0, 20 resultados por página
        List<Books> topBooks = booksRepository.findTop20ByCantidadDescargas(top20);

        if (!topBooks.isEmpty()) {
            int index = 1;
            for (Books book : topBooks) {
                System.out.printf("Libro %d: %s Autor: %s Descargas: %d\n",
                        index,
                        book.getTitulo(),
                        book.getAuthor() != null ? book.getAuthor().getNombre() : "Desconocido",
                        book.getCantidadDescargas());
                index++;
            }
        } else {
            System.out.println("No se encontraron libros con descargas.");
        }
    }

    private void librosBuscados() {
        System.out.println("Ingrese el término de búsqueda: ");
        var termino = sc.nextLine();
        List<Books> libros = booksRepository.findByTituloContainingIgnoreCaseOrAuthorNombreContainingIgnoreCase(termino, termino);

        if (libros.isEmpty()) {
            System.out.println("No se han encontrado libros con el termino de búsqueda '" + termino + "'.");
        } else {
            System.out.println("Libros que coinciden con el termino de búsqueda '" + termino + "':");
            libros.forEach(System.out::println);
        }
    }


    private void autoresDeLibrosBuscados() {
        System.out.println("Ingrese el nombre del autor que desea buscar: ");
        var nombreBuscado = sc.nextLine();
        Optional<Author> author = authorRepository.findFirstByNombreContainsIgnoreCase(nombreBuscado);

        if (nombreBuscado != null) {
            System.out.println("\nEl autor buscado fué: " + author.get().getNombre());

        } else {
            System.out.println("\nEl autor con el nombre '" + nombreBuscado + "' no se encontró.");
        }
    }
}