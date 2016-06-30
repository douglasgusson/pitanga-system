package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Usuario;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface UsuarioDAO {

    public List<Usuario> listarTodos();
    public void inserir(Usuario usuario);
    public void alterar(Usuario usuario);

}
