package com.acpurrinos.literalura.principal;

import com.acpurrinos.literalura.model.Datos;
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
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE="https://gutendex.com/books/?search=";
    private final String SEPARADOR="%20";

    private ConvierteDatos conversor = new ConvierteDatos();



        public void inicia() {
            System.out.println("Ingresar título");
            String titulo = teclado.nextLine().trim(); // Asegúrate de eliminar espacios en blanco innecesarios
            System.out.println("Ingresar autor");
            String autor = teclado.nextLine().trim(); // Asegúrate de eliminar espacios en blanco innecesarios

            String jsonTitulo = consumoAPI.obtenerDatos(URL_BASE + SEPARADOR + titulo);
            String jsonAutor = consumoAPI.obtenerDatos(URL_BASE + autor + SEPARADOR);

            System.out.println("JSON de Título:");
            System.out.println(jsonTitulo);
            System.out.println("JSON de Autor:");
            System.out.println(jsonAutor);


            var datos = conversor.obtenerDatos(jsonTitulo, Datos.class);
            Optional<DatosLibro> libroBuscado = datos.libros().stream()
                    .findFirst();
            if(libroBuscado.isPresent()){
                System.out.println(
                        "\n----- LIBRO -----" +
                                "\nTitulo: " + libroBuscado.get().titulo() +
                                "\nAutor: " + libroBuscado.get().autor().stream()
                                .map(a -> a.nombre()).limit(1).collect(Collectors.joining())+
                                "\nIdioma: " + libroBuscado.get().idiomas().stream().collect(Collectors.joining()) +
                                "\nNumero de descargas: " + libroBuscado.get().numeroDeDescargas() +
                                "\n-----------------\n"
                );}


            System.out.println("DATOS CONVERTIDOS:" +datos);
            System.out.println("ok");

            // Convertir JSON a objeto DatosLibro



        }}

//arreglar con un switch



        /*
        System.out.println("Ingresar titulo");
        var titulo = teclado.nextLine();
        System.out.println("Ingresar un autor");
        var autor = teclado.nextLine();


        var jsonTitulo = consumoAPI.obtenerDatos(URL_BASE+SEPARADOR+titulo);
        var jsonAutor = consumoAPI.obtenerDatos(URL_BASE+autor+SEPARADOR);
        System.out.println(jsonTitulo);
        System.out.println(jsonAutor);

        var datos = conversor.obtenerDatos(jsonTitulo, DatosLibro.class);
        System.out.println(datos);
        System.out.println("ok");
    */
