package br.com.fatec.api.dto;

import java.util.List;

import br.com.fatec.api.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

public record CategoriaResponseDTO(

        @Schema(example = "1") Long idCategoria,

        @Schema(example = "Categoria") String nomeCategoria,

        List<ProdutoResponseDTO> produtos

) {
    // Método utilitário para converter de Entity para DTO
    public static CategoriaResponseDTO fromEntity(Categoria categoria) {
        List<ProdutoResponseDTO> produtosDTO = categoria.getProdutos() == null
        ? List.of()
        : categoria.getProdutos()
            .stream()
            .map(ProdutoResponseDTO::fromEntity)
            .toList();

        return new CategoriaResponseDTO(
                categoria.getIdCategoria(),
                categoria.getNomeCategoria(),
                produtosDTO);
    }
}
