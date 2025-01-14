package com.uni.Literauni.Menu;

import com.uni.Literauni.modelo.*;
import com.uni.Literauni.repository.AutorRepository;
import com.uni.Literauni.repository.LibroRepository;
import com.uni.Literauni.service.ClienteApi;
import com.uni.Literauni.service.ConvierteDatos;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;


public class LibroMenu {
    private Scanner teclado = new Scanner(System.in);
    private ClienteApi clienteApi = new ClienteApi();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public LibroMenu(LibroRepository libroRepositorio, AutorRepository autorRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("""
                    1. Buscar libro por título
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    6. Salir
                    """);
            System.out.print("Elige una opción: ");
            while (!teclado.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                teclado.next();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }


    private Autor obtenerORegistrarAutor(DatoAutor datoAutor) {
        var autoresExistentes = autorRepositorio.findByNombre(datoAutor.nombre());
        if (autoresExistentes.isPresent()) {
            return autoresExistentes.get();
        }
        var nuevoAutor = new Autor(datoAutor);
        return autorRepositorio.saveAndFlush(nuevoAutor);
    }


    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el título del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();
        var json = clienteApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        var datos = conversor.obtenerDatos(json, DatoResultado.class);

        if (datos.libros().isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }

        var primerDatoLibro = datos.libros().get(0);

        if (libroRepositorio.existsByTitulo(primerDatoLibro.titulo())) {
            System.out.println("El libro '" + primerDatoLibro.titulo() + "' ya está registrado.");
            return;
        }

        var autores = primerDatoLibro.autores().stream()
                .map(this::obtenerORegistrarAutor)
                .collect(Collectors.toList());

        var libro = new Libro(primerDatoLibro, new ArrayList<>());
        autores.forEach(libro::addAutor);

        libroRepositorio.save(libro);
        System.out.println(libro);
    }

    private void listarLibros() {
        var libros = libroRepositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        libros.forEach(libro -> System.out.println(libro));
    }

    private void listarAutores() {
        var libros = libroRepositorio.findAll();
        var autores = libros.stream()
                .flatMap(libro -> libro.getAutores().stream())
                .distinct()
                .toList();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }

        autores.forEach(autor -> System.out.println(autor));
    }

    private void listarAutoresVivos() {
        System.out.print("Escribe el año para buscar autores vivos: ");
        int anio = teclado.nextInt();
        teclado.nextLine();

        var libros = libroRepositorio.findAll();
        var autoresVivos = libros.stream()
                .flatMap(libro -> libro.getAutores().stream())
                .filter(autor -> {

                    Integer anioNac = autor.getAnioDeNacimiento();
                    Integer anioFall = autor.getAnioDeFallecimiento();

                    if (anioNac == null) {
                        return false;
                    }

                    if (anioFall == null) {
                        anioFall = 0;
                    }

                    return anioNac <= anio && (anioFall == 0 || anioFall > anio);
                })
                .distinct()
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio + ".");
            return;
        }

        autoresVivos.forEach(autor ->
                System.out.println(autor)
        );
    }

    private void listarLibrosPorIdioma() {
        System.out.print("""
                Ingrese el idioma para buscar los libros:
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """);
        String idioma = teclado.nextLine().toUpperCase();

        var libros = libroRepositorio.findAll();
        var librosPorIdioma = libros.stream()
                .filter(libro ->
                        libro.getIdiomas().stream()
                                .anyMatch(lang -> lang.equalsIgnoreCase(idioma))
                )
                .toList();


        long cantidad = librosPorIdioma.size();

        if (cantidad == 0) {
            System.out.println("No se encontraron libros en el idioma " + idioma + ".");
            return;
        }

        System.out.println("Cantidad de libros en " + idioma + ": " + cantidad);

        librosPorIdioma.forEach(libro -> System.out.println(libro));
    }

}
