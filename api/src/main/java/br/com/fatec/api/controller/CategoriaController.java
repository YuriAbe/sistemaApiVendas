package br.com.fatec.api.controller;

import br.com.fatec.api.dto.CategoriaRequestDTO;
import br.com.fatec.api.dto.CategoriaResponseDTO;
import br.com.fatec.api.service.CategoriaService;
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

@Tag(name = "Categoria", description = "Gerenciamento de Categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @Operation(summary = "Listar todos as categorias", description = "Retorna uma página de categorias cadastrados com paginação")
    // LISTAR COM PAGINAÇÃO
    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> listarCategorias(
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = "nome") Pageable pageable) {

        Page<CategoriaResponseDTO> pagina = service.listarTodos(pageable);
        return ResponseEntity.ok(pagina);
    }

    @Operation(summary = "Criar categoria", description = "Cadastra um objeto categoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }

    @Operation(summary = "Buscar categoria", description = "Busca um objeto categoria pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar categoria", description = "Atualiza o objeto categoria pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO dto) {

        CategoriaResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Excluir categoria", description = "Excluir um objeto categoria pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<CategoriaResponseDTO>> buscarCategorias(
            @RequestParam String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        Page<CategoriaResponseDTO> pagina = service.buscarPorNome(nome, pageable);
        return ResponseEntity.ok(pagina);
    }

}
