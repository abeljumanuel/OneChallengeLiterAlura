package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.ArrayList;

public record Gutendex(
        @JsonAlias("count") int cantidad,
        @JsonAlias("next") String siguientePagina,
        @JsonAlias("previous") String anteriorPagina,
        @JsonAlias("results") ArrayList<DatosBook> datosBook
) {
}
