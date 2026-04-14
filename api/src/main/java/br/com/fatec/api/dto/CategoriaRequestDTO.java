package br.com.fatec.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO (
    @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
        @Schema(example = "Categoria")
        String nome
){}



