package com.uni.Literauni.modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false, unique = true)
    private String nombre;
    private Integer anioDeNacimiento;
    private Integer anioDeFallecimiento;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {}

    public Autor(DatoAutor datoAutor) {
        this.nombre = datoAutor.nombre();
        this.anioDeNacimiento = datoAutor.anioDeNacimiento();
        this.anioDeFallecimiento = datoAutor.anioDeFallecimiento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }
    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public void addLibro(Libro libro) {
        if (!this.libros.contains(libro)) {
            this.libros.add(libro);
            libro.addAutor(this); // Método en la clase Libro
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(Integer anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Integer getAnioDeFallecimiento() {
        return anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(Integer anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    @Override
    public String toString() {
        // Convertimos la lista de libros a un String con sus títulos
        String librosStr = this.libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", "));

        // Maneja los nulos en las fechas como prefieras:
        // p.ej. "Desconocida" o un valor por defecto
        String anioNacStr = (anioDeNacimiento != null) ? String.valueOf(anioDeNacimiento) : "Desconocido";
        String anioFallStr = (anioDeFallecimiento != null) ? String.valueOf(anioDeFallecimiento) : "Desconocido";

        // Construye la cadena final
        return new StringBuilder()
                .append("Autor: ").append(nombre).append("\n")
                .append("Fecha de nacimiento: ").append(anioNacStr).append("\n")
                .append("Fecha de fallecimiento: ").append(anioFallStr).append("\n")
                .append("Libros: [").append(librosStr).append("]\n")
                .toString();
    }
}
