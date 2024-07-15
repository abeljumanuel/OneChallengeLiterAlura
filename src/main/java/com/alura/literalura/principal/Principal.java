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
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> librosList;
    private List<Autor> autoresList;
    private Optional<Autor> autorBuscado;
    private Optional<Libro> libroBuscado;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    Elija la opción a través de su número;
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);

            try {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Consume the newline character left by nextInt()
            } catch (InputMismatchException ime) {
                System.out.println("Opción inválida. Por favor, ingrese un número entero.");
                teclado.nextLine(); // Clear the invalid input from the scanner
                continue; // Restart the loop to show the menu again
            }

            if (opcion < 0 || opcion > 5) {
                System.out.println("Opción inválida. Por favor, elija una opción válida.");
                continue; // Restart the loop to show the menu again
            }

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
                case 0:
                    System.out.println("Saliendo del Sistema ...");
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
            libroBuscado = libroRepository.getLibroByTitulo(datosBook.titulo());

            if (libroBuscado.isPresent()) {
                System.out.println("""
            
            El libro ya existe en Base de Datos
            -------------------""");
            } else {
                Autor autor;
                if (autorBuscado.isPresent()){
                    autor = autorBuscado.get();
                } else {
                    autor = new Autor(datosBook.autores().get(0));
                    autorRepository.save(autor);
                }

                Libro libro = new Libro(datosBook);
                libro.setAutor(autor);

                libroRepository.save(libro);
                System.out.println(datos);
            }

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
        Set<String> validIdiomas = Set.of("es", "en", "fr", "pt");
        String idioma;
        do {
            System.out.println("""
                Ingresa el idioma para buscar los libros:
                es - español
                en - inglés
                fr - frances
                pt - portugues
            """);
            idioma = teclado.nextLine();
            if (!validIdiomas.contains(idioma)) {
                System.out.println("Idioma inválido. Por favor, ingresa una opción válida (es, en, fr, pt).");
            }
        } while (!validIdiomas.contains(idioma));

        librosList = libroRepository.findLibrosByIdiomaContains(idioma);

        librosList.stream()
                .sorted(Comparator.comparing(Libro::getNumero_de_descargas).reversed())
                .forEach(System.out::println);
    }
}

