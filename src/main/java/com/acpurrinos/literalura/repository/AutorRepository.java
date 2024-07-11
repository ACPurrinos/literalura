package com.acpurrinos.literalura.repository;

import com.acpurrinos.literalura.model.Autor;
import com.acpurrinos.literalura.model.Idioma;
import com.acpurrinos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT l.id AS libro_id, l.titulo AS titulo_libro, a.nombre AS nombre_autor FROM Libro l JOIN Autor a")
        //@Query("SELECT a.id AS autor_id, a.nombre AS nombre_autor FROM Autor a JOIN a.libro l")
    List<Autor> findLibrosAndAutores();

    @Query("SELECT a FROM Autor a WHERE a.fechaDeFallecimiento > :fecha")
    List<Autor> buscarAutoresVivos(Integer fecha);


}
