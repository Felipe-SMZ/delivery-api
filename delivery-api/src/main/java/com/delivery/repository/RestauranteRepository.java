package com.delivery.repository;

import com.delivery.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para a entidade Restaurante.
 *
 * Responsabilidades:
 * - Fazer queries no banco de dados
 * - CRUD básico (herdado de JpaRepository)
 * - Queries customizadas
 */
@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    // ========================================
    // QUERIES DERIVADAS (Spring gera automaticamente!)
    // ========================================

    /**
     * Busca restaurantes cujo nome CONTÉM o texto fornecido (case insensitive).
     *
     * SQL gerado:
     * SELECT * FROM restaurantes WHERE LOWER(nome) LIKE LOWER('%texto%')
     *
     * @param nome Texto a buscar no nome
     * @return Lista de restaurantes encontrados
     */
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca restaurantes ativos.
     *
     * SQL gerado:
     * SELECT * FROM restaurantes WHERE ativo = TRUE
     *
     * @return Lista de restaurantes ativos
     */
    List<Restaurante> findByAtivoTrue();

    /**
     * Busca restaurantes inativos.
     *
     * SQL gerado:
     * SELECT * FROM restaurantes WHERE ativo = FALSE
     *
     * @return Lista de restaurantes inativos
     */
    List<Restaurante> findByAtivoFalse();

    /**
     * Busca restaurante por nome exato.
     *
     * SQL gerado:
     * SELECT * FROM restaurantes WHERE nome = ?
     *
     * @param nome Nome exato do restaurante
     * @return Optional contendo o restaurante ou vazio
     */
    Optional<Restaurante> findByNome(String nome);

    /**
     * Busca restaurantes por endereço (contém).
     *
     * SQL gerado:
     * SELECT * FROM restaurantes WHERE endereco LIKE '%texto%'
     *
     * @param endereco Texto a buscar no endereço
     * @return Lista de restaurantes
     */
    List<Restaurante> findByEnderecoContaining(String endereco);

    /**
     * Verifica se existe restaurante com o nome fornecido.
     *
     * SQL gerado:
     * SELECT COUNT(*) > 0 FROM restaurantes WHERE nome = ?
     *
     * @param nome Nome a verificar
     * @return true se existe, false caso contrário
     */
    boolean existsByNome(String nome);

    /**
     * Conta quantos restaurantes estão ativos.
     *
     * SQL gerado:
     * SELECT COUNT(*) FROM restaurantes WHERE ativo = TRUE
     *
     * @return Número de restaurantes ativos
     */
    long countByAtivoTrue();
}