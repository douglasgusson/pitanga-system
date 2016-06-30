/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.model.ItemCompraDAO;
import br.com.pitanga.domain.ItemCompra;
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
 * @author Netsul_suporte
 */
public class PgItemCompraDAO implements ItemCompraDAO {

    Connection connection;
    
    @Override
    public void inserir(ItemCompra item) {
        try {
            String SQL = "INSERT INTO item_compra (cod_produto, cod_compra, quantidade, valor_unitario, desconto)"
                    + "VALUES (?,?,?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, item.getProduto().getIdProduto());
                ps.setInt(2, item.getCompra().getIdCompra());
                ps.setDouble(3, item.getQuantidade());
                ps.setDouble(4, item.getValor());
                ps.setDouble(5, item.getDesconto());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgItemCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir item_compra em PgItemCompraDAO", ex);
        }
    }

    @Override
    public List<ItemCompra> listarTodos() {
        try {
            List<ItemCompra> itens = new ArrayList<>();

            String SQL = "SELECT * FROM item_compra;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                }
            }
            connection.close();

            return itens;

        } catch (SQLException ex) {
            Logger.getLogger(PgFornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar Fornecedores em PgFornecedorDAO", ex);
        }
    }

}
