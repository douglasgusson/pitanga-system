/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.domain;

/**
 *
 * @author Diêgo Biazate
 */
public class Fornecedor {
   
    private int idFornecedor;
    private String nomeFornecedor;
    private String cpfCnpjFornecedor;
    private String rgFornecedor;
    private String enderecoFornecedor;
    private String numeroFornecedor;
    private String bairroFornecedor;
    private String cepFornecedor;
    private String telefoneFornecedor;
    private String celularFornecedor;
    private String emailFornecedor;
    private Cidade cidade;

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getCpfCnpjFornecedor() {
        return cpfCnpjFornecedor;
    }

    public void setCpfCnpjFornecedor(String cpfCnpjFornecedor) {
        this.cpfCnpjFornecedor = cpfCnpjFornecedor;
    }

    public String getRgFornecedor() {
        return rgFornecedor;
    }

    public void setRgFornecedor(String rgFornecedor) {
        this.rgFornecedor = rgFornecedor;
    }

    public String getEnderecoFornecedor() {
        return enderecoFornecedor;
    }

    public void setEnderecoFornecedor(String enderecoFornecedor) {
        this.enderecoFornecedor = enderecoFornecedor;
    }

    public String getNumeroFornecedor() {
        return numeroFornecedor;
    }

    public void setNumeroFornecedor(String numeroFornecedor) {
        this.numeroFornecedor = numeroFornecedor;
    }

    public String getBairroFornecedor() {
        return bairroFornecedor;
    }

    public void setBairroFornecedor(String bairroFornecedor) {
        this.bairroFornecedor = bairroFornecedor;
    }

    public String getCepFornecedor() {
        return cepFornecedor;
    }

    public void setCepFornecedor(String cepFornecedor) {
        this.cepFornecedor = cepFornecedor;
    }

    public String getTelefoneFornecedor() {
        return telefoneFornecedor;
    }

    public void setTelefoneFornecedor(String telefoneFornecedor) {
        this.telefoneFornecedor = telefoneFornecedor;
    }

    public String getCelularFornecedor() {
        return celularFornecedor;
    }

    public void setCelularFornecedor(String celularFornecedor) {
        this.celularFornecedor = celularFornecedor;
    }

    public String getEmailFornecedor() {
        return emailFornecedor;
    }

    public void setEmailFornecedor(String emailFornecedor) {
        this.emailFornecedor = emailFornecedor;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
    
}
