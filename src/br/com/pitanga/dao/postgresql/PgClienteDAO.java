package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.dao.model.CidadeDAO;
import br.com.pitanga.dao.model.ClienteDAO;
import br.com.pitanga.domain.Cidade;
import br.com.pitanga.domain.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Douglas Gusson
 */
public class PgClienteDAO implements ClienteDAO {

    @Override
    public void inserir(Cliente cliente) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            String SQL = "INSERT INTO cliente (nome_cliente, apelido, cpf_cnpj, rg_inscricao, "
                    + "endereco, numero, bairro, cep, telefone, celular, contato,email, cod_cidade)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setString(1, cliente.getNome());
                ps.setString(2, cliente.getApelido());
                ps.setString(3, cliente.getCpfCnpj());
                ps.setString(4, cliente.getRgInscricao());
                ps.setString(5, cliente.getEndereco());
                ps.setString(6, cliente.getNumero());
                ps.setString(7, cliente.getBairro());
                ps.setString(8, cliente.getCep());
                ps.setString(9, cliente.getTelefone());
                ps.setString(10, cliente.getCelular());
                ps.setString(11, cliente.getContato());
                ps.setString(12, cliente.getEmail());
                ps.setInt(13, cliente.getCidade().getIdCidade());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            throw new DAOException("Falha ao inserir cliente!", ex);
        }
    }

    @Override
    public boolean remover(int id) {
        Connection connection = PostgreSQLDAOFactory.getConnection();
        try {
            String SQL = "DELETE FROM cliente WHERE cod_cliente = ?;";

            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, id);

            int result = ps.executeUpdate();

            return result > 0;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao excluir este cliente!", ex);
        }
    }

    @Override
    public List<Cliente> listarTodos() {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        List<Cliente> clientes = new ArrayList<>();

        try {
            String SQL = "SELECT \n"
                    + "      cod_cliente,\n"
                    + "      nome_cliente,\n"
                    + "      apelido,\n"
                    + "      cpf_cnpj,\n"
                    + "      rg_inscricao,\n"
                    + "      endereco,\n"
                    + "      numero,\n"
                    + "      bairro,\n"
                    + "      cep,\n"
                    + "      telefone,\n"
                    + "      celular,\n"
                    + "      email,\n"
                    + "      contato,\n"
                    + "      cod_cidade\n"
                    + "  FROM cliente;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    int idCliente = rs.getInt(1);
                    String nomeCliente = rs.getString(2);
                    String apelido = rs.getString(3);
                    String cpf = rs.getString(4);
                    String rg = rs.getString(5);
                    String endereco = rs.getString(6);
                    String numero = rs.getString(7);
                    String bairro = rs.getString(8);
                    String cep = rs.getString(9);
                    String telefone = rs.getString(10);
                    String celular = rs.getString(11);
                    String email = rs.getString(12);
                    String contato = rs.getString(13);                    
                    int idCidade = rs.getInt(14);

                    CidadeDAO cidadeDAO = DAOFactory.getDefaultDAOFactory().getCidadeDAO();
                    Cidade c = cidadeDAO.buscarPorId(idCidade);

                    cliente = new Cliente(idCliente, nomeCliente, apelido,
                            cpf, rg, endereco, numero, bairro, cep, c,
                            telefone, celular, contato, email);

                    clientes.add(cliente);
                }
            }
            connection.close();

            return clientes;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao listar todos os clientes!", ex);
        }
    }

    @Override
    public List<Cliente> buscarPorNome(String nome) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        List<Cliente> clientes = new ArrayList<>();

        try {
            String SQL = "SELECT \n"
                    + "      cod_cliente,\n"
                    + "      nome_cliente,\n"
                    + "      apelido,\n"
                    + "      cpf_cnpj,\n"
                    + "      rg_inscricao,\n"
                    + "      endereco,\n"
                    + "      numero,\n"
                    + "      bairro,\n"
                    + "      cep,\n"
                    + "      telefone,\n"
                    + "      celular,\n"
                    + "      email,\n"
                    + "      contato,\n"
                    + "      cod_cidade\n"
                    + "  FROM cliente \n"
                    + "    WHERE LOWER(nome_cliente) "
                    + "LIKE '%" + nome.toLowerCase() + "%';";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    int idCliente = rs.getInt(1);
                    String nomeCliente = rs.getString(2);
                    String apelido = rs.getString(3);
                    String cpf = rs.getString(4);
                    String rg = rs.getString(5);
                    String endereco = rs.getString(6);
                    String numero = rs.getString(7);
                    String bairro = rs.getString(8);
                    String cep = rs.getString(9);
                    String telefone = rs.getString(10);
                    String celular = rs.getString(11);
                    String email = rs.getString(12);
                    String contato = rs.getString(13);  
                    int idCidade = rs.getInt(14);

                    CidadeDAO cidadeDAO = DAOFactory.getDefaultDAOFactory().getCidadeDAO();
                    Cidade c = cidadeDAO.buscarPorId(idCidade);

                    cliente = new Cliente(idCliente, nomeCliente, apelido,
                            cpf, rg, endereco, numero, bairro, cep, c,
                            telefone, celular, contato, email);

                    clientes.add(cliente);
                }
            }
            connection.close();

            return clientes;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao buscar cliente por nome!", ex);
        }
    }

    @Override
    public Cliente buscarPorId(int id) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        Cliente cliente = new Cliente();

        try {
            String SQL = "SELECT \n"
                    + "      cod_cliente,\n"
                    + "      nome_cliente,\n"
                    + "      apelido,\n"
                    + "      cpf_cnpj,\n"
                    + "      rg_inscricao,\n"
                    + "      endereco,\n"
                    + "      numero,\n"
                    + "      bairro,\n"
                    + "      cep,\n"
                    + "      telefone,\n"
                    + "      celular,\n"
                    + "      email,\n"
                    + "      contato,\n"
                    + "      cod_cidade\n"
                    + "  FROM cliente \n"
                    + "    WHERE cod_cliente = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int idCliente = rs.getInt(1);
                    String nomeCliente = rs.getString(2);
                    String apelido = rs.getString(3);
                    String cpf = rs.getString(4);
                    String rg = rs.getString(5);
                    String endereco = rs.getString(6);
                    String numero = rs.getString(7);
                    String bairro = rs.getString(8);
                    String cep = rs.getString(9);
                    String telefone = rs.getString(10);
                    String celular = rs.getString(11);
                    String email = rs.getString(12);
                    String contato = rs.getString(13);  
                    int idCidade = rs.getInt(14);

                    CidadeDAO cidadeDAO = DAOFactory.getDefaultDAOFactory().getCidadeDAO();
                    Cidade c = cidadeDAO.buscarPorId(idCidade);

                    cliente = new Cliente(idCliente, nomeCliente, apelido,
                            cpf, rg, endereco, numero, bairro, cep, c,
                            telefone, celular, contato, email);
                }
            }
            connection.close();
            return cliente;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao buscar cliente por id!", ex);
        }
    }

    @Override
    public void alterar(Cliente cliente) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            String SQL
                    = "UPDATE cliente "
                    + "SET nome_cliente = ?, "
                    + "apelido = ?,"
                    + "cpf_cnpj = ?, "
                    + "rg_inscricao = ?, "
                    + "endereco = ?, "
                    + "numero = ?, "
                    + "bairro = ?, "
                    + "cep = ?, "
                    + "telefone = ?, "
                    + "celular = ?, "
                    + "email = ?, "
                    + "contato = ?, "
                    + "cod_cidade = ? "
                    + "WHERE cod_cliente = ?";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setString(1, cliente.getNome());
                ps.setString(2, cliente.getApelido());
                ps.setString(3, cliente.getCpfCnpj());
                ps.setString(4, cliente.getRgInscricao());
                ps.setString(5, cliente.getEndereco());
                ps.setString(6, cliente.getNumero());
                ps.setString(7, cliente.getBairro());
                ps.setString(8, cliente.getCep());
                ps.setString(9, cliente.getTelefone());
                ps.setString(10, cliente.getCelular());
                ps.setString(11, cliente.getEmail());
                ps.setString(12, cliente.getContato());
                ps.setInt(13, cliente.getCidade().getIdCidade());
                ps.setInt(14, cliente.getId());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            throw new DAOException("Falha ao alterar cliente!", ex);
        }
    }
}
