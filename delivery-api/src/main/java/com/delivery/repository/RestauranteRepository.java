package com.delivery.repository;

import com.delivery.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {


    //Busca restaurantes cujo nome CONTÉM o texto fornecido (case insensitive).
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

    //Busca restaurantes ativos.
    List<Restaurante> findByAtivoTrue();


    //Busca restaurantes inativos.
    List<Restaurante> findByAtivoFalse();

    //Busca restaurante por nome exato.
    Optional<Restaurante> findByNome(String nome);

    //Busca restaurantes por endereço (contém).
    List<Restaurante> findByEnderecoContaining(String endereco);

    //Verifica se existe restaurante com o nome fornecido.
    boolean existsByNome(String nome);

    //Conta quantos restaurantes estão ativos.
    long countByAtivoTrue();

    //Buscar restaurante por id
    boolean existsById(Long id);
}