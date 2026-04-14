package br.com.fatec.api.dto;

import br.com.fatec.api.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProdutoResponseDTO (

        @Schema(example = "1")
        Long id,

        @Schema(example = "Produto")
        String nome,

        @Schema(example = "100.0")
        Double preco
) {
    // Método utilitário para converter de Entity para DTO
    public static ProdutoResponseDTO  fromEntity(Produto produto) {
        return new ProdutoResponseDTO(produto.getIdProduto(), produto.getNomeProduto(), produto.getPrecoProduto());
    }
}
