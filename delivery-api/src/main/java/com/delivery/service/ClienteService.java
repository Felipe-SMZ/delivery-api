package com.delivery.service;

import com.delivery.model.Cliente;
import com.delivery.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // ========================================
    // CRIAR
    // ========================================

    @Transactional
    public Cliente criar(Cliente cliente) {
        // Validação 1: Nome obrigatório
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        // Validação 2: Email obrigatório
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail é obrigatório");
        }

        // Validação 3: Email único
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente com este e-mail");
        }

        // Validação 4: Telefone obrigatório
        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }

        // Validação 5: Endereço obrigatório
        if (cliente.getEndereco() == null || cliente.getEndereco().trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço é obrigatório");
        }

        // Validação 6: CPF obrigatório
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }

        // Validação 7: CPF único
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new IllegalArgumentException("Já existe um cliente com este CPF");
        }

        return clienteRepository.save(cliente);
    }

    // ========================================
    // BUSCAR
    // ========================================


    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
    }

    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com email: " + email));
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + cpf));
    }

    public List<Cliente> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return listarTodos();
        }
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    // ========================================
    // ATUALIZAR
    // ========================================

    @Transactional
    public Cliente atualizar(Long id, Cliente dadosAtualizados) {
        // Buscar cliente existente
        Cliente clienteExistente = buscarPorId(id);

        // Validar email único (se mudou)
        if (dadosAtualizados.getEmail() != null &&
                !dadosAtualizados.getEmail().equals(clienteExistente.getEmail())) {

            if (clienteRepository.existsByEmail(dadosAtualizados.getEmail())) {
                throw new IllegalArgumentException("Já existe outro cliente com este e-mail");
            }
        }

        // Validar CPF único (se mudou)
        if (dadosAtualizados.getCpf() != null &&
                !dadosAtualizados.getCpf().equals(clienteExistente.getCpf())) {

            if (clienteRepository.existsByCpf(dadosAtualizados.getCpf())) {
                throw new IllegalArgumentException("Já existe outro cliente com este CPF");
            }
        }

        // Atualizar campos (se fornecidos)
        if (dadosAtualizados.getNome() != null) {
            clienteExistente.setNome(dadosAtualizados.getNome());
        }

        if (dadosAtualizados.getEmail() != null) {
            clienteExistente.setEmail(dadosAtualizados.getEmail());
        }

        if (dadosAtualizados.getTelefone() != null) {
            clienteExistente.setTelefone(dadosAtualizados.getTelefone());
        }

        if (dadosAtualizados.getEndereco() != null) {
            clienteExistente.setEndereco(dadosAtualizados.getEndereco());
        }

        if (dadosAtualizados.getCpf() != null) {
            clienteExistente.setCpf(dadosAtualizados.getCpf());
        }

        return clienteRepository.save(clienteExistente);
    }

    // ========================================
    // DELETAR
    // ========================================

    @Transactional
    public void deletar(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }

    // ========================================
    // ESTATÍSTICAS
    // ========================================

    public long contarTodos() {
        return clienteRepository.count();
    }
}