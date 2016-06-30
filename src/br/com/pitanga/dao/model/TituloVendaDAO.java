/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.TituloVenda;
import java.util.Date;
import java.util.List;

/**
 *
 * @author douglas
 * @date 02/09/2015
 * @time 23:47:56
 */
public interface TituloVendaDAO {

    public void inserir(TituloVenda titulo);    
    public List<TituloVenda> listarTodos();
    public List<TituloVenda> listarTodosAberto();
    public List<TituloVenda> listarFiltro(boolean vencimento, 
            boolean emissao, Date dataInicio, Date dataFim);
    public List<TituloVenda> listarFiltro(boolean vencimento, 
            boolean emissao, Date dataInicio, Date dataFim, int idCliente);
    public boolean atualizarRecebimentoTitulo(TituloVenda tituloVenda);
    public List<TituloVenda> listarPorVenda(Long idVenda);
    public boolean alterar(TituloVenda titulo);
}
