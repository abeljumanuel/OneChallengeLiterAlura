package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public record DatosBook(
        @JsonAlias("id") long idGutendex,
        @JsonAlias("title") String titulo,
        @JsonAlias("subjects") ArrayList<String> contexto,
        @JsonAlias("authors") ArrayList<Authors> autores,
        @JsonAlias("translators") ArrayList<Translator> datosTraduccion,
        @JsonAlias("bookshelves") ArrayList<String> estanteria,
        @JsonAlias("languages") ArrayList<String> lenguajes,
        @JsonAlias("copyright") boolean derechosAutor,
        @JsonAlias("media_type") String tipoContenido,
        @JsonIgnore
        String formats,
        @JsonAlias("download_count") int cantidadDescargas
) {
}
