
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.model.ItemVendaDAO;
import br.com.pitanga.domain.ItemVenda;
import br.com.pitanga.domain.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Douglas Gusson
 */
public class PgItemVendaDAO implements ItemVendaDAO {

    @Override
    public void inserir(ItemVenda item) {
        try {

            Connection connection = PostgreSQLDAOFactory.getConnection();

            String SQL = "INSERT INTO item_venda (cod_produto, cod_venda, quantidade, valor_unitario, desconto)"
                    + "VALUES (?,?,?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, item.getProduto().getIdProduto());
                ps.setLong(2, item.getVenda().getIdVenda());
                ps.setDouble(3, item.getQuantidade());
                ps.setDouble(4, item.getValor());
                ps.setDouble(5, item.getDesconto());

                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new DAOException("Falha ao inserir item_venda em JDBCItemVendaDAO", ex);
        }

    }

    @Override
    public List<ItemVenda> listarTodos() {
        try {
            Connection connection = PostgreSQLDAOFactory.getConnection();
            List<ItemVenda> itens = new ArrayList<>();
            String SQL = "SELECT * FROM item_venda;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            }
            return itens;

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar Clientes em JDBCClienteDAO", ex);
        }
    }

    @Override
    public List<ItemVenda> listarPorVenda(Long idVenda) {
        try {
            Connection connection = PostgreSQLDAOFactory.getConnection();
            List<ItemVenda> itens = new ArrayList<>();
            String SQL = "SELECT \n"
                    + "  iv.quantidade, \n"
                    + "  iv.valor_unitario, \n"
                    + "  iv.desconto, \n"
                    + "  pr.descricao_produto, \n"
                    + "  pr.quantidade, \n"
                    + "  pr.valor_venda, \n"
                    + "  pr.cod_produto \n"
                    + "FROM \n"
                    + "  item_venda iv \n"
                    + "  INNER JOIN produto pr ON iv.cod_produto = pr.cod_produto\n"
                    + "  WHERE iv.cod_venda = ?;";

            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, idVenda);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ItemVenda itemVenda = new ItemVenda();
                Produto produto = new Produto();
                itemVenda.setQuantidade(rs.getDouble(1));
                itemVenda.setValor(rs.getDouble(2));
                itemVenda.setDesconto(rs.getDouble(3));
                produto.setDescricao(rs.getString(4));
                produto.setQuantidade(rs.getDouble(5));
                produto.setValorVenda(rs.getDouble(6));
                produto.setIdProduto(rs.getInt(7));
                itemVenda.setProduto(produto);
                itens.add(itemVenda);
            }
            return itens;

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar itens em PgItemVendaDAO", ex);
        }
    }

    @Override
    public boolean alterar(ItemVenda item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
