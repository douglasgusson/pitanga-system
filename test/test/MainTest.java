package test;


import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.dao.model.UsuarioDAO;
import br.com.pitanga.domain.Usuario;
import br.com.pitanga.util.Seguranca;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author douglas
 */
public class MainTest {
    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        usuario.setNome("admin");
        usuario.setSenha(Seguranca.criptografarSHA256("admin"));
        
        UsuarioDAO udao = DAOFactory.getDefaultDAOFactory().getUsuarioDAO();
        udao.inserir(usuario);
        
        
    }
}
