package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private int numero_de_descargas;

    public Libro() {
    }

    public Libro(DatosBook datosBook) {
        this.titulo = datosBook.titulo();
        this.autor = new Autor(datosBook.autores().get(0));
        this.idioma = datosBook.lenguajes().get(0) ;
        this.numero_de_descargas = datosBook.cantidadDescargas();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumero_de_descargas() {
        return numero_de_descargas;
    }

    public void setNumero_de_descargas(int numero_de_descargas) {
        this.numero_de_descargas = numero_de_descargas;
    }

    @Override
    public String toString() {
        return String.format("""
                ---------- LIBRO ----------
                Titulo  : %s
                Autor   : %s
                Idioma  : %s
                Número de descargas : %d
                ---------------------------
                """,
        titulo,
        autor.getName(),
        idioma,
        numero_de_descargas);

    }
}
