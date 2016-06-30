/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Funcionario;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface FuncionarioDAO {
    
    public void inserir(Funcionario funcionario);
    public void remover(int idFuncionario);
    public List<Funcionario> listarTodos();
    public List<Funcionario> buscarPorNome(String nome);
    public Funcionario buscar(int idFuncionario);
    public void alterar(Funcionario funcionario);

}
