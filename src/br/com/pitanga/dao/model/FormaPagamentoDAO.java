
package br.com.pitanga.dao.model;

import br.com.pitanga.domain.FormaPagamento;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public interface FormaPagamentoDAO {

    public void inserir(FormaPagamento forma);
    public void remover(int id);
    public List<FormaPagamento> listarTodos();
    public List<FormaPagamento> buscarPorDescricao(String descricao);
    public FormaPagamento buscar(int id);
    public void alterar(FormaPagamento forma);

}
