package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String name;
    private int birthyear;
    private int deathyear;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(Authors authors) {
        this.name = authors.name();
        this.birthyear = authors.birth_year();
        this.deathyear = authors.death_year();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birth_year) {
        this.birthyear = birth_year;
    }

    public int getDeathyear() {
        return deathyear;
    }

    public void setDeathyear(int death_year) {
        this.deathyear = death_year;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(libro -> libro.setAutor(this));
        this.libros = libros;
    }

    @Override
    public String toString() {
        StringBuilder librosString = new StringBuilder();
        for (Libro libro : libros) {
            librosString.append("  - ").append(libro.getTitulo()).append("\n");
        }
        return String.format("""
                Autor   :   %s
                Fecha de nacimiento :   %d
                Fecha fallecimiento :   %d
                Libros:
                %s
                """,
                name,
                birthyear,
                deathyear,
                librosString);
    }
}
