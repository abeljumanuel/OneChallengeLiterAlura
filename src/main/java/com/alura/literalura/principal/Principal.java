package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosBook;
import com.alura.literalura.model.Gutendex;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosBook> datosBookList = new ArrayList<>();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> librosList;
    private List<Autor> autoresList;
    private Optional<Autor> autorBuscado;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opción a través de su número;
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosDurante();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private Gutendex getDatosLibro() {
        System.out.println("Ingresa el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        String url = "";
        try {
            url = URL_BASE + URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Libro no encontrado");
        }
        var json = consumoApi.obtenerDatos(url);
        return conversor.obtenerDatos(json, Gutendex.class);
    }

    private void buscarLibroPorTitulo() {
        Gutendex datos = getDatosLibro();
        try {
            DatosBook datosBook = datos.datosBook().get(0);
            autorBuscado = autorRepository.getAutorByName(datosBook.autores().get(0).name());

            Autor autor;
            if (autorBuscado.isPresent()){
                autor = autorBuscado.get();
            } else {
                autor = new Autor(datosBook.autores().get(0));
                autorRepository.save(autor);
            }

            Libro libro = new Libro(datosBook);
            libro.setAutor(autor);
            System.out.println("Lo que se va a guardar : \n" + libro);
            libroRepository.save(libro);
            System.out.println(datos);
        } catch (Exception e) {
            System.out.println("""
            
            Libro no encontrado
            -------------------""");
        }
    }

    private void mostrarLibrosRegistrados() {
        librosList = libroRepository.findAll();

        librosList.stream()
                .sorted(Comparator.comparing(Libro::getNumero_de_descargas).reversed())
                .forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        autoresList = autorRepository.findAll();


        autoresList.stream()
                .sorted(Comparator.comparing(Autor::getDeathyear).reversed())
                .forEach(System.out::println);
    }

    private void mostrarAutoresVivosDurante() {
        System.out.println("Ingresa el año vivo de autor(es) que desea buscar");
        var yearAlive = teclado.nextInt();
        teclado.nextLine();
        autoresList = autorRepository.findByBirthyearLessThanEqualAndDeathyearGreaterThanEqual(yearAlive, yearAlive);


        autoresList.stream()
                .sorted(Comparator.comparing(Autor::getDeathyear).reversed())
                .forEach(System.out::println);
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("""
        Ingresa el idioma para buscar los libros:
        es - español
        en - inglés
        fr - frances
        pt - portugues
        """);
        var idioma = teclado.nextLine();
        librosList = libroRepository.findLibrosByIdiomaContains(idioma);

        librosList.stream()
                .sorted(Comparator.comparing(Libro::getNumero_de_descargas).reversed())
                .forEach(System.out::println);
    }
}

