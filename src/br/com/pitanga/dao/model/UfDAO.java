
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Uf;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface UfDAO {
    
    public void inserir(Uf uf);    
    public void remover(String sigla);
    public List<Uf> listar();
    public Uf buscarPorSigla(String siglaUf);
    public void alterar(Uf uf);   
    
}
