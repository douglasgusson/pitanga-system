package br.com.pitanga.domain;

import br.com.pitanga.util.StringUtils;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;

public class Venda {

    private Long idVenda;
    private Date dataVenda;
    private double valorVenda;
    private double valorDesconto;
    private int quantParcela;
    private String observacoes;
    private Cliente cliente;
    private Empresa empresa;
    private Usuario usuario;
    private Funcionario funcionario;
    private List<ItemVenda> itensVenda;
    private List<TituloVenda> titulosVenda;


    public Venda(Long idVenda, Date dataVenda, double valorVenda,
            double valorDesconto, int quantParcela, String observacoes,
            Cliente cliente, Empresa empresa, List<ItemVenda> itensVenda,
            List<TituloVenda> titulosVenda) {
        this.idVenda = idVenda;
        this.dataVenda = dataVenda;
        this.valorVenda = valorVenda;
        this.valorDesconto = valorDesconto;
        this.quantParcela = quantParcela;
        this.observacoes = observacoes;
        this.cliente = cliente;
        this.empresa = empresa;
        this.itensVenda = itensVenda;
        this.titulosVenda = titulosVenda;
    }

    public Venda() {
    }

    /**
     * @return the idVenda
     */
    public Long getIdVenda() {
        return idVenda;
    }

    public String idVendaToString() {
        return String.format("%05d", getIdVenda());
    }

    /**
     * @param idVenda the idVenda to set
     */
    public void setIdVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    /**
     * @return the dataVenda
     */
    public Date getDataVenda() {
        return dataVenda;
    }

    public String getDataVendaFormatada() {
        DateTime dt = new DateTime(getDataVenda());
        String data = dt.toString("dd/MM/yyyy");
        return data;
    }

    /**
     * @param dataVenda the dataVenda to set
     */
    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
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
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the itensVenda
     */
    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    /**
     * @param itensVenda the itensVenda to set
     */
    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    /**
     * @return the titulosVenda
     */
    public List<TituloVenda> getTitulosVenda() {
        return titulosVenda;
    }

    /**
     * @param titulosVenda the titulosVenda to set
     */
    public void setTitulosVenda(List<TituloVenda> titulosVenda) {
        this.titulosVenda = titulosVenda;
    }

    /**
     * @return the funcionario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * @return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return Exemplo: 00000000 - Fulano de Tal
     */
    public String getCaminhoArquivoPDF() {
        return "vendas-pdf/" + StringUtils.inteiroOitoDig(getIdVenda())
                + " - " + getCliente().getNome() + ".pdf";
    }

    /**
     * @return Exemplo: Venda Nº 00000000 - Fulano de Tal
     */
    public String getDescricaoTela() {
        return "Venda Nº " + StringUtils.inteiroOitoDig(getIdVenda())
                + " - " + getCliente().getNome();
    }

}
