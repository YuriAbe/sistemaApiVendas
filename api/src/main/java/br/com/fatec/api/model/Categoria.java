package br.com.fatec.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para auto incremento

    private Long idCategoria;

    @NotBlank(message = "O nome é obrigatório e não pode conter apenas espaços em branco.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre {min} e {max} caracteres")
    private String nomeCategoria;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Produto> produtos;

}