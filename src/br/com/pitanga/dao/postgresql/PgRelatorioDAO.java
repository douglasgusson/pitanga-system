package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.model.RelatorioDAO;
import br.com.pitanga.domain.Produto;
import br.com.pitanga.domain.Venda;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author douglas
 * @date 12/09/2015
 * @time 09:18:24
 */
public class PgRelatorioDAO implements RelatorioDAO {

    @Override
    public JasperViewer gerarRelatorioClientesAnalitico() {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            Statement stm = connection.createStatement();
            String query = "SELECT\n"
                    + "cl.cod_cliente AS codigo,\n"
                    + "cl.nome_cliente AS nome,\n"
                    + "cl.cpf_cnpj AS cpf_cnpj,\n"
                    + "cl.rg_inscricao AS rg_insc,\n"
                    + "cl.endereco AS endereco,\n"
                    + "cl.numero AS num,\n"
                    + "cl.bairro AS bairro,\n"
                    + "cl.cep AS cep,\n"
                    + "ci.nome_cidade AS nome_cidade,\n"
                    + "ci.sigla_uf AS sigla,\n"
                    + "cl.telefone AS telefone,\n"
                    + "cl.celular AS celular,\n"
                    + "cl.email AS email\n"
                    + "FROM cliente cl "
                    + "INNER JOIN cidade ci ON cl.cod_cidade = ci.cod_cidade\n"
                    + "ORDER BY cl.nome_cliente;";

            ResultSet rs = stm.executeQuery(query);

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            InputStream fonte
                    = PgRelatorioDAO.class.getResourceAsStream(
                            "/br/com/pitanga/report/RelatorioClientesAnalitico.jrxml");

            JasperReport report = JasperCompileManager.compileReport(fonte);

            JasperPrint print = JasperFillManager.fillReport(report, null, jrRS);
            JasperViewer jv = new JasperViewer(print, false);

            return jv;

        } catch (JRException | SQLException ex) {
            throw new DAOException(
                    "Falha ao gerar relatório analítico de "
                    + "clientes em JDBCRelatorioDAO", ex);
        }
    }

    @Override
    public JasperViewer gerarRelatorioClientesSintetico() {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            Statement stm = connection.createStatement();
            String query = "SELECT\n"
                    + "     cl.\"cod_cliente\" AS codigo,\n"
                    + "     cl.\"nome_cliente\" AS nome,\n"
                    + "     ci.\"nome_cidade\" AS nome_cidade,\n"
                    + "     ci.\"sigla_uf\" AS sigla,\n"
                    + "     cl.\"telefone\" AS telefone\n"
                    + "FROM\n"
                    + "     \"cliente\" cl INNER JOIN \"cidade\" ci "
                    + "ON cl.\"cod_cidade\" = ci.\"cod_cidade\"\n"
                    + "ORDER BY\n"
                    + "     cl.nome_cliente ASC";

            ResultSet rs = stm.executeQuery(query);

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            InputStream fonte
                    = PgRelatorioDAO.class.getResourceAsStream(
                            "/br/com/pitanga/report/RelatorioClientesSintetico.jrxml");

            JasperReport report = JasperCompileManager.compileReport(fonte);

            JasperPrint print = JasperFillManager.fillReport(report, null, jrRS);
            JasperViewer jv = new JasperViewer(print, false);

            return jv;

        } catch (JRException | SQLException ex) {
            throw new DAOException(
                    "Falha ao gerar relatório sintético "
                    + "de clientes em JDBCRelatorioDAO", ex);
        }
    }

    @Override
    public JasperViewer imprimirVenda(Venda venda) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {

            Statement stm = connection.createStatement();

            String query = "SELECT\n"
                    + "     *,\n"
                    + "     ve.\"cod_venda\" AS ve_cod_venda,\n"
                    + "     ve.\"data_venda\" AS ve_data_venda,\n"
                    + "     ve.\"valor_venda\" AS ve_valor_venda,\n"
                    + "     ve.\"cod_cliente\" AS ve_cod_cliente,\n"
                    + "     cl.\"cod_cliente\" AS cl_cod_cliente,\n"
                    + "     cl.\"nome_cliente\" AS cl_nome_cliente,\n"
                    + "     cl.\"cpf_cnpj\" AS cl_cpf_cnpj,\n"
                    + "     cl.\"rg_inscricao\" AS cl_rg_inscricao,\n"
                    + "     cl.\"endereco\" AS cl_endereco,\n"
                    + "     cl.\"numero\" AS cl_numero,\n"
                    + "     cl.\"bairro\" AS cl_bairro,\n"
                    + "     cl.\"cep\" AS cl_cep,\n"
                    + "     cl.\"telefone\" AS cl_telefone,\n"
                    + "     cl.\"celular\" AS cl_celular,\n"
                    + "     cl.\"email\" AS cl_email,\n"
                    + "     cl.\"cod_cidade\" AS cl_cod_cidade,\n"
                    + "     ci.\"nome_cidade\" AS ci_nome_cidade,\n"
                    + "     ci.\"sigla_uf\" AS ci_sigla_uf,\n"
                    + "     iv.\"desconto\" AS iv_desconto,\n"
                    + "     iv.\"valor_unitario\" AS iv_valor_unitario,\n"
                    + "     iv.\"quantidade\" AS iv_quantidade,\n"
                    + "     iv.\"cod_produto\" AS iv_cod_produto,\n"
                    + "     pro.\"cod_produto\" AS pro_cod_produto,\n"
                    + "     pro.\"cod_unidade\" AS pro_cod_unidade,\n"
                    + "     pro.\"descricao_produto\" AS pro_descricao_produto,\n"
                    + "     tp.\"descricao_tipo\" AS tp_descricao_tipo,\n"
                    + "     ve.\"valor_desconto\" AS ve_valor_desconto,\n"
                    + "     ve.\"observacoes\" AS ve_observacoes\n"
                    + "FROM\n"
                    + "     \"cliente\" cl INNER JOIN \"venda\" ve ON cl.\"cod_cliente\" = ve.\"cod_cliente\"\n"
                    + "     INNER JOIN \"cidade\" ci ON cl.\"cod_cidade\" = ci.\"cod_cidade\"\n"
                    + "     INNER JOIN \"item_venda\" iv ON ve.\"cod_venda\" = iv.\"cod_venda\"\n"
                    + "     INNER JOIN \"produto\" pro ON iv.\"cod_produto\" = pro.\"cod_produto\"\n"
                    + "     INNER JOIN \"tipo_produto\" tp ON pro.\"cod_tipo_produto\" = tp.\"cod_tipo_produto\"\n"
                    + "WHERE\n"
                    + "     ve.\"cod_venda\" = " + venda.getIdVenda() + ";";

            ResultSet rs = stm.executeQuery(query);
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            InputStream fonte = PgRelatorioDAO.class.getResourceAsStream(
                    "/br/com/pitanga/report/ImpressaoVenda.jrxml");
            JasperReport report = JasperCompileManager.compileReport(fonte);

            URL realPath = getClass().getResource("/br/com/pitanga/report/");

            Map<String, Object> params = new HashMap<>();
            params.put("REPORT_CONNECTION", connection);
            params.put("SUBREPORT_DIR", realPath);

            JasperPrint print = JasperFillManager.fillReport(report, params, jrRS);
            JasperViewer jv = new JasperViewer(print, false);

            if (!new File("vendas-pdf/").exists()) {
                (new File("vendas-pdf/")).mkdir();
            }

            JasperExportManager.exportReportToPdfFile(
                    print, venda.getCaminhoArquivoPDF());

            return jv;

        } catch (SQLException | JRException e) {
            throw new DAOException(
                    "Falha ao gerar relatório da venda em JDBCRelatorioDAO", e);
        }
    }

    @Override
    public JasperViewer gerarRelatorioVendaProduto(
            Produto produto, Calendar dataInicial, Calendar dataFinal) {

        java.sql.Date inicio = new java.sql.Date(dataInicial.getTimeInMillis());
        java.sql.Date fim = new java.sql.Date(dataFinal.getTimeInMillis());

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            Statement stm = connection.createStatement();

            StringBuilder query = new StringBuilder();

            query.append("SELECT\n"
                    + "     ve.cod_venda AS venda_cod_venda,\n"
                    + "     cl.nome_cliente AS cliente_nome_cliente,\n"
                    + "     ve.data_venda AS venda_data_venda,\n"
                    + "     pr.descricao_produto AS produto_descricao_produto,\n"
                    + "     iv.quantidade AS item_venda_quantidade,\n"
                    + "     iv.valor_unitario AS item_venda_valor_unitario\n"
                    + "FROM\n"
                    + "     venda ve INNER JOIN item_venda iv ON ve.cod_venda = iv.cod_venda\n"
                    + "     INNER JOIN cliente cl ON ve.cod_cliente = cl.cod_cliente\n"
                    + "     INNER JOIN produto pr ON iv.cod_produto = pr.cod_produto\n");

            query.append(" WHERE pr.cod_produto = ")
                    .append(produto.getIdProduto())
                    .append(" AND ve.data_venda BETWEEN ")
                    .append("'").append(inicio).append("'")
                    .append(" AND ")
                    .append("'").append(fim).append("'");

            String SQL = query.toString();

            ResultSet rs = stm.executeQuery(SQL);

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            InputStream fonte
                    = PgRelatorioDAO.class.getResourceAsStream(
                            "/br/com/pitanga/report/RelatorioVendaPorProduto.jrxml");

            JasperReport report = JasperCompileManager.compileReport(fonte);

            JasperPrint print = JasperFillManager.fillReport(report, null, jrRS);
            JasperViewer jv = new JasperViewer(print, false);

            return jv;

        } catch (JRException | SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public JasperViewer gerarRelatorioValorMedio() {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {

            Statement stm = connection.createStatement();

            String query = "SELECT\n"
                    + "       p.cod_produto,\n"
                    + "       p.descricao_produto,\n"
                    + "       AVG(iv.valor_unitario) AS valor_medio\n"
                    + "  FROM produto p\n"
                    + "  INNER JOIN item_venda iv ON iv.cod_produto = p.cod_produto\n"
                    + "  GROUP BY (p.cod_produto);";

            ResultSet rs = stm.executeQuery(query);

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            InputStream fonte
                    = PgRelatorioDAO.class.getResourceAsStream(
                            "/br/com/pitanga/report/RelatorioValorMedioVenda.jrxml");

            JasperReport report = JasperCompileManager.compileReport(fonte);

            JasperPrint print = JasperFillManager.fillReport(report, null, jrRS);
            JasperViewer jv = new JasperViewer(print, false);

            return jv;

        } catch (SQLException | JRException ex) {
            throw new DAOException("Falha ao gerar relatório de valor médio!", ex);
        }
    }

}
