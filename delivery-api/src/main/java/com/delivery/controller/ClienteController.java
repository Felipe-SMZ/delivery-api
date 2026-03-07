package com.delivery.controller;

import com.delivery.model.Cliente;
import com.delivery.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteRepository clienteRepository;

    // ========================================
    // GET - BUSCAR/LISTAR
    // ========================================

    // GET /api/clientes
    // Listar todos
    @GetMapping
    public ResponseEntity<Cliente>
}
