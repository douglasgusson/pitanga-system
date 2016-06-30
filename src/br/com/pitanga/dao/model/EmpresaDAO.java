
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Empresa;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface EmpresaDAO {

    public List<Empresa> listarTodos();
    public boolean alterar(Empresa empresa);
    public Empresa buscarPorId(int id);

}
