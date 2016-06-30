/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.ItemVenda;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface ItemVendaDAO {

    public void inserir(ItemVenda item);
    public List<ItemVenda> listarTodos();
    public List<ItemVenda> listarPorVenda(Long idVenda);
    public boolean alterar(ItemVenda item);

}
