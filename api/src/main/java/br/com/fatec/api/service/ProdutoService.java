package br.com.fatec.api.service;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.model.Categoria;
import br.com.fatec.api.model.Produto;
import br.com.fatec.api.repository.CategoriaRepository;
import br.com.fatec.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Listar: Converte a lista de Entidades para DTOs
    public Page<ProdutoResponseDTO> listarTodos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ProdutoResponseDTO::fromEntity);
    }

    // Buscar por ID: Retorna Optional de DTO
    public Optional<ProdutoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(ProdutoResponseDTO::fromEntity);
        // Se a Entity existir, o .map() a transforma em DTO.
        // Se não
    }

    // Salvar: Recebe RequestDTO -> Converte para Entity -> Salva -> Retorna
    // ResponseDTO
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNomeProduto(dto.nome());
        produto.setPrecoProduto(dto.preco());

        // BUSCA A CATEGORIA NO BANCO
        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setCategoria(categoria);

        Produto salvo = repository.save(produto);
        return ProdutoResponseDTO.fromEntity(salvo);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        // Busca a Entity no banco
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrada"));

        // Transfere os dados do DTO para o Entity
        existente.setNomeProduto(dto.nome());
        existente.setPrecoProduto(dto.preco());

        // BUSCA A CATEGORIA NO BANCO
        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        existente.setCategoria(categoria);

        // Salva e converte para o DTO de saida
        Produto atualizado = repository.save(existente);
        return ProdutoResponseDTO.fromEntity(atualizado);
    }

    public void deletar(Long id) {
        // Valida se o ID existe antes de chamar o deleteById
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Produto não encontrado com ID" + id);
        }

        // O método correto para um único ID é deleteById
        repository.deleteById(id);
    }
}
