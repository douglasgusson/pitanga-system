/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Compra;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface CompraDAO {
    
    public void inserir(Compra compra);
    public void remover(int idCompra);
    public List<Compra> listarTodos();
    public List<Compra> buscarPorId(int idCompra);
    public Compra buscar(int idCompra);
    public void alterar(Compra compra);
    public Compra getUltimaCompra();

}
