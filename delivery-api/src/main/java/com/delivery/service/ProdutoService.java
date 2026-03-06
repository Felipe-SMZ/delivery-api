package com.delivery.service;

import com.delivery.model.Produto;
import com.delivery.repository.ProdutoRepository;
import com.delivery.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;


    @Transactional
    public Produto criar(Produto produto) {
        //Nome não pode ser vazio
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        //Preço deve ser maior que zero
        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que ZERO!");
        }

        //Restaurante deve existir
        if (!restauranteRepository.existsById(produto.getRestaurante().getId())) {
            throw new IllegalArgumentException("O restaurante não existe!");
        }

        //Produto começa disponível (true)
        produto.setDisponivel(true);

        // Salvar no banco
        return produtoRepository.save(produto);
    }

    // ========================================
    // BUSCAR
    // ========================================
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    public List<Produto> listarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId);
    }

    public List<Produto> listarDisponiveis() {
        return produtoRepository.findByDisponivelTrue();
    }

    // ========================================
    // ATUALIZAR
    // ========================================
    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        //Buscar se o produto existe
        Produto produtoExistente = buscarPorId(id);

        //Validar se novo nome já existe
        if (produtoAtualizado.getNome() != null
                && !produtoAtualizado.getNome().equals(produtoExistente.getNome())) {
            if (produtoRepository.existsByNome(produtoAtualizado.getNome())) {
                throw new IllegalArgumentException("Já existe um produto com esse nome!");
            }
        }

        // Atualizar campos (se fornecidos)
        if (produtoAtualizado.getNome() != null) {
            produtoExistente.setNome(produtoAtualizado.getNome());
        }

        if (produtoAtualizado.getDescricao() != null) {
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        }

        if (produtoAtualizado.getPreco() != null) {
            if (produtoAtualizado.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("O preço deve ser maior que zero");
            }
            produtoExistente.setPreco(produtoAtualizado.getPreco());
        }

        if (produtoAtualizado.getDisponivel() != null) {
            produtoExistente.setDisponivel(produtoAtualizado.getDisponivel());
        }

        return produtoRepository.save(produtoExistente);

    }

    // ========================================
    // DELETAR
    // ========================================
    @Transactional
    public void inativar(Long id) {
        Produto produto = buscarPorId(id);
        produto.setDisponivel(false);
        produtoRepository.save(produto);
    }

    @Transactional
    public void ativar(Long id) {
        Produto produto = buscarPorId(id);
        produto.setDisponivel(true);
        produtoRepository.save(produto);
    }

    @Transactional
    public void excluir(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    // ========================================
    // ESTATÍSTICAS
    // ========================================
    //Conta quantos produtos estão disponíveis
    public long contarDisponiveis() {
        return produtoRepository.countByDisponivelTrue();
    }

    //Conta quantos produtos estão indisponíveis
    public long contarIndisponiveis() {
        return produtoRepository.countByDisponivelFalse();
    }

    //Conta quantos produtos temos
    public long contarTodos() {
        return produtoRepository.count();
    }
}
