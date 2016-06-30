package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.model.TituloVendaDAO;
import br.com.pitanga.domain.Cliente;
import br.com.pitanga.domain.TituloVenda;
import br.com.pitanga.domain.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author douglas
 * @date 02/09/2015
 * @time 23:49:01
 */
public class PgTituloVendaDAO implements TituloVendaDAO {

    @Override
    public void inserir(TituloVenda titulo) {

        Connection connection = PostgreSQLDAOFactory.getConnection();
        java.util.Date utilDate = titulo.getVencimento();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            String SQL = "INSERT INTO titulo_venda (cod_venda, numero_parcela, valor_parcela, vencimento)"
                    + "VALUES (?,?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setLong(1, titulo.getVenda().getIdVenda());
                ps.setInt(2, titulo.getNumParcela());
                ps.setDouble(3, titulo.getValorParcela());
                ps.setDate(4, sqlDate);

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgTituloVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir titulo_venda em JDBCTituloVendaDAO", ex);
        }
    }

    @Override
    public List<TituloVenda> listarTodos() {

        Connection connection = PostgreSQLDAOFactory.getConnection();
        try {

            List<TituloVenda> titulos = new ArrayList<>();

            String query
                    = "SELECT \n"
                    + "  tv.cod_titulo_venda AS codigo,"
                    + "  tv.numero_parcela AS parcela, \n"
                    + "  tv.valor_recebido AS recebido,\n"
                    + "  cl.nome_cliente AS cliente,\n"
                    + "  ve.cod_venda AS venda,\n"
                    + "  ve.data_venda AS emissao, \n"
                    + "  ve.quant_parcela AS qtd_parcela, \n"
                    + "  tv.vencimento AS vencimento, \n"
                    + "  tv.valor_parcela AS valor\n"
                    + "FROM titulo_venda tv\n"
                    + "  INNER JOIN venda ve ON tv.cod_venda = ve.cod_venda \n"
                    + "  INNER JOIN cliente cl ON ve.cod_cliente = cl.cod_cliente \n"
                    + "ORDER BY ve.cod_venda;";

            PreparedStatement ps = connection.prepareStatement(
                    query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TituloVenda titulo = new TituloVenda();
                Venda venda = new Venda();
                Cliente cliente = new Cliente();

                titulo.setIdTitulo(rs.getInt(1));
                titulo.setNumParcela(rs.getInt(2));
                titulo.setValorRecebido(rs.getDouble(3));
                cliente.setNome(rs.getString(4));
                venda.setCliente(cliente);
                venda.setIdVenda(rs.getLong(5));
                venda.setDataVenda(rs.getDate(6));
                venda.setQuantParcela(rs.getInt(7));
                titulo.setVenda(venda);
                titulo.setVencimento(rs.getDate(8));
                titulo.setValorParcela(rs.getDouble(9));

                titulos.add(titulo);
            }

            connection.close();

            return titulos;

        } catch (SQLException ex) {
            throw new DAOException("Erro ao obter registros de títulos", ex);
        }
    }

    @Override
    public List<TituloVenda> listarFiltro(boolean vencimento, boolean emissao,
            Date dataInicio, Date dataFim) {

        Connection connection = PostgreSQLDAOFactory.getConnection();
        try {

            List<TituloVenda> titulos = new ArrayList<>();

            java.sql.Date data1 = new java.sql.Date(dataInicio.getTime());
            java.sql.Date data2 = new java.sql.Date(dataFim.getTime());

            StringBuilder query = new StringBuilder();

            query.append("SELECT \n"
                    + "  tv.cod_titulo_venda AS codigo,"
                    + "  tv.numero_parcela AS parcela, \n"
                    + "  tv.valor_recebido AS recebido,\n"
                    + "  cl.nome_cliente AS cliente,\n"
                    + "  ve.cod_venda AS venda,\n"
                    + "  ve.data_venda AS emissao, \n"
                    + "  ve.quant_parcela AS qtd_parcela, \n"
                    + "  tv.vencimento AS vencimento, \n"
                    + "  tv.valor_parcela AS valor\n"
                    + "FROM titulo_venda tv\n"
                    + "  INNER JOIN venda ve ON tv.cod_venda = ve.cod_venda \n"
                    + "  INNER JOIN cliente cl ON ve.cod_cliente = cl.cod_cliente");

            if (vencimento) {
                query.append(" \nWHERE vencimento ");
            } else if (emissao) {
                query.append("\nWHERE data_venda ");
            }

            query.append("BETWEEN ? AND ?");

            String SQL = query.toString();

            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setDate(1, data1);
            ps.setDate(2, data2);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TituloVenda titulo = new TituloVenda();
                Venda venda = new Venda();
                Cliente cliente = new Cliente();

                titulo.setIdTitulo(rs.getInt("codigo"));
                titulo.setNumParcela(rs.getInt("parcela"));
                titulo.setValorRecebido(rs.getDouble("recebido"));
                cliente.setNome(rs.getString("cliente"));
                venda.setCliente(cliente);
                venda.setIdVenda(rs.getLong("venda"));
                venda.setDataVenda(rs.getDate("emissao"));
                venda.setQuantParcela(rs.getInt("qtd_parcela"));
                titulo.setVenda(venda);
                titulo.setVencimento(rs.getDate("vencimento"));
                titulo.setValorParcela(rs.getDouble("valor"));

                titulos.add(titulo);

            }

            connection.close();
            return titulos;

        } catch (SQLException ex) {
            Logger.getLogger(PgTituloVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar Títulos com filtro em JDBCTituloVendaDAO", ex);
        }

    }

    @Override
    public List<TituloVenda> listarFiltro(boolean vencimento, boolean emissao,
            Date dataInicio, Date dataFim, int idCliente) {

        Connection connection = PostgreSQLDAOFactory.getConnection();
        try {

            List<TituloVenda> titulos = new ArrayList<>();

            java.sql.Date data1 = new java.sql.Date(dataInicio.getTime());
            java.sql.Date data2 = new java.sql.Date(dataFim.getTime());

            StringBuilder query = new StringBuilder();

            query.append("SELECT \n"
                    + "  tv.cod_titulo_venda AS codigo,"
                    + "  tv.numero_parcela AS parcela, \n"
                    + "  tv.valor_recebido AS recebido,\n"
                    + "  cl.nome_cliente AS cliente,\n"
                    + "  ve.cod_venda AS venda,\n"
                    + "  ve.data_venda AS emissao, \n"
                    + "  ve.quant_parcela AS qtd_parcela, \n"
                    + "  tv.vencimento AS vencimento, \n"
                    + "  tv.valor_parcela AS valor\n"
                    + "FROM titulo_venda tv\n"
                    + "  INNER JOIN venda ve ON tv.cod_venda = ve.cod_venda \n"
                    + "  INNER JOIN cliente cl ON ve.cod_cliente = cl.cod_cliente");

            if (vencimento) {
                query.append(" \nWHERE (vencimento ");
            } else if (emissao) {
                query.append("\nWHERE (data_venda ");
            }

            query.append("BETWEEN ? AND ?) ");
            query.append("AND (cl.cod_cliente = ?)");

            String SQL = query.toString();

            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setDate(1, data1);
            ps.setDate(2, data2);
            ps.setInt(3, idCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TituloVenda titulo = new TituloVenda();
                Venda venda = new Venda();
                Cliente cliente = new Cliente();

                titulo.setIdTitulo(rs.getInt("codigo"));
                titulo.setNumParcela(rs.getInt("parcela"));
                titulo.setValorRecebido(rs.getDouble("recebido"));
                cliente.setNome(rs.getString("cliente"));
                venda.setCliente(cliente);
                venda.setIdVenda(rs.getLong("venda"));
                venda.setDataVenda(rs.getDate("emissao"));
                venda.setQuantParcela(rs.getInt("qtd_parcela"));
                titulo.setVenda(venda);
                titulo.setVencimento(rs.getDate("vencimento"));
                titulo.setValorParcela(rs.getDouble("valor"));

                titulos.add(titulo);

            }

            connection.close();
            return titulos;

        } catch (SQLException ex) {
            Logger.getLogger(PgTituloVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar Títulos com filtro em JDBCTituloVendaDAO", ex);
        }

    }

    @Override
    public boolean atualizarRecebimentoTitulo(TituloVenda tituloVenda) {
        try {

            Connection con = PostgreSQLDAOFactory.getConnection();

            String query
                    = "UPDATE titulo_venda "
                    + "SET valor_recebido = valor_recebido + ? "
                    + "WHERE cod_titulo_venda = ?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, tituloVenda.getValorRecebido());
            ps.setInt(2, tituloVenda.getIdTitulo());

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao atualizar o registro de título"
                    + "\nERRO: " + ex,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public List<TituloVenda> listarPorVenda(Long idVenda) {
        try {

            Connection connection = PostgreSQLDAOFactory.getConnection();

            List<TituloVenda> titulos = new ArrayList<>();

            String SQL = "SELECT \n"
                    + "  cod_titulo_venda, \n"
                    + "  numero_parcela, \n"
                    + "  valor_parcela, \n"
                    + "  valor_recebido, \n"
                    + "  vencimento\n"
                    + "FROM \n"
                    + "  titulo_venda "
                    + "WHERE cod_venda = ? \n"
                    + "ORDER BY numero_parcela;";

            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, idVenda);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TituloVenda tituloVenda = new TituloVenda();
                tituloVenda.setIdTitulo(rs.getInt(1));
                tituloVenda.setNumParcela(rs.getInt(2));
                tituloVenda.setValorParcela(rs.getDouble(3));
                tituloVenda.setValorRecebido(rs.getDouble(4));
                tituloVenda.setVencimento(rs.getDate(5));
                titulos.add(tituloVenda);
            }
            return titulos;

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar titulos em PgTituloVendaDAO", ex);
        }

    }

    @Override
    public boolean alterar(TituloVenda titulo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TituloVenda> listarTodosAberto() {
        Connection connection = PostgreSQLDAOFactory.getConnection();
        try {

            List<TituloVenda> titulos = new ArrayList<>();

            String query
                    = "SELECT \n"
                    + "  tv.cod_titulo_venda AS codigo,"
                    + "  tv.numero_parcela AS parcela, \n"
                    + "  tv.valor_recebido AS recebido,\n"
                    + "  cl.nome_cliente AS cliente,\n"
                    + "  ve.cod_venda AS venda,\n"
                    + "  ve.data_venda AS emissao, \n"
                    + "  ve.quant_parcela AS qtd_parcela, \n"
                    + "  tv.vencimento AS vencimento, \n"
                    + "  tv.valor_parcela AS valor\n"
                    + "FROM titulo_venda tv\n"
                    + "  INNER JOIN venda ve ON tv.cod_venda = ve.cod_venda \n"
                    + "  INNER JOIN cliente cl ON ve.cod_cliente = cl.cod_cliente \n"
                    + "WHERE \n"
                    + "  tv.valor_parcela <> tv.valor_recebido \n"
                    + "ORDER BY ve.cod_venda;";

            PreparedStatement ps = connection.prepareStatement(
                    query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TituloVenda titulo = new TituloVenda();
                Venda venda = new Venda();
                Cliente cliente = new Cliente();

                titulo.setIdTitulo(rs.getInt(1));
                titulo.setNumParcela(rs.getInt(2));
                titulo.setValorRecebido(rs.getDouble(3));
                cliente.setNome(rs.getString(4));
                venda.setCliente(cliente);
                venda.setIdVenda(rs.getLong(5));
                venda.setDataVenda(rs.getDate(6));
                venda.setQuantParcela(rs.getInt(7));
                titulo.setVenda(venda);
                titulo.setVencimento(rs.getDate(8));
                titulo.setValorParcela(rs.getDouble(9));

                titulos.add(titulo);
            }

            connection.close();

            return titulos;

        } catch (SQLException ex) {
            throw new DAOException("Erro ao obter registros de títulos", ex);
        }
    }

}
