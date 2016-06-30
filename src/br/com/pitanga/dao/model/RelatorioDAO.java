package br.com.pitanga.dao.model;

import br.com.pitanga.domain.Produto;
import br.com.pitanga.domain.Venda;
import java.util.Calendar;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author douglas
 * @date 12/09/2015
 * @time 09:13:50
 */
public interface RelatorioDAO {

    public JasperViewer gerarRelatorioClientesAnalitico();
    public JasperViewer gerarRelatorioClientesSintetico();
    public JasperViewer imprimirVenda(Venda venda);
    public JasperViewer gerarRelatorioVendaProduto(
            Produto produto, Calendar dataInicial, Calendar dataFinal);
    public JasperViewer gerarRelatorioValorMedio();

}
