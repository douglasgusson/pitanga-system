
package br.com.pitanga.domain;

public class ClientePJ {

    private String razaoSocial;
    private String cnpj;
    private String inscricao;
    private String contato;

    public ClientePJ(String razaoSocial, String cnpj, String inscricao, 
            String contato, int id, String nome, String cpfCnpj, 
            String rgInscricao, String endereco, String numero, 
            String bairro, String cep, Cidade cidade, String telefone, 
            String celular, String email) {
        
        //super(id, nome, cpfCnpj, rgInscricao, endereco, numero, bairro, cep, cidade, telefone, celular, email);
        
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.inscricao = inscricao;
        this.contato = contato;
    }

    /**
     * @return the razaoSocial
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    /**
     * @param razaoSocial the razaoSocial to set
     */
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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
     * @return the contato
     */
    public String getContato() {
        return contato;
    }

    /**
     * @param contato the contato to set
     */
    public void setContato(String contato) {
        this.contato = contato;
    }
    

}
