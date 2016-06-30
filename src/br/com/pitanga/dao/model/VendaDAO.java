/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Venda;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface VendaDAO {
    
    public boolean inserir(Venda venda);
    public boolean remover(Long idVenda);
    public List<Venda> listarTodos(int ordem);
    public List<Venda> buscarPorId(int idVenda);
    public Venda buscar(int idVenda);
    public void alterar(Venda venda);
    public boolean alterarVendaNova(Venda venda);
    public Venda getUltimaVenda();
    public boolean alterarVenda(Venda venda);

}
