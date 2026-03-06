package com.delivery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, length = 200)
    private String endereco;

    @Column(length = 20)
    private String telefone;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "ativo")
    private Boolean ativo = true;

    // Relacionamento: Um restaurante TEM MUITOS produtos
    @JsonManagedReference
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();

    // Metodo executado antes de salvar no banco
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    // Métodos de negócio
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        produto.setRestaurante(this);
    }

    public void removerProduto(Produto produto) {
        produtos.remove(produto);
        produto.setRestaurante(null);
    }
}