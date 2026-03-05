package com.delivery.model;

import com.delivery.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pedido", nullable = false, updatable = false)
    private LocalDateTime dataPedido;

    @Column(name = "endereco_entrega", nullable = false, length = 200)
    private String enderecoEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(name = "taxa_entrega", precision = 10, scale = 2)
    private BigDecimal taxaEntrega = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    // Relacionamento: Muitos pedidos pertencem a UM cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Relacionamento: Um pedido TEM MUITOS itens
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        dataPedido = LocalDateTime.now();
    }

    // Métodos de negócio
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this);
        calcularTotal();
    }

    public void removerItem(ItemPedido item) {
        itens.remove(item);
        item.setPedido(null);
        calcularTotal();
    }

    public void calcularTotal() {
        BigDecimal subtotal = itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.total = subtotal.add(taxaEntrega);
    }

    public void confirmar() {
        if (this.status == StatusPedido.PENDENTE) {
            this.status = StatusPedido.CONFIRMADO;
        }
    }

    public void cancelar() {
        if (this.status == StatusPedido.PENDENTE || this.status == StatusPedido.CONFIRMADO) {
            this.status = StatusPedido.CANCELADO;
        } else {
            throw new IllegalStateException("Não é possível cancelar pedido no status: " + this.status);
        }
    }
}