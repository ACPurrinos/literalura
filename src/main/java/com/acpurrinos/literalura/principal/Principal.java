package com.acpurrinos.literalura.principal;

import com.acpurrinos.literalura.model.Datos;
import com.acpurrinos.literalura.model.DatosAutor;
import com.acpurrinos.literalura.model.DatosLibro;
import com.acpurrinos.literalura.model.Libro;
import com.acpurrinos.literalura.service.ConsumoAPI;
import com.acpurrinos.literalura.service.ConvierteDatos;
import com.fasterxml.jackson.core.ObjectCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private final List<DatosLibro> librosEncontrados = new ArrayList<>();
    private final List<DatosAutor> autoresEncontrados = new ArrayList<>();

    public void inicia() {
        int opcion = -100;

        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(teclado.nextLine());
                switch (opcion) {
                    case 1:
                        buscaLibroPorTitulo();
                        break;
                    case 2:
                        listaLibrosEncontrados();
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
                ************************************
                          LITERALURA - BIENVENIDO
                ************************************
                Elija una opción:
                
                1 --> Buscar libro por título.
                2 --> Listar libros buscados.
                3 --> Listar autores de los libros buscados.
                4 --> Listar autores vivos en un determinado año.
                
                0 -> SALIR
                
                """);
    }

    public void buscaLibroPorTitulo() {
        System.out.println("Ingrese el título del libro o parte de él:");
        String titulo = teclado.nextLine().trim().toLowerCase();

        String jsonTitulo = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));
        Datos datos = conversor.obtenerDatos(jsonTitulo, Datos.class);

        Optional<DatosLibro> libroBuscado = datos.libros().stream().findFirst();
        if (libroBuscado.isPresent()) {
            librosEncontrados.add(libroBuscado.get());
            autoresEncontrados.addAll(libroBuscado.get().autor());
            System.out.println("\nLibro encontrado:\n" + libroBuscado.get());
        } else {
            System.out.println("No se encontró ningún libro con ese título.");
        }
    }

    public void listaLibrosEncontrados() {
        System.out.println("LISTADO DE LIBROS BUSCADOS Y ENCONTRADOS:");
        librosEncontrados.forEach(libro -> {
            System.out.println(
                    "----- LIBRO -----\n" +
                            "Titulo: " + libro.titulo() + "\n" +
                            "Autores: " + obtenerNombresAutores(libro.autor()) + "\n" +
                            "Idiomas: " + libro.idiomas().stream().collect(Collectors.joining(", ")) + "\n" +
                            "Número de descargas: " + libro.numeroDeDescargas() + "\n" +
                            "-----------------\n"
            );
        });
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
