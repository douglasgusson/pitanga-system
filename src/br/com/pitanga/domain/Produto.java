package br.com.pitanga.domain;

import java.util.Collection;

public class Produto {

    private int idProduto;
    private String descricao;
    private double valorVenda;
    private String localizacao;
    private double quantidade;
    private TipoProduto tipoProduto;
    private Unidade unidade;

    private Collection<ItemVenda> itemVenda;
    private Collection<ItemCompra> itemCompra;

    public Produto(int idProduto, String descricao, double valorVenda,
            double comprimentoBr, double alturaBr, double larguraBr,
            double comprimentoLiq, double alturaLiq, double larguraLiq,
            String localizacao, double quantidade, TipoProduto tipoProduto,
            Unidade unidade) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.valorVenda = valorVenda;
        this.localizacao = localizacao;
        this.quantidade = quantidade;
        this.tipoProduto = tipoProduto;
        this.unidade = unidade;
    }

    public Produto() {
    }
    
    /**
     * @return the idProduto
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * @param idProduto the idProduto to set
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
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
     * @return the valorVenda
     */
    public double getValorVenda() {
        return valorVenda;
    }

    /**
     * @param valorVenda the valorVenda to set
     */
    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    /**
     * @return the localizacao
     */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * @param localizacao the localizacao to set
     */
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * @return the quantidade
     */
    public double getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the tipoProduto
     */
    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    /**
     * @param tipoProduto the tipoProduto to set
     */
    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    /**
     * @return the unidade
     */
    public Unidade getUnidade() {
        return unidade;
    }

    /**
     * @param unidade the unidade to set
     */
    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

}
