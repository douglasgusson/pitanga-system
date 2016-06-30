package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.dao.model.CidadeDAO;
import br.com.pitanga.dao.model.UfDAO;
import br.com.pitanga.domain.Cidade;
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
public class PgCidadeDAO implements CidadeDAO {

    @Override
    public void inserir(Cidade cidade) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into cidade(cod_cidade,nome_cidade,sigla_uf)"
                    + "values(?,?,?);");
            ps.setInt(1, cidade.getIdCidade());
            ps.setString(2, cidade.getNomeCidade());
            ps.setString(3, cidade.getUf());

            ps.executeUpdate();

            ps.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgCidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir cidade em JDBCCidadeDAO", ex);
        }
    }

    @Override
    public void remover(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cidade> listar() {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();

        List<Cidade> cidades = new ArrayList<>();

        try {
            String SQL = "SELECT * FROM cidade ORDER BY nome_cidade;";
            ResultSet rs;
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    Cidade cidade = new Cidade();
                    Uf estado = new Uf();

                    cidade.setIdCidade(rs.getInt("cod_cidade"));
                    cidade.setNomeCidade(rs.getString("nome_cidade"));
                    estado.setSiglaUf(rs.getString("sigla_uf"));
                    cidade.setUf(estado);
                    cidades.add(cidade);
                }
            }
            rs.close();

            return cidades;

        } catch (SQLException ex) {
            Logger.getLogger(PgCidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar cidades em JDBCCidadeDAO", ex);
        }
    }

    @Override
    public Cidade buscarPorId(int id) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        Cidade cidade = null;

        try {
            String SQL = "SELECT  \n"
                    + "      cod_cidade,\n"
                    + "      nome_cidade,\n"
                    + "      sigla_uf\n"
                    + "  FROM cidade \n"
                    + "    WHERE cod_cidade = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    int idCidade = rs.getInt(1);
                    String nome = rs.getString(2);
                    String siglaUf = rs.getString(3);

                    UfDAO ufDAO = DAOFactory.getDefaultDAOFactory().getUfDAO();
                    Uf uf = ufDAO.buscarPorSigla(siglaUf);

                    cidade = new Cidade(idCidade, nome, uf);
                }
            }

            connection.close();

            return cidade;

        } catch (SQLException ex) {
            throw new DAOException("Erro ao buscar cidade por id", ex);
        }
    }
    
    
    @Override
    public void alterar(Cidade cidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cidade> listarPorUf(String sigla) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();

        List<Cidade> cidades = new ArrayList<>();

        try {
            String SQL = "SELECT * FROM cidade WHERE sigla_uf = ?;";
            ResultSet rs;
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setString(1, sigla);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Cidade cidade = new Cidade();
                    Uf estado = new Uf();

                    cidade.setIdCidade(rs.getInt("cod_cidade"));
                    cidade.setNomeCidade(rs.getString("nome_cidade"));
                    estado.setSiglaUf(rs.getString("sigla_uf"));
                    cidade.setUf(estado);
                    cidades.add(cidade);
                }
            }
            rs.close();

            return cidades;

        } catch (SQLException ex) {
            Logger.getLogger(PgCidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar cidades em JDBCCidadeDAO", ex);
        }
    }

    @Override
    public List<Cidade> buscarPorNome(String nome) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        try {
            List<Cidade> cidades = new ArrayList<>();

            String SQL = "SELECT * FROM cidade "
                    + "WHERE LOWER(nome_cidade) LIKE '%" + nome.toLowerCase() + "%';";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Cidade cidade = new Cidade();
                    Uf uf = new Uf();
                    cidade.setIdCidade(rs.getInt("cod_cidade"));
                    cidade.setNomeCidade(rs.getString("nome_cidade"));
                    uf.setSiglaUf(rs.getString("sigla_uf"));
                    cidade.setUf(uf);
                    cidades.add(cidade);
                }
            }
            connection.close();

            return cidades;

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar clientes em JDBCClienteDAO", ex);
        }
    }

}
