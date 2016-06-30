package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.model.ProdutoDAO;
import br.com.pitanga.domain.Produto;
import br.com.pitanga.domain.TipoProduto;
import br.com.pitanga.domain.Unidade;
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
public class PgProdutoDAO implements ProdutoDAO {

    Connection connection;

    public PgProdutoDAO() {
        connection = PostgreSQLDAOFactory.getConnection();
    }

    @Override
    public void inserir(Produto produto) {

        try {
            String SQL
                    = "INSERT INTO produto (cod_tipo_produto, cod_unidade, "
                    + "descricao_produto, localizacao, valor_venda, quantidade)"
                    + "VALUES (?,?,?,?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                ps.setInt(1, produto.getTipoProduto().getIdTipo());
                ps.setString(2, produto.getUnidade().getIdUnidade());
                ps.setString(3, produto.getDescricao());
                ps.setString(4, produto.getLocalizacao());
                ps.setDouble(5, produto.getValorVenda());
                ps.setDouble(6, produto.getQuantidade());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir produto em JDBCProdutoDAO", ex);
        }
    }

    @Override
    public boolean remover(int idProduto) {
        try {
            String SQL = "DELETE FROM produto WHERE cod_produto = ?;";

            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, idProduto);
            int result = ps.executeUpdate();

            connection.close();

            return result > 0;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao remover produto.", ex);
        }
    }

    @Override
    public List<Produto> listarTodos() {
        try {
            List<Produto> produtos = new ArrayList<>();

            String SQL
                    = "SELECT \n"
                    + "      pr.cod_produto AS codigo, \n"
                    + "      pr.cod_unidade AS unidade, \n"
                    + "      ti.cod_tipo_produto AS cod_tipo, \n"
                    + "      ti.descricao_tipo AS tipo,\n"
                    + "      pr.descricao_produto AS descricao,\n"
                    + "      pr.localizacao AS localizacao,\n"
                    + "      pr.valor_venda AS valor,\n"
                    + "      pr.quantidade AS quantidade"
                    + "  FROM produto pr\n"
                    + "  INNER JOIN tipo_produto ti \n"
                    + "  ON pr.cod_tipo_produto = ti.cod_tipo_produto;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    TipoProduto tipo = new TipoProduto(rs.getInt("cod_tipo"), rs.getString("tipo"));
                    Unidade unidade = new Unidade(rs.getString("unidade"), "");

                    Produto produto = new Produto();
                    produto.setIdProduto(rs.getInt("codigo"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setValorVenda(rs.getDouble("valor"));
                    produto.setLocalizacao(rs.getString("localizacao"));
                    produto.setQuantidade(rs.getDouble("quantidade"));
                    produto.setTipoProduto(tipo);
                    produto.setUnidade(unidade);

                    produtos.add(produto);
                }
            }
            connection.close();

            return produtos;

        } catch (SQLException ex) {
            Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar Produtos em JDBCProdutoDAO", ex);
        }
    }

    @Override
    public List<Produto> buscarPorNome(String nome) {
        try {
            List<Produto> produtos = new ArrayList<>();

            String SQL
                    = "SELECT * FROM produto WHERE LOWER(descricao_produto) "
                    + "LIKE '%" + nome.toLowerCase() + "%';";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Produto produto = new Produto();
                    TipoProduto tipo = new TipoProduto();
                    Unidade unidade = new Unidade();
                    produto.setIdProduto(rs.getInt("cod_produto"));
                    produto.setDescricao(rs.getString("descricao_produto"));
                    produto.setValorVenda(rs.getDouble("valor_venda"));
                    tipo.setIdTipo(rs.getInt("cod_tipo_produto"));
                    produto.setTipoProduto(tipo);
                    unidade.setIdUnidade(rs.getString("cod_unidade"));
                    produto.setUnidade(unidade);
                    produtos.add(produto);
                }
            }
            connection.close();

            return produtos;

        } catch (SQLException ex) {
            Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar produtos em JDBCProdutoDAO", ex);
        }
    }

    @Override
    public Produto buscar(int idProduto) {

        Produto produto = new Produto();

        try {
            String SQL = "SELECT * FROM produto WHERE cod_produto = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, idProduto);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    TipoProduto tipo = new TipoProduto();
                    Unidade unidade = new Unidade();
                    produto.setIdProduto(rs.getInt("cod_produto"));
                    produto.setDescricao(rs.getString("descricao_produto"));
                    produto.setValorVenda(rs.getDouble("valor_venda"));
                    tipo.setIdTipo(rs.getInt("cod_tipo_produto"));
                    produto.setTipoProduto(tipo);
                    unidade.setIdUnidade(rs.getString("cod_unidade"));
                    produto.setUnidade(unidade);
                }
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produto;
    }

    @Override
    public void alterar(Produto produto) {

        try {
            String SQL = "UPDATE produto "
                    + "SET nome_produto = ?, "
                    + "id_tipo_produto = ?, "
                    + "preco = ?, "
                    + "WHERE cod_produto = ?";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setString(1, produto.getDescricao());
                ps.setInt(2, produto.getTipoProduto().getIdTipo());
                ps.setInt(3, produto.getIdProduto());
                ps.setDouble(4, produto.getValorVenda());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao alterar produto em JDBCProdutoDAO", ex);
        }
    }

    @Override
    public void alterarQuantidade(int qtd, int id) {
        try {
            String SQL = "UPDATE produto "
                    + "SET cod_produto = ?, "
                    + "WHERE quantidade = quantidade + ?";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, id);
                ps.setInt(2, qtd);
                
                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao alterar produto em JDBCProdutoDAO", ex);
        }
    }

}
