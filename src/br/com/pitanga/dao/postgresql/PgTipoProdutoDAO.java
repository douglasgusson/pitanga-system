
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.model.TipoProdutoDAO;
import br.com.pitanga.domain.TipoProduto;
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
public class PgTipoProdutoDAO implements TipoProdutoDAO {

    Connection connection;

    public PgTipoProdutoDAO() {
        connection = PostgreSQLDAOFactory.getConnection();
    }

    @Override
    public void inserir(TipoProduto tipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TipoProduto> listarTodos() {
        try {
            List<TipoProduto> tipos = new ArrayList<>();

            String SQL = "SELECT * FROM tipo_produto;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    TipoProduto tipo = new TipoProduto(
                            rs.getInt("cod_tipo_produto"),
                            rs.getString("descricao_tipo"));

                    tipos.add(tipo);
                }
            }
            connection.close();

            return tipos;

        } catch (SQLException ex) {
            Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar Tipos de produtos em JDBCTipoProdutoDAO", ex);
        }
    }

    @Override
    public List<TipoProduto> buscarPorDescricao(String descricao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoProduto buscar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(TipoProduto tipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
