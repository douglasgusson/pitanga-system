/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.ItemCompra;
import java.util.List;

/**
 *
 * @author Netsul_suporte
 */
public interface ItemCompraDAO {
    
    public void inserir(ItemCompra item);
    public List<ItemCompra> listarTodos();
}
