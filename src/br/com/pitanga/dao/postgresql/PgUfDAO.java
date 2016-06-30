
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.model.UfDAO;
import br.com.pitanga.domain.Uf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public class PgUfDAO implements UfDAO {

    @Override
    public void inserir(Uf uf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(String sigla) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Uf> listar() {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();

        List<Uf> estados = new ArrayList<>();

        try {
            String SQL = "SELECT * FROM uf";
            ResultSet rs;
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    Uf estado = new Uf();
                    estado.setSiglaUf(rs.getString("sigla_uf"));
                    estado.setNomeUf(rs.getString("nome_uf"));
                    estados.add(estado);
                }
            }
            rs.close();

            return estados;

        } catch (SQLException ex) {
            Logger.getLogger(PgUfDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar estados em JDBCUfDAO", ex);
        }
    }

    @Override
    public Uf buscarPorSigla(String siglaUf) {
        try {
            Connection connection = PostgreSQLDAOFactory.getConnection();

            Uf uf = null;

            String query = "SELECT  \n"
                    + "      sigla_uf,\n"
                    + "      nome_uf\n"
                    + "  FROM uf\n"
                    + "    WHERE sigla_uf = ?;";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, siglaUf);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String sigla = rs.getString(1);
                String nome = rs.getString(2);

                uf = new Uf(siglaUf, nome);

            }

            return uf;

        } catch (SQLException ex) {
            throw new DAOException("Erro ao buscar UF por sigla.", ex);
        }
    }

    @Override
    public void alterar(Uf uf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
