package com.delivery.repository;

import com.delivery.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //Buscar cliente por email
    Optional<Cliente> findByEmail(String email);

    //Buscar clientes por nome (contendo)
    List<Cliente> findByNomeContaining(String nome);

    //Verificar se existe cliente com determinado email
    boolean existsByEmail(String email);

    //Buscar clientes por CPF
    Optional<Cliente> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}

