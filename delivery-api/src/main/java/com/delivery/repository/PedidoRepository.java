package com.delivery.repository;

import com.delivery.model.Cliente;
import com.delivery.model.Pedido;
import com.delivery.model.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    //    Buscar pedidos de um cliente específico
    List<Pedido> findByClienteId(Long clienteId);

    //    Buscar pedidos por status
    List<Pedido> findByStatus(StatusPedido status);

    //    Buscar pedidos de um cliente com status específico
    List<Pedido> findByClienteIdAndStatus(Long clienteId, StatusPedido status);

    //    Contar pedidos por status
    long countByStatus(StatusPedido status);

    // Últimos pedidos do cliente (ordenados)
    List<Pedido> findByClienteIdOrderByDataPedidoDesc(Long clienteId);

    // Top 10 pedidos mais recentes
    List<Pedido> findTop10ByClienteIdOrderByDataPedidoDesc(Long clienteId);
}
