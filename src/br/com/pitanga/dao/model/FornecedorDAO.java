/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Fornecedor;
import java.util.List;

/**
 *
 * @author Diêgo Biazate
 */
public interface FornecedorDAO {
    public void inserir(Fornecedor fornecedor);
    public void remover(int id);
    public List<Fornecedor> listarTodos();
    public List<Fornecedor> buscarPorNome(String nome);
    public Fornecedor buscar(int id);
    public void alterar(Fornecedor fornecedor);
}
