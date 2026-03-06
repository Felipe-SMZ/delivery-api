package com.delivery.controller;

import com.delivery.model.Produto;
import com.delivery.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    // ========================================
    // GET - BUSCAR/LISTAR
    // ========================================

    // GET    /api/produtos
    // Listar todos
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }


    // GET    /api/produtos/{id}
    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    // GET    /api/produtos/disponiveis
    // Listar disponíveis
    @GetMapping("/disponiveis")
    public ResponseEntity<List<Produto>> listarDisponiveis() {
        List<Produto> produtos = produtoService.listarDisponiveis();
        return ResponseEntity.ok(produtos);
    }

    // GET    /api/produtos/restaurante/{id}
    // Produtos de um restaurante
    @GetMapping("/restaurante/{id}")
    public ResponseEntity<List<Produto>> listarPorRestaurante(@PathVariable long id) {
        List<Produto> produtos = produtoService.listarPorRestaurante(id);
        return ResponseEntity.ok(produtos);
    }

    // POST   /api/produtos
    // Criar produto
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        Produto produtoCriado = produtoService.criar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    // PUT    /api/produtos/{id}
    // Atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable long id,
            @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    // DELETE /api/produtos/{id}
    // Inativar produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Produto> inativar(@PathVariable long id) {
        produtoService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH  /api/produtos/{id}/ativar
    // Ativar produto
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Produto> ativar(@PathVariable long id) {
        produtoService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<Produto> remover(@PathVariable long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // ========================================
    // ESTATÍSTICAS
    // ========================================
    @GetMapping("/stats")
    public ResponseEntity<ProdutoStats> obterEstatisticas() {
        long total = produtoService.contarTodos();
        long disponiveis = produtoService.contarDisponiveis();
        long indisponiveis = produtoService.contarIndisponiveis();

        ProdutoStats stats = new ProdutoStats(total, disponiveis, indisponiveis);
        return ResponseEntity.ok(stats);
    }

    // Classe interna para resposta de estatísticas
    record ProdutoStats(long total, long disponiveis, long indisponiveis) {

    }
}
