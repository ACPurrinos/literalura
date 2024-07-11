package com.acpurrinos.literalura.repository;

import com.acpurrinos.literalura.model.Idioma;
import com.acpurrinos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

   @Query("SELECT l.id AS libro_id, l.titulo AS titulo_libro, a.nombre AS nombre_autor FROM Libro l JOIN Autor a")
   //@Query("SELECT a.id AS autor_id, a.nombre AS nombre_autor FROM Autor a JOIN a.libro l")
    List<Libro> findLibrosAndAutores();

    @Query("SELECT l FROM Libro l WHERE l.idiomas = :idioma")
    List<Libro> buscarLibrosPorIdioma(Idioma idioma);

    @Query("SELECT COUNT(l) FROM Libro l WHERE l.idiomas = :idioma")
    long contarLibrosPorIdioma(Idioma idioma);

 @Query("SELECT l FROM Libro l WHERE l.idiomas = :idioma ORDER BY l.titulo")
 List<Libro> buscarLibrosPorIdiomaOrdenadosPorTitulo(Idioma idioma);
}
