package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> getAutorByName(String name);

    List<Autor> findByBirthyearLessThanEqualAndDeathyearGreaterThanEqual(int year, int yearAlive);
}
