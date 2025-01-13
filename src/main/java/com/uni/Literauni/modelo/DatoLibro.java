package com.uni.Literauni.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatoLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatoAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") int cantidadDeDescargas
) {
}
