package com.acpurrinos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(

        @JsonAlias("title") String titulo,
        @JsonAlias("download_count")Integer numeroDeDescargas,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("authors") List<DatosAutor> autor
        ) {
}
