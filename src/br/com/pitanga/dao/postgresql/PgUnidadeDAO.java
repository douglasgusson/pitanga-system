/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.model.UnidadeDAO;
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
public class PgUnidadeDAO implements UnidadeDAO {

    Connection connection;

    public PgUnidadeDAO() {
        connection = PostgreSQLDAOFactory.getConnection();
    }

    @Override
    public void inserir(Unidade unidade) {
        try {
            String SQL = "INSERT INTO unidade (cod_unidade, descricao_unidade)"
                    + "VALUES (?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                ps.setString(1, unidade.getIdUnidade());
                ps.setString(2, unidade.getDescricao());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir unidade em JDBCUnidadeDAO", ex);
        }
    }

    @Override
    public boolean remover(String id) {
        try {            
            String SQL = "DELETE FROM unidade WHERE cod_unidade = ?;";

            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, id);            
            int result = ps.executeUpdate();
            connection.close();

            return result > 0;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao remover unidade.", ex);
        }
    }

    @Override
    public List<Unidade> listarTodos() {
        try {
            List<Unidade> unidades = new ArrayList<>();

            String SQL = "SELECT * FROM unidade ORDER BY descricao_unidade;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Unidade unidade = new Unidade(
                            rs.getString("cod_unidade"),
                            rs.getString("descricao_unidade"));
                    unidades.add(unidade);
                }
            }
            connection.close();

            return unidades;

        } catch (SQLException ex) {
            Logger.getLogger(PgUnidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar Unidades em JDBCUnidadeDAO", ex);
        }
    }

    @Override
    public List<Unidade> buscarPorDescricao(String descricao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Unidade buscar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(Unidade unidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
