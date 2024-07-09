package com.acpurrinos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(

        @JsonAlias("title") String titulo,
        @JsonAlias("download_count")Integer numeroDeDescargas,
        @JsonAlias("languages") List<String> idiomas,
        //@JsonAlias("languages") String idiomas,
        @JsonAlias("authors") List<DatosAutor> autor,

        @JsonAlias("subjects") List<String> categoria,

        @JsonAlias("id") Long id
        ) {
        public Optional<DatosAutor> getAutor() {
                return autor.stream().findFirst();
        }
}
