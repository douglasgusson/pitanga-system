/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.TipoProduto;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface TipoProdutoDAO {

    public void inserir(TipoProduto tipo);
    public void remover(int id);
    public List<TipoProduto> listarTodos();
    public List<TipoProduto> buscarPorDescricao(String descricao);
    public TipoProduto buscar(int id);
    public void alterar(TipoProduto tipo);

}
