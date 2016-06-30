package br.com.pitanga.domain;

import java.util.Date;
import java.util.Collection;

public class Compra {
 
	private int idCompra;
	 
	private Date dataCompra;
	 
	private double valorCompra;
	 
	private double valorDesconto;
	 
	private int quantParcela;
	 
	private Collection<TituloCompra> tituloCompra;
	 
	private Collection<ItemCompra> itemCompra;
	 
	private Fornecedor fornecedor;

    /**
     * @return the idCompra
     */
    public int getIdCompra() {
        return idCompra;
    }

    /**
     * @param idCompra the idCompra to set
     */
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    /**
     * @return the dataCompra
     */
    public Date getDataCompra() {
        return dataCompra;
    }

    /**
     * @param dataCompra the dataCompra to set
     */
    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    /**
     * @return the valorCompra
     */
    public double getValorCompra() {
        return valorCompra;
    }

    /**
     * @param valorCompra the valorCompra to set
     */
    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    /**
     * @return the valorDesconto
     */
    public double getValorDesconto() {
        return valorDesconto;
    }

    /**
     * @param valorDesconto the valorDesconto to set
     */
    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    /**
     * @return the quantParcela
     */
    public int getQuantParcela() {
        return quantParcela;
    }

    /**
     * @param quantParcela the quantParcela to set
     */
    public void setQuantParcela(int quantParcela) {
        this.quantParcela = quantParcela;
    }

    /**
     * @return the tituloCompra
     */
    public Collection<TituloCompra> getTituloCompra() {
        return tituloCompra;
    }

    /**
     * @param tituloCompra the tituloCompra to set
     */
    public void setTituloCompra(Collection<TituloCompra> tituloCompra) {
        this.tituloCompra = tituloCompra;
    }

    /**
     * @return the itemCompra
     */
    public Collection<ItemCompra> getItemCompra() {
        return itemCompra;
    }

    /**
     * @param itemCompra the itemCompra to set
     */
    public void setItemCompra(Collection<ItemCompra> itemCompra) {
        this.itemCompra = itemCompra;
    }

    /**
     * @return the fornecedor
     */
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    /**
     * @param fornecedor the fornecedor to set
     */
    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
	 
}
 
