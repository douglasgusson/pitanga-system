
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Cliente;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface ClienteDAO {
    
    public void inserir(Cliente cliente);
    public boolean remover(int id);
    public List<Cliente> listarTodos();
    public List<Cliente> buscarPorNome(String nome);
    public Cliente buscarPorId(int id);
    public void alterar(Cliente cliente);

}
