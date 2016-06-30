/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Unidade;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface UnidadeDAO {

    public void inserir(Unidade unidade);
    public boolean remover(String id);
    public List<Unidade> listarTodos();
    public List<Unidade> buscarPorDescricao(String descricao);
    public Unidade buscar(int id);
    public void alterar(Unidade unidade);
    
}
