package br.com.pitanga.domain;

import java.text.DecimalFormat;
import java.util.Date;
import org.joda.time.DateTime;

public class TituloVenda {

    private int idTitulo;
    private int numParcela;
    private double valorParcela;
    private double valorRecebido;
    private Date vencimento;
    private Venda venda;

    public TituloVenda(int idTitulo, int numParcela, double valorParcela,
            double valorRecebido, Date vencimento, Venda venda) {
        this.idTitulo = idTitulo;
        this.numParcela = numParcela;
        this.valorParcela = valorParcela;
        this.valorRecebido = valorRecebido;
        this.vencimento = vencimento;
        this.venda = venda;
    }

    public TituloVenda() {
    }

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

    public String vencimentoToString() {
        DateTime dt = new DateTime(getVencimento());
        String data = dt.toString("dd/MM/yyyy");
        return data;
    }

    /**
     * @param vencimento the vencimento to set
     */
    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    /**
     * @return the venda
     */
    public Venda getVenda() {
        return venda;
    }

    /**
     * @param venda the venda to set
     */
    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    /**
     * @return the valorRecebido
     */
    public double getValorRecebido() {
        return valorRecebido;
    }

    /**
     * @param valorRecebido the valorRecebido to set
     */
    public void setValorRecebido(double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public String parcelaToString() {
        String str = String.format("%02d", getNumParcela())
                + " / " + String.format("%02d", getVenda().getQuantParcela());
        return str;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("##0.00");
        String valor = df.format(getValorParcela());
        String s = String.format("%02d", getNumParcela())
                + " - " + vencimentoToString()
                + " - " + valor;
        return s;
    }

}
