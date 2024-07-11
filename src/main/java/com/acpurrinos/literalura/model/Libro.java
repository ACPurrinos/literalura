package com.acpurrinos.literalura.model;

import com.acpurrinos.literalura.repository.LibroRepository;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Entity //es para que jpa sepa q esta es una tabla de la bd
@Table(name = "libros") // esto le indica con que nombre se va a guardar en la bd
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
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor;

    private List<String> categoria;
    private Long idAPI;

    public Libro(){

    }//PARA USAR JPA TODAS LAS CLASES - ENTIDADES TIENEN QUE TENER UN CONSTRUCTOR PREDETERMINADO

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
        this.idAPI = datosLibro.id();
        if (datosLibro.categoria() != null && !datosLibro.categoria().isEmpty()) {
            this.categoria = datosLibro.categoria();
        } else {
            this.categoria = List.of("Desconocida"); // Asignar una lista con "Desconocida" si está vacía o nula
        }

        if (!datosLibro.idiomas().isEmpty()) {
            this.idiomas = Idioma.fromString(datosLibro.idiomas().get(0));
        } else {
            throw new IllegalArgumentException("La lista de idiomas está vacía.");
        };

        if (datosLibro.autor() != null) {
            this.autor = new ArrayList<>();
            for (DatosAutor datosAutor : datosLibro.autor()) {
                Autor autor = new Autor(datosAutor);
                addAutor(autor); // Agregar autor con relación bidireccional establecida
            }


    }}


        public void addAutor(Autor autor) {
            if (autor != null) {
                if (this.autor == null) {
                    this.autor = new ArrayList<>();
                }
                this.autor.add(autor);
                autor.setLibro(this); // Establecer el libro en el autor
            }
        }


    @Override
    public String toString() {
        String categoriaPrincipal = categoria != null && !categoria.isEmpty() ? categoria.get(0) : "Sin categoría";


        return String.format(
                "titulo=%s,  idioma=%s, categoria=%s, cantidad de descargas=%d, id=%d\n",
                titulo.toUpperCase(),
                //autor.toString(),

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

    public List<Autor> getAutor() {
        return autor;
    }



    public void setAutor(List<Autor> autor) {
        autor.forEach(a->a.setLibro(this));
        this.autor = autor;}


}


