package br.com.fatec.api.controller;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.service.ProdutoService;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produto", description = "Gerenciamento de Produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma página de produtos cadastrados com paginação")
    // LISTAR COM PAGINAÇÃO
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listarProdutos(
            @ParameterObject
            @PageableDefault(size = 5, page = 0, sort = "nome") Pageable pageable) {

        Page<ProdutoResponseDTO> pagina = service.listarTodos(pageable);
        return ResponseEntity.ok(pagina);
    }

    @Operation(summary = "Criar produto", description = "Cadastra um objeto produto")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }

    @Operation(summary = "Buscar produto", description = "Busca um objeto produto pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza o objeto produto pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {

        ProdutoResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Excluir produto", description = "Excluir um objeto produto pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}