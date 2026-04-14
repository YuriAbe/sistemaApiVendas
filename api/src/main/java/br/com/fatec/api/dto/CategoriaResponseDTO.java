package br.com.fatec.api.dto;

import br.com.fatec.api.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

public record CategoriaResponseDTO (

        @Schema(example = "1")
        Long id,

        @Schema(example = "Categoria")
        String nome
) {
    // Método utilitário para converter de Entity para DTO
    public static CategoriaResponseDTO  fromEntity(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getIdCategoria(), categoria.getNomeCategoria());
    }
}
