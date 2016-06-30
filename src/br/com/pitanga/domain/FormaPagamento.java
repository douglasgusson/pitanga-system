/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.domain;

/**
 *
 * @author Netsul
 */
public class FormaPagamento {
    
    private int id_FormaPagamento;
    private String descricao;

    public int getId_FormaPagamento() {
        return id_FormaPagamento;
    }

    public void setId_FormaPagamento(int id_FormaPagamento) {
        this.id_FormaPagamento = id_FormaPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    
}
