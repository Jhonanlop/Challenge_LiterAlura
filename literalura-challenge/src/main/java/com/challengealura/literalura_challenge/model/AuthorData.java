package com.challengealura.literalura_challenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData(
        @JsonAlias("name") String nombre,
        @JsonAlias ("birth_year") String fechaDeNacimiento,
        @JsonAlias ("death_year") String fechaDeMuerte
) {
}