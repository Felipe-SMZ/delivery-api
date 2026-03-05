package com.delivery.service;

import com.delivery.model.Restaurante;
import com.delivery.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service para gerenciar a lógica de negócio de Restaurantes.
 * <p>
 * Responsabilidades:
 * - Validações de negócio
 * - Regras de negócio
 * - Orquestrar múltiplos repositories
 * - Gerenciar transações
 */
@Service
@RequiredArgsConstructor
public class RestauranteService {

    // Injeção de Dependência via construtor (graças ao @RequiredArgsConstructor do Lombok)
    private final RestauranteRepository restauranteRepository;

    // ========================================
    // CRIAR
    // ========================================

    /**
     * Cria um novo restaurante.
     * <p>
     * Validações:
     * - Nome não pode ser vazio
     * - Endereço não pode ser vazio
     * - Nome não pode já existir
     *
     * @param restaurante Dados do restaurante
     * @return Restaurante salvo com ID gerado
     * @throws IllegalArgumentException se validações falharem
     */
    @Transactional
    public Restaurante criar(Restaurante restaurante) {
        // Validação 1: Nome obrigatório
        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do restaurante é obrigatório");
        }

        // Validação 2: Endereço obrigatório
        if (restaurante.getEndereco() == null || restaurante.getEndereco().trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do restaurante é obrigatório");
        }

        // Validação 3: Nome já existe?
        if (restauranteRepository.existsByNome(restaurante.getNome())) {
            throw new IllegalArgumentException("Já existe um restaurante com este nome");
        }

        // Regra de negócio: Todo restaurante começa ativo
        restaurante.setAtivo(true);

        // Salvar no banco
        return restauranteRepository.save(restaurante);
    }

    // ========================================
    // BUSCAR
    // ========================================

    /**
     * Lista todos os restaurantes.
     *
     * @return Lista de todos os restaurantes
     */
    public List<Restaurante> listarTodos() {
        return restauranteRepository.findAll();
    }

    /**
     * Lista apenas restaurantes ativos.
     *
     * @return Lista de restaurantes ativos
     */
    public List<Restaurante> listarAtivos() {
        return restauranteRepository.findByAtivoTrue();
    }

    /**
     * Busca restaurante por ID.
     *
     * @param id ID do restaurante
     * @return Restaurante encontrado
     * @throws RuntimeException se não encontrar
     */
    public Restaurante buscarPorId(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado com ID: " + id));
    }

    /**
     * Busca restaurantes por nome (contendo).
     *
     * @param nome Texto a buscar
     * @return Lista de restaurantes encontrados
     */
    public List<Restaurante> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return listarTodos();
        }
        return restauranteRepository.findByNomeContainingIgnoreCase(nome);
    }

    // ========================================
    // ATUALIZAR
    // ========================================

    /**
     * Atualiza dados de um restaurante.
     *
     * @param id               ID do restaurante a atualizar
     * @param dadosAtualizados Novos dados
     * @return Restaurante atualizado
     * @throws RuntimeException se não encontrar
     */
    @Transactional
    public Restaurante atualizar(Long id, Restaurante dadosAtualizados) {
        // Buscar restaurante existente
        Restaurante restauranteExistente = buscarPorId(id);

        // Validar se novo nome já existe (e não é o próprio restaurante)
        if (dadosAtualizados.getNome() != null &&
                !dadosAtualizados.getNome().equals(restauranteExistente.getNome())) {

            if (restauranteRepository.existsByNome(dadosAtualizados.getNome())) {
                throw new IllegalArgumentException("Já existe outro restaurante com este nome");
            }
        }

        // Atualizar campos (se fornecidos)
        if (dadosAtualizados.getNome() != null) {
            restauranteExistente.setNome(dadosAtualizados.getNome());
        }

        if (dadosAtualizados.getDescricao() != null) {
            restauranteExistente.setDescricao(dadosAtualizados.getDescricao());
        }

        if (dadosAtualizados.getEndereco() != null) {
            restauranteExistente.setEndereco(dadosAtualizados.getEndereco());
        }

        if (dadosAtualizados.getTelefone() != null) {
            restauranteExistente.setTelefone(dadosAtualizados.getTelefone());
        }

        // Salvar mudanças
        return restauranteRepository.save(restauranteExistente);
    }

    // ========================================
    // DELETAR
    // ========================================

    /**
     * Inativa um restaurante (soft delete).
     * Não deleta do banco, apenas marca como inativo.
     *
     * @param id ID do restaurante
     */
    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = buscarPorId(id);
        restaurante.setAtivo(false);
        restauranteRepository.save(restaurante);
    }

    /**
     * Reativa um restaurante inativo.
     *
     * @param id ID do restaurante
     */
    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscarPorId(id);
        restaurante.setAtivo(true);
        restauranteRepository.save(restaurante);
    }

    /**
     * Deleta permanentemente um restaurante do banco.
     * CUIDADO: Isso remove todos os produtos relacionados!
     *
     * @param id ID do restaurante
     */
    @Transactional
    public void deletar(Long id) {
        Restaurante restaurante = buscarPorId(id);
        restauranteRepository.delete(restaurante);
    }

    // ========================================
    // ESTATÍSTICAS
    // ========================================

    /**
     * Conta quantos restaurantes estão ativos.
     *
     * @return Número de restaurantes ativos
     */
    public long contarAtivos() {
        return restauranteRepository.countByAtivoTrue();
    }

    /**
     * Conta total de restaurantes.
     *
     * @return Número total de restaurantes
     */
    public long contarTodos() {
        return restauranteRepository.count();
    }
}