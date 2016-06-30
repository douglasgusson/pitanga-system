
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Cidade;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface CidadeDAO {
    
    public void inserir(Cidade cidade);
    public void remover(int id);
    public List<Cidade> listar();
    public Cidade buscarPorId(int id);
    public void alterar(Cidade cidade);
    public List<Cidade> listarPorUf(String sigla);
    List<Cidade> buscarPorNome(String nome);
    
}
