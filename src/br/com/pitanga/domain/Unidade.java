package br.com.pitanga.domain;

import java.util.Collection;

public class Unidade {

    private String idUnidade;
    private String descricao;
    private Collection<Produto> produto;

    public Unidade(String idUnidade, String descricao) {
        this.idUnidade = idUnidade;
        this.descricao = descricao;
    }

    public Unidade() {
    }

    /**
     * @return the idUnidade
     */
    public String getIdUnidade() {
        return idUnidade;
    }

    /**
     * @param idUnidade the idUnidade to set
     */
    public void setIdUnidade(String idUnidade) {
        this.idUnidade = idUnidade;
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

}
