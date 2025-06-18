package com.alura.Literalura.repository;

import com.alura.Literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Libro findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> encontrarPorIdioma(String idioma);

}
