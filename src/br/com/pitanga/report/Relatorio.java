
package br.com.pitanga.report;

import br.com.pitanga.domain.Cidade;
import br.com.pitanga.domain.Uf;
import java.io.InputStream;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Douglas Gusson
 */
public class Relatorio {

    public void gerarRelatorioEstados(List<Uf> estados) throws JRException {

        InputStream fonte = Relatorio.class.getResourceAsStream("/br/com/pitanga/report/RelatorioEstados.jrxml");

        JasperReport report = JasperCompileManager.compileReport(fonte);
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(estados));
        JasperViewer.viewReport(print, false);
    }

    public void gerarRelatorioCidades(List<Cidade> cidades) throws JRException {

        InputStream fonte = Relatorio.class.getResourceAsStream("/br/com/pitanga/report/RelatorioCidades.jrxml");

        JasperReport report = JasperCompileManager.compileReport(fonte);
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(cidades));
        JasperViewer.viewReport(print, false);
    }

}
