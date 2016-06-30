/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.TituloCompra;
import java.util.List;

/**
 *
 * @author douglas
 * @date 02/09/2015
 * @time 23:47:56
 */
public interface TituloCompraDAO {

    public void inserir(TituloCompra titulo);
    public List<TituloCompra> listarTodos();

}
