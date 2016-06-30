/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.model.TituloCompraDAO;
import br.com.pitanga.domain.TituloCompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author douglas
 * @date 02/09/2015
 * @time 23:49:01
 */
public class PgTituloCompraDAO implements TituloCompraDAO {

    Connection connection;

    public PgTituloCompraDAO() {
        connection = PostgreSQLDAOFactory.getConnection();
    }

    @Override
    public void inserir(TituloCompra titulo) {

        java.util.Date utilDate = titulo.getVencimento();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            String SQL = "INSERT INTO titulo_compra (cod_compra, numero_parcela, valor_parcela, vencimento)"
                    + "VALUES (?,?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, titulo.getCompra().getIdCompra());
                ps.setInt(2, titulo.getNumParcela());
                ps.setDouble(3, titulo.getValorParcela());
                ps.setDate(4, sqlDate);

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgTituloCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir titulo_compra em JDBCTituloCompraDAO", ex);
        }
    }

    @Override
    public List<TituloCompra> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
