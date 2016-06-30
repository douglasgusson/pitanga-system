package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.dao.model.ClienteDAO;
import br.com.pitanga.dao.model.EmpresaDAO;
import br.com.pitanga.dao.model.ItemVendaDAO;
import br.com.pitanga.dao.model.TituloVendaDAO;
import br.com.pitanga.dao.model.VendaDAO;
import br.com.pitanga.domain.Cidade;
import br.com.pitanga.domain.Cliente;
import br.com.pitanga.domain.Empresa;
import br.com.pitanga.domain.ItemVenda;
import br.com.pitanga.domain.Produto;
import br.com.pitanga.domain.TituloVenda;
import br.com.pitanga.domain.Uf;
import br.com.pitanga.domain.Venda;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public class PgVendaDAO implements VendaDAO {

    @Override
    public boolean inserir(Venda venda) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        java.util.Date utilDate = venda.getDataVenda();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            String SQL = "INSERT INTO venda (data_venda, valor_venda, "
                    + "valor_desconto, quant_parcela, observacoes, cod_cliente, "
                    + "cod_empresa) "
                    + "VALUES (?,?,?,?,?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setDate(1, sqlDate);
                ps.setDouble(2, venda.getValorVenda());
                ps.setDouble(3, venda.getValorDesconto());
                ps.setInt(4, venda.getQuantParcela());
                ps.setString(5, venda.getObservacoes());
                ps.setInt(6, venda.getCliente().getId());
                ps.setInt(7, 1); // venda.getEmpresa().getIdPessoa()

                int result = ps.executeUpdate();
                connection.close();
                return result > 0;
            }

        } catch (SQLException ex) {
            throw new DAOException("Falha ao inserir venda!", ex);
        }
    }

    @Override
    public boolean remover(Long idVenda) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            String SQL = "DELETE FROM venda WHERE cod_venda = ?;";

            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, idVenda);

            int result = ps.executeUpdate();
            connection.close();
            return result > 0;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao remover venda!", ex);
        }
    }

    @Override
    public List<Venda> listarTodos(int ordem) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            List<Venda> vendas = new ArrayList<>();

            StringBuilder query = new StringBuilder();

            query.append("SELECT \n"
                    + "  cod_venda, \n"
                    + "  data_venda, \n"
                    + "  valor_venda, \n"
                    + "  valor_desconto, \n"
                    + "  quant_parcela, \n"
                    + "  observacoes, \n"
                    + "  cod_cliente, \n"
                    + "  cod_empresa \n"
                    + "FROM venda;");

            String SQL = query.toString();

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Long idVenda = rs.getLong(1);
                    Date dataVenda = rs.getDate(2);
                    double valorVenda = rs.getDouble(3);
                    double valorDesconto = rs.getDouble(4);
                    int quantParcelas = rs.getInt(5);
                    String observacoes = rs.getString(6);
                    int idCliente = rs.getInt(7);
                    int idEmpresa = rs.getInt(8);

                    ClienteDAO clienteDAO = DAOFactory.getDefaultDAOFactory().getClienteDAO();
                    Cliente cliente = clienteDAO.buscarPorId(idCliente);

                    EmpresaDAO empresaDAO = DAOFactory.getDefaultDAOFactory().getEmpresaDAO();
                    Empresa empresa = empresaDAO.buscarPorId(idEmpresa);

                    ItemVendaDAO itemVendaDAO = DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
                    List<ItemVenda> itensVenda = itemVendaDAO.listarPorVenda(idVenda);

                    TituloVendaDAO tituloVendaDAO = DAOFactory.getDefaultDAOFactory().getTituloVendaDAO();
                    List<TituloVenda> titulosVenda = tituloVendaDAO.listarPorVenda(idVenda);

                    Venda v = new Venda(idVenda, dataVenda, valorVenda,
                            valorDesconto, quantParcelas, observacoes,
                            cliente, empresa, itensVenda, titulosVenda);

                    vendas.add(v);
                }
            }

            connection.close();

            return vendas;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao listar todas as vendas!", ex);
        }
    }

    @Override
    public List<Venda> buscarPorId(int idVenda) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            List<Venda> vendas = new ArrayList<>();

            String SQL = "SELECT * \n"
                    + "  FROM venda ve \n"
                    + "    INNER JOIN cliente cl ON ve.cod_cliente = cl.cod_cliente \n"
                    + "    INNER JOIN cidade ci ON cl.cod_cidade = ci.cod_cidade"
                    + "  WHERE ve.cod_venda = ?";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Venda venda = new Venda();
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("cod_cliente"));
                    cliente.setNome(rs.getString("nome_cliente"));
                    venda.setCliente(cliente);
                    venda.setIdVenda(rs.getLong("cod_venda"));
                    venda.setDataVenda(rs.getDate("data_venda"));
                    venda.setValorVenda(rs.getDouble("valor_venda"));

                    vendas.add(venda);
                }
            }
            connection.close();

            return vendas;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao buscar venda por id!", ex);
        }
    }

    @Override
    public Venda buscar(int idVenda) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        Venda venda = new Venda();

        try {
            String SQL = "SELECT * \n"
                    + "  FROM venda ve \n"
                    + "    INNER JOIN cliente cl ON ve.cod_cliente = cl.cod_cliente \n"
                    + "    INNER JOIN cidade ci ON cl.cod_cidade = ci.cod_cidade"
                    + "  WHERE ve.cod_venda = ?";

            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, idVenda);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cod_cliente"));
                cliente.setNome(rs.getString("nome_cliente"));
                venda.setCliente(cliente);
                venda.setIdVenda(rs.getLong("cod_venda"));
                venda.setDataVenda(rs.getDate("data_venda"));
                venda.setValorVenda(rs.getDouble("valor_venda"));
            }

            connection.close();
            return venda;
        } catch (SQLException ex) {
            throw new DAOException("Falha ao buscar venda!", ex);
        }
    }

    @Override
    public void alterar(Venda venda) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        java.util.Date utilDate = venda.getDataVenda();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String data = "'" + sqlDate + "'";

        try {

            Statement stm = connection.createStatement();

            String SQL = "UPDATE venda "
                    + "SET data_venda = " + data + ","
                    + "valor_venda = " + venda.getValorVenda() + ","
                    + "cod_cliente = " + venda.getCliente().getId()
                    + "WHERE cod_venda = " + venda.getIdVenda() + ";";

            System.out.print(SQL);

            System.out.print(stm.isClosed());

            stm.executeUpdate(SQL);

            connection.close();

        } catch (SQLException ex) {
            throw new DAOException("Falha ao alterar venda!", ex);
        }
    }

    @Override
    public Venda getUltimaVenda() {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        Venda venda = new Venda();

        try {

            String SQL = "SELECT * \n"
                    + "  FROM venda ve \n"
                    + "    INNER JOIN cliente cl ON ve.cod_cliente = cl.cod_cliente \n"
                    + "    INNER JOIN cidade ci ON cl.cod_cidade = ci.cod_cidade"
                    + "  WHERE ve.cod_venda = (SELECT MAX(cod_venda) FROM venda);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("cod_cliente"));
                    cliente.setNome(rs.getString("nome_cliente"));
                    cliente.setEmail(rs.getString("email"));
                    venda.setCliente(cliente);
                    venda.setIdVenda(rs.getLong("cod_venda"));
                    venda.setDataVenda(rs.getDate("data_venda"));
                    venda.setValorVenda(rs.getDouble("valor_venda"));
                }

                connection.close();
                return venda;
            }

        } catch (SQLException ex) {
            throw new DAOException("Falha ao pegar a última venda!", ex);
        }
    }

    @Override
    public boolean alterarVendaNova(Venda venda) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        java.util.Date utilDate = venda.getDataVenda();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {

            String SQL = "UPDATE venda "
                    + "SET data_venda = ?, "
                    + "valor_venda = ?, "
                    + "valor_desconto = ?, "
                    + "quant_parcela = ?, "
                    + "cod_cliente = ? "
                    + "WHERE cod_venda = ?;";

            PreparedStatement ps = connection.prepareStatement(SQL);

            ps.setDate(1, sqlDate);
            ps.setDouble(2, venda.getValorVenda());
            ps.setDouble(3, venda.getValorDesconto());
            ps.setInt(4, venda.getQuantParcela());
            ps.setInt(5, venda.getCliente().getId());
            ps.setLong(6, venda.getIdVenda());

            for (ItemVenda iv : venda.getItensVenda()) {
                ItemVenda item = new ItemVenda();
                Produto produto = new Produto();
                item.setDesconto(iv.getDesconto());
                produto.setIdProduto(iv.getProduto().getIdProduto());
                item.setProduto(produto);
                item.setQuantidade(iv.getQuantidade());
                item.setValor(iv.getValor());
                item.setVenda(venda);
                ItemVendaDAO itemDao = DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
                itemDao.inserir(item);
            }

            for (TituloVenda tv : venda.getTitulosVenda()) {
                TituloVenda titulo = new TituloVenda();
                titulo.setVenda(venda);
                titulo.setNumParcela(tv.getNumParcela());
                titulo.setValorParcela(tv.getValorParcela());
                titulo.setVencimento(tv.getVencimento());
                TituloVendaDAO tituloDAO = DAOFactory.getDefaultDAOFactory().getTituloVendaDAO();
                tituloDAO.inserir(titulo);
            }

            int result = ps.executeUpdate();

            connection.close();

            return result > 0;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao alterar venda!", ex);
        }
    }

    @Override
    public boolean alterarVenda(Venda venda) {
        Connection connection = PostgreSQLDAOFactory.getConnection();

        java.util.Date utilDate = venda.getDataVenda();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {

            String SQL = "UPDATE venda "
                    + "SET data_venda = ?, "
                    + "valor_venda = ?, "
                    + "valor_desconto = ?, "
                    + "quant_parcela = ?, "
                    + "cod_cliente = ? "
                    + "WHERE cod_venda = ?;";

            PreparedStatement ps = connection.prepareStatement(SQL);

            ps.setDate(1, sqlDate);
            ps.setDouble(2, venda.getValorVenda());
            ps.setDouble(3, venda.getValorDesconto());
            ps.setInt(4, venda.getQuantParcela());
            ps.setInt(5, venda.getCliente().getId());
            ps.setLong(6, venda.getIdVenda());

            for (ItemVenda iv : venda.getItensVenda()) {
                ItemVenda item = new ItemVenda();
                Produto produto = new Produto();
                item.setDesconto(iv.getDesconto());
                produto.setIdProduto(iv.getProduto().getIdProduto());
                item.setProduto(produto);
                item.setQuantidade(iv.getQuantidade());
                item.setValor(iv.getValor());
                item.setVenda(venda);
                ItemVendaDAO itemDao = DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
                itemDao.alterar(item);
            }

            for (TituloVenda tv : venda.getTitulosVenda()) {
                TituloVenda titulo = new TituloVenda();
                titulo.setVenda(venda);
                titulo.setNumParcela(tv.getNumParcela());
                titulo.setValorParcela(tv.getValorParcela());
                titulo.setVencimento(tv.getVencimento());
                TituloVendaDAO tituloDAO = DAOFactory.getDefaultDAOFactory().getTituloVendaDAO();
                tituloDAO.alterar(titulo);
            }

            int result = ps.executeUpdate();
            connection.close();
            return result > 0;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao alterar venda!", ex);
        }
    }

}
