/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.model.FormaPagamentoDAO;
import br.com.pitanga.domain.FormaPagamento;
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
public class PgFormaPagamentoDAO implements FormaPagamentoDAO {

    @Override
    public void inserir(FormaPagamento tipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FormaPagamento> listarTodos() {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        try {
            List<FormaPagamento> formas = new ArrayList<>();

            String SQL = "SELECT * FROM forma_pagamento;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    FormaPagamento fp = new FormaPagamento();
                    fp.setId_FormaPagamento(rs.getInt("cod_forma_pagamento"));
                    fp.setDescricao(rs.getString("descricao_forma"));

                    formas.add(fp);
                }
            }
            connection.close();

            return formas;

        } catch (SQLException ex) {
            Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar as Formas de Pagamento em JDBCFormaPagamentoDAO", ex);
        }
    }

    @Override
    public List<FormaPagamento> buscarPorDescricao(String descricao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FormaPagamento buscar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(FormaPagamento forma) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
