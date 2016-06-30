package br.com.pitanga.domain;

import java.util.Calendar;

/**
 *
 * @author Douglas Gusson
 */
public class Usuario {

    private int id;
    private String nome;
    private String senha;
    private Calendar ultimoAcesso;
    private boolean ativo;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Calendar getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Calendar ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        String s = "Usuário: " + getNome() + "\n"
                + "Senha: " + getSenha() + "\n"
                + "Último acesso: " + getUltimoAcesso() + "\n"
                + "Ativo: " + isAtivo();

        return s;
    }

}
