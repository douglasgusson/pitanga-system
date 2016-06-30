
package br.com.pitanga.domain;

import java.util.Date;

/**
 *
 * @author Douglas Gusson
 */
public class Recebimento {
    
    private int idRecebimento;
    private Date dataRecebimento;
    private double valor;
    private double desconto;
    private double multa;
    private double juros;
    private String observacoes;
    
    private FormaPagamento formaPagamento;
    private TituloVenda tituloVenda;

    public Recebimento(int idRecebimento, double desconto, double multa, 
            double juros, String observacoes, FormaPagamento formaPagamento, 
            TituloVenda tituloVenda) {
        this.idRecebimento = idRecebimento;
        this.desconto = desconto;
        this.multa = multa;
        this.juros = juros;
        this.observacoes = observacoes;
        this.formaPagamento = formaPagamento;
        this.tituloVenda = tituloVenda;
    }

    public Recebimento() {
    }
    

    /**
     * @return the idRecebimento
     */
    public int getIdRecebimento() {
        return idRecebimento;
    }

    /**
     * @param idRecebimento the idRecebimento to set
     */
    public void setIdRecebimento(int idRecebimento) {
        this.idRecebimento = idRecebimento;
    }

    /**
     * @return the desconto
     */
    public double getDesconto() {
        return desconto;
    }

    /**
     * @param desconto the desconto to set
     */
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    /**
     * @return the multa
     */
    public double getMulta() {
        return multa;
    }

    /**
     * @param multa the multa to set
     */
    public void setMulta(double multa) {
        this.multa = multa;
    }

    /**
     * @return the juros
     */
    public double getJuros() {
        return juros;
    }

    /**
     * @param juros the juros to set
     */
    public void setJuros(double juros) {
        this.juros = juros;
    }

    /**
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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

    /**
     * @return the tituloVenda
     */
    public TituloVenda getTituloVenda() {
        return tituloVenda;
    }

    /**
     * @param tituloVenda the tituloVenda to set
     */
    public void setTituloVenda(TituloVenda tituloVenda) {
        this.tituloVenda = tituloVenda;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the dataRecebimento
     */
    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    /**
     * @param dataRecebimento the dataRecebimento to set
     */
    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }
    
}
