/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.model.FuncionarioDAO;
import br.com.pitanga.domain.Funcionario;
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
public class PgFuncionarioDAO implements FuncionarioDAO {

    @Override
    public void inserir(Funcionario funcionario) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            String SQL = "INSERT INTO funcionario (cod_funcionario, nome_funcionario, endereco, "
                    + "telefone, cpf)"
                    + "VALUES (?,?,?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, funcionario.getIdFuncionario());
                ps.setString(2, funcionario.getNome());;
                ps.setString(3, funcionario.getEndereco());
                ps.setString(4, funcionario.getTelefone());
                ps.setString(5, funcionario.getCpf());
                
                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgFuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir funcionario em JDBCFuncionarioDAO", ex);
        }
    }

    @Override
    public void remover(int id) {
        Connection connection = PostgreSQLDAOFactory.getConnection();
        try {
            String SQL = "DELETE FROM funcionario WHERE cod_funcionario = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgFuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Funcionario> listarTodos() {
        Connection connection = PostgreSQLDAOFactory.getConnection();
        try {
            List<Funcionario> funcionarios = new ArrayList<>();

            String SQL = "SELECT * FROM funcionario ORDER BY nome_funcionario;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    Funcionario funcionario = new Funcionario();
                    
                    funcionario.setIdFuncionario(rs.getInt("cod_funcionario"));
                    funcionario.setNome(rs.getString("nome_funcionario"));
                    funcionario.setEndereco(rs.getString("endereco"));
                    funcionario.setTelefone(rs.getString("telefone"));
                    funcionario.setCpf(rs.getString("cpf"));

                    
                    funcionarios.add(funcionario);
                }
            }
            connection.close();

            return funcionarios;

        } catch (SQLException ex) {
            Logger.getLogger(PgFuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar Clientes em JDBCClienteDAO", ex);
        }
    }

    @Override
    public List<Funcionario> buscarPorNome(String nome) {
        Connection connection = PostgreSQLDAOFactory.getConnection();
        try {
            List<Funcionario> funcionarios = new ArrayList<>();

            String SQL = "SELECT * FROM funcionario WHERE nome_funcionario LIKE '%"+nome+"%';";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
         
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setIdFuncionario(rs.getInt("cod_funcionario"));
                    funcionario.setNome(rs.getString("nome_funcionario"));
                    funcionario.setEndereco(rs.getString("endereco"));
                    funcionario.setTelefone(rs.getString("telefone"));
                    funcionario.setCpf(rs.getString("cpf"));
                    funcionarios.add(funcionario);
                }
            }
            connection.close();

            return funcionarios;

        } catch (SQLException ex) {
            Logger.getLogger(PgFuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar funcionarios em JDBCFuncionarioDAO", ex);
        }
    }

    @Override
    public Funcionario buscar(int idFuncionario) {
        Connection connection = PostgreSQLDAOFactory.getConnection();

        Funcionario funcionario = new Funcionario();

        try {
            String SQL = "SELECT * FROM funcionario WHERE cod_funcionario = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, idFuncionario);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    funcionario.setIdFuncionario(rs.getInt("cod_funcionario"));
                    funcionario.setNome(rs.getString("nome_funcionario"));
                    funcionario.setEndereco(rs.getString("endereco"));
                    funcionario.setTelefone(rs.getString("telefone"));
                    funcionario.setCpf(rs.getString("cpf"));
                }
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgFuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return funcionario;
    }

    @Override
    public void alterar(Funcionario funcionario) {
        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            String SQL = "UPDATE funcionario "
                    + "SET nome_funcionario = ?, "
                    + "endereco = ?, "
                    + "telefone = ?, "
                    + "cpf = ?, "
                    + "WHERE cod_funcionario = ?";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setString(1, funcionario.getNome());
                ps.setString(2, funcionario.getEndereco());
                ps.setString(3, funcionario.getTelefone());
                ps.setString(4, funcionario.getCpf());
                ps.setInt(5, funcionario.getIdFuncionario());
                
                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgFuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao alterar funcionario em JDBCFuncionarioDAO", ex);
        }
    }

}
