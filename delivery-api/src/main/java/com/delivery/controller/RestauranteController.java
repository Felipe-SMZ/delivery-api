package com.delivery.controller;

import com.delivery.model.Restaurante;
import com.delivery.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;

    // ========================================
    // GET - BUSCAR/LISTAR
    // ========================================


//     GET /api/restaurantes
//     Lista todos os restaurantes

    @GetMapping
    public ResponseEntity<List<Restaurante>> listarTodos() {
        List<Restaurante> restaurantes = restauranteService.listarTodos();
        return ResponseEntity.ok(restaurantes);
    }


//     GET /api/restaurantes/{id}
//     Busca restaurante por ID

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarPorId(id);
        return ResponseEntity.ok(restaurante);
    }


//     GET /api/restaurantes/ativos
//     Lista apenas restaurantes ativos

    @GetMapping("/ativos")
    public ResponseEntity<List<Restaurante>> listarAtivos() {
        List<Restaurante> restaurantes = restauranteService.listarAtivos();
        return ResponseEntity.ok(restaurantes);
    }

//     GET /api/restaurantes/buscar?nome=pizza
//     Busca restaurantes por nome

    @GetMapping("/buscar")
    public ResponseEntity<List<Restaurante>> buscarPorNome(@RequestParam(required = false) String nome) {
        List<Restaurante> restaurantes = restauranteService.buscarPorNome(nome);
        return ResponseEntity.ok(restaurantes);
    }

    // ========================================
    // POST - CRIAR
    // ========================================


//     POST /api/restaurantes
//     Cria um novo restaurante

    @PostMapping
    public ResponseEntity<Restaurante> criar(@RequestBody Restaurante restaurante) {
        Restaurante restauranteCriado = restauranteService.criar(restaurante);
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteCriado);
    }

    // ========================================
    // PUT - ATUALIZAR
    // ========================================


//     PUT /api/restaurantes/{id}
//     Atualiza um restaurante existente

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(
            @PathVariable Long id,
            @RequestBody Restaurante restaurante) {

        Restaurante restauranteAtualizado = restauranteService.atualizar(id, restaurante);
        return ResponseEntity.ok(restauranteAtualizado);
    }

    // ========================================
    // DELETE/PATCH - INATIVAR/ATIVAR/DELETAR
    // ========================================


//     DELETE /api/restaurantes/{id}
//     Inativa um restaurante (soft delete)

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
        return ResponseEntity.noContent().build();
    }

//     PATCH /api/restaurantes/{id}/ativar
//     Reativa um restaurante inativo

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
        return ResponseEntity.noContent().build();
    }


//     DELETE /api/restaurantes/{id}/permanente
//     Deleta permanentemente um restaurante

    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<Void> deletarPermanente(@PathVariable Long id) {
        restauranteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ========================================
    // ESTATÍSTICAS
    // ========================================


//     GET /api/restaurantes/stats
//     Retorna estatísticas de restaurantes

    @GetMapping("/stats")
    public ResponseEntity<RestauranteStats> obterEstatisticas() {
        long total = restauranteService.contarTodos();
        long ativos = restauranteService.contarAtivos();
        long inativos = total - ativos;

        RestauranteStats stats = new RestauranteStats(total, ativos, inativos);
        return ResponseEntity.ok(stats);
    }

    // Classe interna para resposta de estatísticas
    record RestauranteStats(long total, long ativos, long inativos) {
    }
}