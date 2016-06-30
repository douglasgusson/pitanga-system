package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.model.RecebimentoDAO;
import br.com.pitanga.domain.Recebimento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Douglas Gusson
 */
public class PgRecebimentoDAO implements RecebimentoDAO {

    @Override
    public boolean inserir(Recebimento recebimento) {
        try {

            java.util.Date utilDate = recebimento.getDataRecebimento();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            Connection con = PostgreSQLDAOFactory.getConnection();

            String SQL = "INSERT INTO recebimento ("
                    + "cod_forma_pagamento, cod_titulo_venda, data_recebimento, "
                    + "valor_recebido, desconto, multa, juros, observacoes)"
                    + "VALUES (?,?,?,?,?,?,?,?);";

            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, recebimento.getFormaPagamento().getId_FormaPagamento());
            ps.setInt(2, recebimento.getTituloVenda().getIdTitulo());
            ps.setDate(3, sqlDate);
            ps.setDouble(4, recebimento.getValor());
            ps.setDouble(5, recebimento.getDesconto());
            ps.setDouble(6, recebimento.getMulta());
            ps.setDouble(7, recebimento.getJuros());
            ps.setString(8, recebimento.getObservacoes());

            int result = ps.executeUpdate();
            return result == 1;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao inserir registro de recebimento"
                    + "\nERRO: " + ex,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}
