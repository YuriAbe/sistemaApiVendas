package br.com.fatec.api.repository;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fatec.api.model.Produto;

@Repository
public interface ProdutoRepository
        extends JpaRepository<Produto, Long> {
    // O JpaRepository já contém o método: Page<T> findAll(Pageable pageable);

}
