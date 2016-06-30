/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Produto;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface ProdutoDAO {
    
    public void inserir(Produto produto);
    public boolean remover(int idProduto);
    public List<Produto> listarTodos();
    public List<Produto> buscarPorNome(String nome);
    public Produto buscar(int idProduto);
    public void alterar(Produto produto);
    public void alterarQuantidade(int qtd, int id);

}
