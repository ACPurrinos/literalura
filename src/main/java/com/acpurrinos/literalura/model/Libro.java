package com.acpurrinos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Entity //es para que jpa sepa q esta es una tabla de la bd
@Table (name = "Libros") // esto le indica con que nombre se va a guardar en la bd
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //se genera como clave primaria  y autoincremental
    private Long id;

    @Column(unique = true) //no admite repetidos
    private String titulo;
    private Integer numeroDeDescargas;
    // private List<String> idiomas;

    @Enumerated(EnumType.STRING)
    private Idioma idiomas;
    @Transient
    private Optional<DatosAutor> autor;

    //private DatosAutor autor;

    @Transient
    private List<String> categoria;
    private Long idAPI;

    public Libro(){

    }//PARA USAR JPA TODAS LAS CLASES - ENTIDADES TIENEN Q TENER UN CONSTRUCTOR PREDETERMINADO

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = datosLibro.autor().stream().findFirst();
        //this.autor=datosLibro.autor();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
        this.idAPI = datosLibro.id();
        if (datosLibro.categoria() != null && !datosLibro.categoria().isEmpty()) {
            this.categoria = datosLibro.categoria();
        } else {
            this.categoria = List.of("Desconocida"); // Asignar una lista con "Desconocida" si está vacía o nula
        }
        //this.categoria = datosLibro.categoria();
        //this.idiomas=Idioma.fromString(datosLibro.idiomas());
        if (!datosLibro.idiomas().isEmpty()) {
            this.idiomas = Idioma.fromString(datosLibro.idiomas().get(0));
        } else {
            throw new IllegalArgumentException("La lista de idiomas está vacía.");
        }
    }


    @Override

    public String toString() {
        /*String categoriaPrincipal = categoria.isEmpty() ? "Sin categoría" : categoria.get(0);
        String autorString = autor.map(DatosAutor::nombre).orElse("Desconocido");
        return
                "titulo=" + titulo.toUpperCase() +
                        ",  autor=" + autorString.toUpperCase() +
                        ",  idioma=" + idiomas +
                        ",  categoria=" + categoriaPrincipal.toUpperCase() +
                        ",  cantidad de descargas=" + numeroDeDescargas +
                        ",  id=" + idAPI +
                        "\n"
                ;
    }*/

            String categoriaPrincipal = categoria != null && !categoria.isEmpty() ? categoria.get(0) : "Sin categoría";

        String autorString = autor != null && autor.isPresent() ? autor.get().nombre() : "Desconocido";
            //String autorString = autor.map(DatosAutor::nombre).orElse("Desconocido");
            return String.format(
                    "titulo=%s, autor=%s, idioma=%s, categoria=%s, cantidad de descargas=%d, id=%d\n",
                    titulo.toUpperCase(),
                    autorString.toUpperCase(),
                    idiomas,
                    categoriaPrincipal.toUpperCase(),
                    numeroDeDescargas,
                    idAPI
            );
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Idioma getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idioma idiomas) {
        this.idiomas = idiomas;
    }

    public Optional<DatosAutor> getAutor() {
        return autor;
    }

    public void setAutor(Optional<DatosAutor> autor) {
        this.autor = autor;
    }

    public List<String> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<String> categoria) {
        this.categoria = categoria;
    }

    public Long getIdAPI() {
        return idAPI;
    }

    public void setIdAPI(Long idAPI) {
        this.idAPI = idAPI;
    }
}
