package com.uni.Literauni.modelo;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String titulo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private List<String> idiomas = new ArrayList<>();

    private int cantidadDeDescargas;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();


    public Libro() {}


    public Libro(DatoLibro d, List<Autor> autores) {
        this.titulo = d.titulo();
        this.idiomas = d.idiomas() != null ? d.idiomas() : new ArrayList<>();
        this.cantidadDeDescargas = d.cantidadDeDescargas();
        this.autores = autores != null ? autores : new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public int getCantidadDeDescargas() {
        return cantidadDeDescargas;
    }

    public void setCantidadDeDescargas(int cantidadDeDescargas) {
        this.cantidadDeDescargas = cantidadDeDescargas;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public void addAutor(Autor autor) {
        if (!this.autores.contains(autor)) {
            this.autores.add(autor);
            autor.addLibro(this);
        }
    }

    @Override
    public String toString() {

        String autoresStr = autores.stream()
                .map(Autor::getNombre)
                .collect(Collectors.joining(", "));

        return new StringBuilder()
                .append("----- LIBRO -----\n")
                .append("Título: ").append(titulo).append("\n")
                .append("Autor(es): ").append(autoresStr).append("\n")
                .append("Idioma(s): ").append(idiomas).append("\n")
                .append("Número de descargas: ").append(cantidadDeDescargas).append("\n")
                .append("-----------------\n")
                .toString();
    }
}
