package br.com.pitanga.domain;

/**
 *
 * @author Douglas Gusson
 */
public class Empresa extends Pessoa {

    private String nomeFantasia;
    private String cnpj;
    private String inscricao;
    private String logomarca;

    public Empresa(String nomeFantasia, String cnpj, String inscricao,
            String logomarca, int idPessoa, String nome, String endereco,
            String numero, String bairro, String cep, String telefone,
            String celular, String email, Cidade cidade) {
        super(idPessoa, nome, endereco, numero, bairro, cep, telefone, celular, email, cidade);
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.inscricao = inscricao;
        this.logomarca = logomarca;
    }

    public Empresa() {
        super(0, null, null, null, null, null, null, null, null, null);
    }

    /**
     * @return the nomeFantasia
     */
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * @param nomeFantasia the nomeFantasia to set
     */
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the inscricao
     */
    public String getInscricao() {
        return inscricao;
    }

    /**
     * @param inscricao the inscricao to set
     */
    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    /**
     * @return the logomarca
     */
    public String getLogomarca() {
        return logomarca;
    }

    /**
     * @param logomarca the logomarca to set
     */
    public void setLogomarca(String logomarca) {
        this.logomarca = logomarca;
    }

}
