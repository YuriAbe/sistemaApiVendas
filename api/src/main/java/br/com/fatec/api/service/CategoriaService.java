package br.com.fatec.api.service;

import br.com.fatec.api.dto.CategoriaRequestDTO;
import br.com.fatec.api.dto.CategoriaResponseDTO;
import br.com.fatec.api.model.Categoria;
import br.com.fatec.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    // Buscar por nome: Converte a lista de Entidades para DTOs
    public Page<CategoriaResponseDTO> buscarPorNome(String nomeCategoria, Pageable pageable) {
        return repository
                .findByNomeCategoriaContainingIgnoreCase(nomeCategoria, pageable)
                .map(CategoriaResponseDTO::fromEntity);
    }

    // Listar: Converte a lista de Entidades para DTOs
    public Page<CategoriaResponseDTO> listarTodos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(CategoriaResponseDTO::fromEntity);
    }

    // Buscar por ID: Retorna Optional de DTO
    public Optional<CategoriaResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(CategoriaResponseDTO::fromEntity);
        // Se a Entity existir, o .map() a transforma em DTO.
        // Se não
    }

    // Salvar: Recebe RequestDTO -> Converte para Entity -> Salva -> Retorna
    // ResponseDTO
    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(dto.nomeCategoria());

        Categoria salvo = repository.save(categoria);
        return CategoriaResponseDTO.fromEntity(salvo);
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        // Busca a Entity no banco
        Categoria existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Transfere os dados do DTO para o Entity
        existente.setNomeCategoria(dto.nomeCategoria());

        // Salva e converte para o DTO de saida
        Categoria atualizado = repository.save(existente);
        return CategoriaResponseDTO.fromEntity(atualizado);
    }

    public void deletar(Long id) {
        // Valida se o ID existe antes de chamar o deleteById
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Categoria não encontrado com ID" + id);
        }

        // O método correto para um único ID é deleteById
        repository.deleteById(id);
    }
}
