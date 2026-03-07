package com.delivery.controller;

import com.delivery.model.Cliente;
import com.delivery.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    // ========================================
    // GET - BUSCAR/LISTAR
    // ========================================

    // GET /api/clientes
    // Listar todos
    @GetMapping
    public ResponseEntity<List<Cliente>> listarCliente() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    // GET    /api/clientes/{id}
    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    // GET    /api/clientes/email/{email}
    // Buscar por email

    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> buscarPorEmail(@PathVariable String email) {
        Cliente cliente = clienteService.buscarPorEmail(email);
        return ResponseEntity.ok(cliente);
    }

    //GET /api/clientes/cpf/{cpf}
    //buscar por CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCPF(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    //GET /api/clientes/buscar
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam(required = false) String nome) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }

    // POST   /api/clientes
    // Criar cliente
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente clienteCriado = clienteService.criar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }

    // PUT    /api/clientes/{id}
    // Atualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteAtualizado = clienteService.atualizar(id, cliente);
        return ResponseEntity.ok(clienteAtualizado);
    }

    // DELETE /api/clientes/{id}
    // Deletar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // STATS /api/clientes/stats
    @GetMapping("/stats")
    public ResponseEntity<ClienteStats> obterEstatistica() {
        long total = clienteService.contarTodos();
        ClienteStats clienteStats = new ClienteStats(total);
        return ResponseEntity.ok(clienteStats);
    }

    // Classe interna para resposta de estatísticas
    record ClienteStats(long total) {
    }
}
