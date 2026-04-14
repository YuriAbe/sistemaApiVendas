package br.com.fatec.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fatec.api.model.Categoria;

@Repository
public interface CategoriaRepository 
    extends JpaRepository<Categoria, Long> {
        // O JpaRepository já contém o método: Page<T> findAll(Pageable pageable);
        Page<Categoria> findByNomeCategoriaContainingIgnoreCase(String nome, Pageable pageable);
    }

