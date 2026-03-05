package com.delivery.repository;

import com.delivery.model.Produto;
import com.delivery.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar produtos de um restaurante
    List<Produto> findByRestauranteId(Long restauranteId);

    // Buscar produtos disponíveis
    List<Produto> findByDisponivelTrue();

    // Buscar produtos com preço menor que X
    List<Produto> findByPrecoLessThan(BigDecimal preco);

    // Buscar produtos com preço entre X e Y
    List<Produto> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    // Verificar se existe produto com determinado nome
    boolean existsByNome(String nome);

    // Buscar produtos contendo nome
    List<Produto> findByNomeContaining(String nome);

    // Produtos disponíveis de um restaurante específico
    List<Produto> findByRestauranteIdAndDisponivelTrue(Long restauranteId);
}