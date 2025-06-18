package com.alura.Literalura.principal;

import com.alura.Literalura.model.Autor;
import com.alura.Literalura.model.DatosLibro;
import com.alura.Literalura.model.DatosRespuesta;
import com.alura.Literalura.model.Libro;
import com.alura.Literalura.repository.AutorRepository;
import com.alura.Literalura.repository.LibroRepository;
import com.alura.Literalura.service.ConsumoApi;
import com.alura.Literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private LibroRepository repositorio;
    private AutorRepository autorRepository;
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepository = autorRepository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idioma
                    
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosPorAño();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;

                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Opción inválida: por favor ingrese un número valido del menu");
                teclado.nextLine();
            }
        }

    }


    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        String nombreLibro = teclado.nextLine();
        String json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        if (json == null) {
            System.out.println("No se encontraron datos para la búsqueda.");
            return null;
        }
        try {
            DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);
            return respuesta.resultados().isEmpty() ? null : respuesta.resultados().get(0);
        } catch (RuntimeException e) {
            System.out.println("Error al procesar el JSON: " + e.getMessage());
            return null;
        }
    }

    private void buscarLibro() {
        DatosLibro datos = getDatosLibro();
        if (datos == null) {
            System.out.println("No se pudo obtener el libro.");
            return;
        }
        Libro libroExistente = repositorio.findByTitulo(datos.titulo());
        if (libroExistente != null) {
            System.out.println("El libro '" + datos.titulo() + "' ya está registrado en la base de datos.");
            System.out.println(datos);
            return;
        }

        Autor autor = null;
        if (!datos.autores().isEmpty()) {
            String nombreAutor = datos.autores().get(0).nombre();
            autor = autorRepository.findByNombre(nombreAutor);
            if (autor == null) {
                autor = new Autor();
                autor.setNombre(nombreAutor);
                autor.setFechaDeNacimiento(datos.autores().get(0).fechaDeNacimiento());
                autor.setFechaDeFallecimiento(datos.autores().get(0).fechaDeFallecimiento());
                autor = autorRepository.save(autor);
            }
        }
        Libro libro = new Libro(datos);
        libro.setAutor(autor);
        repositorio.save(libro);
        System.out.println(datos);
    }

    private void listarLibros() {
        List<Libro> libros = repositorio.findAll();
        libros.forEach(e -> System.out.printf("libro: %s - Autor: %s - idioma: %s - Numero de descargas: %s\n",
                e.getTitulo(), e.getAutor(), e.getIdioma(), e.getDescargas()));
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(e -> System.out.printf("autor: %s - fecha de nacimiento: %s - fecha de fallecimiento: %s - libros: %s\n",
                e.getNombre(), e.getFechaDeNacimiento(), e.getFechaDeFallecimiento(), e.getLibros().toString()));
    }

    private void listarAutoresVivosPorAño() {
        System.out.println("Ingrese el año:");
        try {
            long anio = teclado.nextLong();
            teclado.nextLine();
            if (anio < 0 || anio > 2025) {
                System.out.println("Por favor, ingrese un año válido entre 0 y 2025");
                return;
            }
            List<Autor> autores = autorRepository.findByVivosEnAnio(anio);
            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
            } else {
                autores.forEach(e -> System.out.printf("autor: %s - fecha de nacimiento: %s - fecha de fallecimiento: %s - libros: %s\n",
                        e.getNombre(), e.getFechaDeNacimiento(), e.getFechaDeFallecimiento(), e.getLibros().toString()));
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Por favor, ingrese un año válido");
            teclado.nextLine();
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (por ejemplo, 'en' para inglés, 'es' para español):");
        String idioma = teclado.nextLine().trim();
        List<Libro> libros = repositorio.encontrarPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idioma);
        } else {
            libros.forEach(e -> System.out.printf("libro: %s - Autor: %s - idioma: %s - Numero de descargas: %s\n",
                    e.getTitulo(), e.getAutor(), e.getIdioma(), e.getDescargas()));
        }
    }



}
