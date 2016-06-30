package br.com.pitanga.domain;

import java.util.Date;

public class TituloCompra {

    private int idTitulo;
    private int numParcela;
    private double valorParcela;
    private Date vencimento;
    private Compra compra;
    private FormaPagamento formaPagamento;

    /**
     * @return the idTitulo
     */
    public int getIdTitulo() {
        return idTitulo;
    }

    /**
     * @param idTitulo the idTitulo to set
     */
    public void setIdTitulo(int idTitulo) {
        this.idTitulo = idTitulo;
    }

    /**
     * @return the numParcela
     */
    public int getNumParcela() {
        return numParcela;
    }

    /**
     * @param numParcela the numParcela to set
     */
    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
    }

    /**
     * @return the valorParcela
     */
    public double getValorParcela() {
        return valorParcela;
    }

    /**
     * @param valorParcela the valorParcela to set
     */
    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    /**
     * @return the vencimento
     */
    public Date getVencimento() {
        return vencimento;
    }

    /**
     * @param vencimento the vencimento to set
     */
    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    /**
     * @return the compra
     */
    public Compra getCompra() {
        return compra;
    }

    /**
     * @param compra the compra to set
     */
    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    /**
     * @return the formaPagamento
     */
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * @param formaPagamento the formaPagamento to set
     */
    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

}
