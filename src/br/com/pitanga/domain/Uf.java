package br.com.pitanga.domain;

import java.util.Collection;

public class Uf {

    private String siglaUf;
    private String nomeUf;
    private Collection<Cidade> cidade;

    public Uf(String siglaUf, String nomeUf) {
        this.siglaUf = siglaUf;
        this.nomeUf = nomeUf;
    }

    public Uf() {
    }
    

    /**
     * @return the siglaUf
     */
    public String getSiglaUf() {
        return siglaUf;
    }

    /**
     * @param siglaUf the siglaUf to set
     */
    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
    }

    /**
     * @return the nomeUf
     */
    public String getNomeUf() {
        return nomeUf;
    }

    /**
     * @param nomeUf the nomeUf to set
     */
    public void setNomeUf(String nomeUf) {
        this.nomeUf = nomeUf;
    }

    /**
     * @return the cidade
     */
    public Collection<Cidade> getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(Collection<Cidade> cidade) {
        this.cidade = cidade;
    }

}
