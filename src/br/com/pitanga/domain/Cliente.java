
package br.com.pitanga.domain;

/**
 *
 * @author Douglas Gusson
 */
public class Cliente {

    private int id;
    private String nome;
    private String apelido;
    private String cpfCnpj;
    private String rgInscricao;
    private String endereco;
    private String numero;
    private String bairro;
    private String cep;
    private Cidade cidade;
    private String telefone;
    private String celular;
    private String contato;
    private String email;

    public Cliente(int id, String nome, String apelido, String cpfCnpj, 
            String rgInscricao, String endereco, String numero, 
            String bairro, String cep, Cidade cidade, String telefone, 
            String celular, String contato, String email) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.cpfCnpj = cpfCnpj;
        this.rgInscricao = rgInscricao;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.telefone = telefone;
        this.celular = celular;
        this.contato = contato;
        this.email = email;
    }

    public Cliente() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getRgInscricao() {
        return rgInscricao;
    }

    public void setRgInscricao(String rgInscricao) {
        this.rgInscricao = rgInscricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the apelido
     */
    public String getApelido() {
        return apelido;
    }

    /**
     * @param apelido the apelido to set
     */
    public void setApelido(String apelido) {
        this.apelido = apelido;
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
