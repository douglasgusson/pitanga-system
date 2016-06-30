package br.com.pitanga.domain;

import java.util.Collection;

public class TipoProduto {

    private int idTipo;
    private String descricao;
    private boolean medida;
    
    private Collection<Produto> produto;

    public TipoProduto(int idTipo, String descricao) {
        this.idTipo = idTipo;
        this.descricao = descricao;
    }

    public TipoProduto() {
    }

    /**
     * @return the idTipo
     */
    public int getIdTipo() {
        return idTipo;
    }

    /**
     * @param idTipo the idTipo to set
     */
    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the produto
     */
    public Collection<Produto> getProduto() {
        return produto;
    }

    /**
     * @param produto the produto to set
     */
    public void setProduto(Collection<Produto> produto) {
        this.produto = produto;
    }

    /**
     * @return the medida
     */
    public boolean isMedida() {
        return medida;
    }

    /**
     * @param medida the medida to set
     */
    public void setMedida(boolean medida) {
        this.medida = medida;
    }
    
}
