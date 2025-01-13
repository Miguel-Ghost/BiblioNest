package com.uni.Literauni.repository;

import com.uni.Literauni.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);

}
