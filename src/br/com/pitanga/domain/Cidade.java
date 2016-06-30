package br.com.pitanga.domain;

import java.util.Collection;

public class Cidade {

    private int idCidade;
    private String nomeCidade;
    private Uf uf;
    private Collection<Pessoa> pessoa;

    public Cidade(int idCidade, String nomeCidade, Uf uf) {
        this.idCidade = idCidade;
        this.nomeCidade = nomeCidade;
        this.uf = uf;
    }

    public Cidade() {
    }

    /**
     * @return the idCidade
     */
    public int getIdCidade() {
        return idCidade;
    }

    /**
     * @param idCidade the idCidade to set
     */
    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    /**
     * @return the nomeCidade
     */
    public String getNomeCidade() {
        return nomeCidade;
    }

    /**
     * @param nomeCidade the nomeCidade to set
     */
    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    /**
     * @return the uf
     */
    public String getUf() {
        return uf.getSiglaUf();
    }

    /**
     * @param uf the uf to set
     */
    public void setUf(Uf uf) {
        this.uf = uf;
    }

    /**
     * @return the pessoa
     */
    public Collection<Pessoa> getPessoa() {
        return pessoa;
    }

    /**
     * @param pessoa the pessoa to set
     */
    public void setPessoa(Collection<Pessoa> pessoa) {
        this.pessoa = pessoa;
    }

}
