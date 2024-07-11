package com.acpurrinos.literalura.principal;

import com.acpurrinos.literalura.model.*;
import com.acpurrinos.literalura.repository.AutorRepository;
import com.acpurrinos.literalura.repository.LibroRepository;
import com.acpurrinos.literalura.service.ConsumoAPI;
import com.acpurrinos.literalura.service.ConvierteDatos;

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
private AutorRepository autorRepositorio;
  public Principal(LibroRepository repository, AutorRepository autorRepository) {
      this.repositorio = repository;
      this.autorRepositorio=autorRepository;

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
                        break;
                    case 2:
                        mostrarLibrosBuscados();
                        break;
                    case 3:
                        muestraNombresDeAutores();
                        break;
                    case 4:
                        listaAutoresVivos();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
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
                5 --> Buscar libros registrados por idioma.
                0 -> SALIR.
                """);
    }

    private Datos getDatos() {
        System.out.println("Ingrese el título del libro o parte de él:");
        String titulo = teclado.nextLine().trim().toLowerCase();

        String jsonTitulo = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));
        return conversor.obtenerDatos(jsonTitulo, Datos.class);
    }

    private void buscarLibroPorTitulo() {
        Datos datosLibro = getDatos();
        Optional<DatosLibro> libroBuscado = datosLibro.libros().stream().findFirst();


        if (libroBuscado.isPresent()) {
            DatosLibro datosLibroEncontrado = libroBuscado.get();
            Libro libro = new Libro(datosLibroEncontrado);

                // Guardar autores asociados al libro
                if (datosLibroEncontrado.autor() != null) {
                    List<Autor> autores = new ArrayList<>();
                    for (DatosAutor datosAutor : datosLibroEncontrado.autor()) {
                        Autor autor = new Autor(datosAutor);
                        autor.setLibro(libro); // Asignar el libro al autor
                        autores.add(autor);
                    }
                    libro.setAutor(autores); // Asignar autores al libro
                  System.out.println("--->"+autores);
                }
            // Guardar el libro en la base de datos
            repositorio.save(libro);

            System.out.println(datosLibroEncontrado.autor()+"AAAAA");
            // Agregar el libro a la lista de libros encontrados
            librosEncontrados.add(datosLibroEncontrado);

            // Mostrar el libro encontrado en consola
            System.out.println("***************************");
            System.out.println("1 lo q se guarda en bs"+libro);
            System.out.println(datosLibroEncontrado);
            System.out.println("2 lo q llega por getDatos "+libroBuscado);
        } else {
            System.out.println("No se encontró ningún libro con ese título.");
        }
    }

    private void mostrarLibrosBuscados() {
        List<Libro> libros = repositorio.findAll();
        System.out.println(libros);

    }

    private void muestraNombresDeAutores() {
        List<Autor> autores = autorRepositorio.findAll();
        System.out.println();
        autores.forEach(l-> System.out.println(
                "Autor: " + l.getNombre() + ", Fecha de nacimiento: " + l.getFechaDeNacimiento()+ ", Fecha de fallecimiento: " + l.getFechaDeFallecimiento() +
                        "\nLibros: " + l.getLibro()
        ));
    }

    public void listaAutoresVivos() {
        System.out.println("Introduzca un año para listar autores vivos en ese año");
        try {
            String input = teclado.nextLine().trim();  // Obtener la entrada del usuario y quitar espacios en blanco al inicio y al final
            if (input.isEmpty()) {
                throw new NumberFormatException("La entrada no puede estar vacía");
            }
            var fecha = Integer.valueOf(input);
            List<Autor> autores = autorRepositorio.buscarAutoresVivos(fecha);
            if (!autores.isEmpty()) {
                System.out.println("Autores vivos en el año " + fecha + ":");
                System.out.println("---------------------------------------");
                autores.forEach(a -> {
                    System.out.println("Autor: " + a.getNombre()+ ", Fecha de nacimiento: " + a.getFechaDeNacimiento()+", Fecha de fallecimiento: " + a.getFechaDeFallecimiento());
                    System.out.println("Libros: " + a.getLibro().getTitulo());
                    System.out.println(" ");
                });
            } else {
                System.out.println("No hay autores vivos registrados en la Base de Datos para el año " + fecha);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Introduce un año válido.");
        }
    }

    public void listarLibrosPorIdioma() {
    var menu = """
                Elija un idioma para ver libros registrados en ese idioma:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """;
    System.out.println(menu);
    var idiomaAPI = teclado.nextLine().trim().toLowerCase(); // Convertir a minúsculas y quitar espacios en blanco

    try {
        Idioma idiomaSeleccionado = Idioma.fromIdiomaAPI(idiomaAPI);
        List<Libro> libros = repositorio.buscarLibrosPorIdiomaOrdenadosPorTitulo(idiomaSeleccionado);
        long cantidadLibros = repositorio.contarLibrosPorIdioma(idiomaSeleccionado);
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma.");
        } else {
            System.out.println();
            System.out.println("Cantidad de libros en " + idiomaSeleccionado + ": " + cantidadLibros);
            libros.forEach(l -> System.out.println(
                            l.getTitulo() +", Idioma: " + l.getIdiomas() +", Numero de descargas: " + l.getNumeroDeDescargas() + ", Autores: " + l.getAutor()  // Método para obtener los nombres de los autores como cadena

            ));
        }
    } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }
}

}


