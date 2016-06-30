/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.model.CompraDAO;
import br.com.pitanga.domain.Fornecedor;
import br.com.pitanga.domain.Compra;
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
public class PgCompraDAO implements CompraDAO {


    @Override
    public void inserir(Compra compra) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();

        java.util.Date utilDate = compra.getDataCompra();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            String SQL = "INSERT INTO compra (data_compra, valor_compra, cod_fornecedor)"
                    + "VALUES (?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setDate(1, sqlDate);
                ps.setDouble(2, compra.getValorCompra());
                ps.setInt(3, compra.getFornecedor().getIdFornecedor());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir compra em JDBCCompraDAO", ex);
        }
    }

    @Override
    public void remover(int idCompra) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        try {
            String SQL = "DELETE FROM compra WHERE cod_compra = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, idCompra);
                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Compra> listarTodos() {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        try {
            List<Compra> compras = new ArrayList<>();

            String SQL = "SELECT * FROM compra ORDER BY cod_compra;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Compra compra = new Compra();
                    compra.setIdCompra(rs.getInt("cod_compra"));
                    compra.setValorCompra(rs.getFloat("valorCompra"));

                    compras.add(compra);
                }
            }
            connection.close();

            return compras;

        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar compras em JDBCCompraDAO", ex);
        }
    }

    @Override
    public List<Compra> buscarPorId(int idCompra) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        try {
            List<Compra> compras = new ArrayList<>();

            String SQL = "SELECT * FROM compra WHERE cod_compra LIKE '%" + "" + "%';";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Compra compra = new Compra();
                    compra.setIdCompra(rs.getInt("cod_compra"));
                    compra.setValorCompra(rs.getFloat("valorCompra"));

                    compras.add(compra);
                }
            }
            connection.close();

            return compras;

        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar compras em JDBCCompraDAO", ex);
        }
    }

    @Override
    public Compra buscar(int idCompra) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();

        Compra compra = new Compra();

        try {
            String SQL = "SELECT * FROM compra WHERE cod_compra = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, idCompra);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    compra.setIdCompra(rs.getInt("cod_compra"));
                    compra.setValorCompra(rs.getFloat("valorCompra"));
                }
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return compra;
    }

    @Override
    public void alterar(Compra compra) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();

        //JOptionPane.showMessageDialog(null, "JDBC " + venda.getIdVenda() + " " + venda.getCliente().getId() + " " + venda.getDataVenda() + " " + venda.getValorVenda());

        java.util.Date utilDate = compra.getDataCompra();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            String SQL = "UPDATE compra "
                    + "SET data_compra = ?, "
                    + "valor_compra = ?, "
                    + "cod_fornecedor = ? "
                    + "WHERE cod_fornecedor = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setDate(1, sqlDate);
                ps.setDouble(2, compra.getValorCompra());
                ps.setInt(3, compra.getFornecedor().getIdFornecedor());
                ps.setInt(4, compra.getIdCompra());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao alterar compra em JDBCCompraDAO", ex);
        }
    }

    @Override
    public Compra getUltimaCompra() {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        Compra compra = new Compra();

        try {
            String SQL = "SELECT * FROM compra"
                    + " WHERE cod_compra = (SELECT MAX(cod_compra) FROM compra); ";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Fornecedor fornecedor = new Fornecedor();
                    compra.setIdCompra(rs.getInt("cod_compra"));
                    fornecedor.setIdFornecedor(rs.getInt("cod_fornecedor"));
                    compra.setFornecedor(fornecedor);
                }
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return compra;
    }

}
