package com.acpurrinos.literalura.principal;

import com.acpurrinos.literalura.model.Datos;
import com.acpurrinos.literalura.model.DatosAutor;
import com.acpurrinos.literalura.model.DatosLibro;
import com.acpurrinos.literalura.model.Libro;
import com.acpurrinos.literalura.repository.LibroRepository;
import com.acpurrinos.literalura.service.ConsumoAPI;
import com.acpurrinos.literalura.service.ConvierteDatos;
import com.fasterxml.jackson.core.ObjectCodec;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private final List<DatosLibro> librosEncontrados = new ArrayList<>();
    private final List<DatosAutor> autoresEncontrados = new ArrayList<>();
private LibroRepository repositorio;
    public Principal(LibroRepository repository) {
        this.repositorio=repository;
    }

    public void inicia() {
        int opcion = -100;

        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(teclado.nextLine());
                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        //buscaLibroPorTitulo();
                        break;
                    case 2:
                        //listaLibrosEncontrados();
                        mostrarLibrosBuscados();
                        break;
                    case 3:
                        muestraNombresDeAutores();
                        break;
                    case 4:
                        listaAutoresVivos();
                        break;
                    case 0:
                        System.out.println("Gracias por usar Literalura.");
                        System.out.println("PROGRAMA FINALIZADO.");
                        break;
                    default:
                        System.out.println("¡Opción no válida!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida: " + e.getMessage());
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("""
                
                Elija una opción:
                1 --> Buscar libro por título.
                2 --> Listar libros buscados.
                3 --> Listar autores de los libros buscados.
                4 --> Listar autores vivos en un determinado año.
                0 -> SALIR.
                """);
    }

    private Datos getDatos(){
        System.out.println("Ingrese el título del libro o parte de él:");
        String titulo = teclado.nextLine().trim().toLowerCase();

        String jsonTitulo = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));
        Datos datos = conversor.obtenerDatos(jsonTitulo, Datos.class);

        return datos;
    }

    private void buscarLibroPorTitulo(){
        Datos datosLibro = getDatos();

        Optional<DatosLibro> libroBuscado = datosLibro.libros().stream().findFirst();
        if (libroBuscado.isPresent()) {
            DatosLibro libro = libroBuscado.get();
            Libro l = new Libro(libro);
            repositorio.save(l);
            librosEncontrados.add(libro);
            autoresEncontrados.addAll(libro.autor());
            System.out.println(libro);
            System.out.println(l);
            //System.out.println(librosEncontrados.stream().map(l->new Libro(l)).collect(Collectors.toList()));
} else {
            System.out.println("No se encontró ningún libro con ese título.");
        }

    }


    private void mostrarLibrosBuscados(){
        List<Libro> libros = repositorio.findAll();
        System.out.println(libros);


       /* List<Libro> libros = new ArrayList<>();
        libros = librosEncontrados.stream().map(l->new Libro(l)).collect(Collectors.toList());
        System.out.println(libros);
*/

    }

    private String obtenerNombresAutores(List<DatosAutor> autores) {
        return autores.stream()
                .map(DatosAutor::nombre)
                .collect(Collectors.joining(", "));
    }

    public void muestraNombresDeAutores() {
        System.out.println("LISTADO DE AUTORES DE LOS LIBROS BUSCADOS:");
        autoresEncontrados.forEach(autor -> {
            String infoAutor = String.format("Nombre: %s, Año de nacimiento: %d, Año de fallecimiento: %d",
                    autor.nombre(), autor.fechaDeNacimiento(), autor.fechaDeFallecimiento());
            System.out.println(infoAutor);
        });
    }

    public void listaAutoresVivos() {
        System.out.println("LISTADO DE AUTORES VIVOS EN UN DETERMINADO AÑO:");
        // Implementar lógica para listar autores vivos en un año específico
    }
}
/*
    public void buscaLibroPorTitulo() {
        System.out.println("Ingrese el título del libro o parte de él:");
        String titulo = teclado.nextLine().trim().toLowerCase();

        String jsonTitulo = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));
        Datos datos = conversor.obtenerDatos(jsonTitulo, Datos.class);


        Optional<DatosLibro> libroBuscado = datos.libros().stream().findFirst();
        if (libroBuscado.isPresent()) {
            DatosLibro libro = libroBuscado.get();
            librosEncontrados.add(libro);
            autoresEncontrados.addAll(libro.autor());

            // Formatear la salida del libro encontrado
            System.out.println("\nLibro encontrado:");
            System.out.println("Título: " + libro.titulo());
            System.out.println("Autores: " + obtenerNombresAutores(libro.autor()));
            System.out.println("Idiomas: " + libro.idiomas().stream().collect(Collectors.joining(", ")));
            System.out.println("Número de descargas: " + libro.numeroDeDescargas());
           // librosEncontrados.add(libroBuscado.get());
           // autoresEncontrados.addAll(libroBuscado.get().autor());
           // System.out.println("\nLibro encontrado:\n" + libroBuscado.get());
           // System.out.println(libroBuscado.);
        } else {
            System.out.println("No se encontró ningún libro con ese título.");
        }
    }*/
/*
    public void listaLibrosEncontrados() {
        System.out.println("LISTADO DE LIBROS BUSCADOS Y ENCONTRADOS:");
        librosEncontrados.forEach(libro -> {
            System.out.println(
                    "título=" + libro.titulo().toUpperCase() + "; " +
                            " autores=" + obtenerNombresAutores(libro.autor()).toUpperCase() + ";" +
                            " Idiomas=" + libro.idiomas().stream().collect(Collectors.joining(", ")).toUpperCase() + "; " +
                            " Número de descargas=" + libro.numeroDeDescargas() + "; "

            );
        });
    }*/

/*
            // Formatear la salida del libro encontrado
            System.out.println("\nLibro encontrado:");
            System.out.println("Título: " + libro.titulo());
            System.out.println("Autores: " + obtenerNombresAutores(libro.autor()));
            System.out.println("Idiomas: " + libro.idiomas().stream().collect(Collectors.joining(", ")));
            System.out.println("Número de descargas: " + libro.numeroDeDescargas());
            System.out.println("Categoria: "+libro.categoria().stream().findFirst().orElse("sin categorizar"));
        */