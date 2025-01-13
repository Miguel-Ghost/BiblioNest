package com.uni.Literauni.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatoAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer anioDeNacimiento,
        @JsonAlias("death_year") Integer anioDeFallecimiento
) {
}
