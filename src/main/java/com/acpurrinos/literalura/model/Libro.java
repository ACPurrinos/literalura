package com.acpurrinos.literalura.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Libro {
private String titulo;
private Integer numeroDeDescargas;
private String Idiomas;
private DatosAutor autor;

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        try {
            this.numeroDeDescargas = Integer.valueOf(datosLibro.numeroDeDescargas());
        } catch (NumberFormatException ex){
            this.numeroDeDescargas = 0;
        }


    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public String getIdiomas() {
        return Idiomas;
    }

    public void setIdiomas(String idiomas) {
        Idiomas = idiomas;
    }

    public DatosAutor getAutor() {
        return autor;
    }

    public void setAutor(DatosAutor autor) {
        this.autor = autor;
    }
}
