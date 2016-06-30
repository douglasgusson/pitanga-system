/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.model.FornecedorDAO;
import br.com.pitanga.domain.Cidade;
import br.com.pitanga.domain.Fornecedor;
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
 * @author Diêgo Biazate
 */
public class PgFornecedorDAO implements FornecedorDAO {

    @Override
    public void inserir(Fornecedor fornecedor) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        try {
            String SQL = "INSERT INTO fornecedor (nome_fornecedor, cpf_cnpj, rg_inscricao, "
                    + "endereco, numero, bairro, cep, telefone, celular, email, cod_cidade)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setString(1, fornecedor.getNomeFornecedor());
                ps.setString(2, fornecedor.getCpfCnpjFornecedor());
                ps.setString(3, fornecedor.getRgFornecedor());
                ps.setString(4, fornecedor.getEnderecoFornecedor());
                ps.setString(5, fornecedor.getNumeroFornecedor());
                ps.setString(6, fornecedor.getBairroFornecedor());
                ps.setString(7, fornecedor.getCepFornecedor());
                ps.setString(8, fornecedor.getTelefoneFornecedor());
                ps.setString(9, fornecedor.getCelularFornecedor());
                ps.setString(10, fornecedor.getEmailFornecedor());
                ps.setInt(11, fornecedor.getCidade().getIdCidade());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir fornecedor em JDBCFornecedorDAO", ex);
        }
    }

    @Override
    public void remover(int id) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            String SQL = "DELETE FROM fornecedor WHERE cod_fornecedor = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Fornecedor> listarTodos() {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        try {
            List<Fornecedor> fornecedores = new ArrayList<>();

            String SQL = "SELECT * FROM fornecedor ORDER BY nome_fornecedor;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Fornecedor fornecedore = new Fornecedor();
                    Cidade cidade = new Cidade();
                    fornecedore.setIdFornecedor(rs.getInt("cod_fornecedor"));
                    fornecedore.setNomeFornecedor(rs.getString("nome_fornecedor"));
                    fornecedore.setCpfCnpjFornecedor(rs.getString("cpf_cnpj"));
                    fornecedore.setRgFornecedor(rs.getString("rg_inscricao"));
                    fornecedore.setEnderecoFornecedor(rs.getString("endereco"));
                    fornecedore.setNumeroFornecedor(rs.getString("numero"));
                    fornecedore.setBairroFornecedor(rs.getString("bairro"));
                    fornecedore.setCepFornecedor(rs.getString("cep"));
                    cidade.setNomeCidade(rs.getString("cod_cidade"));
                    fornecedore.setCidade(cidade);
                    fornecedore.setTelefoneFornecedor(rs.getString("telefone"));
                    fornecedore.setCelularFornecedor(rs.getString("celular"));
                    fornecedore.setEmailFornecedor(rs.getString("email"));
                    fornecedores.add(fornecedore);
                }
            }
            connection.close();

            return fornecedores;

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar Fornecedores em JDBCFornecedorDAO", ex);
        }
    }

    @Override
    public List<Fornecedor> buscarPorNome(String nome) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        
        try {
            List<Fornecedor> fornecedores = new ArrayList<>();

            String SQL = "SELECT * FROM fornecedor WHERE nome_fornecedor LIKE '%" + nome + "%';";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Fornecedor fornecedor = new Fornecedor();
                    Cidade cidade = new Cidade();
                    fornecedor.setIdFornecedor(rs.getInt("cod_fornecedor"));
                    fornecedor.setNomeFornecedor(rs.getString("nome_fornecedor"));
                    fornecedor.setCpfCnpjFornecedor(rs.getString("cpf_cnpj"));
                    fornecedor.setRgFornecedor(rs.getString("rg_inscricao"));
                    fornecedor.setEnderecoFornecedor(rs.getString("endereco"));
                    fornecedor.setNumeroFornecedor(rs.getString("numero"));
                    fornecedor.setBairroFornecedor(rs.getString("bairro"));
                    fornecedor.setCepFornecedor(rs.getString("cep"));
                    cidade.setNomeCidade(rs.getString("cod_cidade"));
                    fornecedor.setCidade(cidade);
                    fornecedor.setTelefoneFornecedor(rs.getString("telefone"));
                    fornecedor.setCelularFornecedor(rs.getString("celular"));
                    fornecedor.setEmailFornecedor(rs.getString("email"));
                    fornecedores.add(fornecedor);
                }
            }
            connection.close();

            return fornecedores;

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar fornecedores em JDBCFornecedorDAO", ex);
        }
    }

    @Override
    public Fornecedor buscar(int id) {
        
        Connection connection = PostgreSQLDAOFactory.getConnection();
        Fornecedor fornecedor = new Fornecedor();

        try {
            String SQL = "SELECT * FROM fornecedor WHERE cod_fornecedor = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Cidade cidade = new Cidade();
                    fornecedor.setIdFornecedor(rs.getInt("cod_fornecedor"));
                    fornecedor.setNomeFornecedor(rs.getString("nome_fornecedor"));
                    fornecedor.setCpfCnpjFornecedor(rs.getString("cpf_cnpj"));
                    fornecedor.setRgFornecedor(rs.getString("rg_inscricao"));
                    fornecedor.setEnderecoFornecedor(rs.getString("endereco"));
                    fornecedor.setNumeroFornecedor(rs.getString("numero"));
                    fornecedor.setBairroFornecedor(rs.getString("bairro"));
                    fornecedor.setCepFornecedor(rs.getString("cep"));
                    cidade.setIdCidade(rs.getInt("cod_cidade"));
                    fornecedor.setCidade(cidade);
                    fornecedor.setTelefoneFornecedor(rs.getString("telefone"));
                    fornecedor.setCelularFornecedor(rs.getString("celular"));
                    fornecedor.setEmailFornecedor(rs.getString("email"));
                }
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fornecedor;
    }

    @Override
    public void alterar(Fornecedor fornecedor) {
        Connection connection = PostgreSQLDAOFactory.getConnection();
        try {
            String SQL = "UPDATE fornecedor "
                    + "SET nome_fornecedor = ?, "
                    + "cpf_cnpj = ?, "
                    + "rg_inscricao = ?, "
                    + "endereco = ?, "
                    + "numero = ?, "
                    + "bairro = ?, "
                    + "cep = ?, "
                    + "telefone = ?, "
                    + "celular = ?, "
                    + "email = ?, "
                    + "cod_cidade = ? "
                    + "WHERE cod_fornecedor = ?";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setString(1, fornecedor.getNomeFornecedor());
                ps.setString(2, fornecedor.getCpfCnpjFornecedor());
                ps.setString(3, fornecedor.getRgFornecedor());
                ps.setString(4, fornecedor.getEnderecoFornecedor());
                ps.setString(5, fornecedor.getNumeroFornecedor());
                ps.setString(6, fornecedor.getBairroFornecedor());
                ps.setString(7, fornecedor.getCepFornecedor());
                ps.setString(8, fornecedor.getTelefoneFornecedor());
                ps.setString(9, fornecedor.getCelularFornecedor());
                ps.setString(10, fornecedor.getEmailFornecedor());
                ps.setInt(11, fornecedor.getCidade().getIdCidade());
                ps.setInt(12, fornecedor.getIdFornecedor());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao alterar fornecedor em JDBCFornecedorDAO", ex);
        }
    }
}
