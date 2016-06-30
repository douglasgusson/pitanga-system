package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.dao.model.CidadeDAO;
import br.com.pitanga.dao.model.EmpresaDAO;
import br.com.pitanga.domain.Cidade;
import br.com.pitanga.domain.Empresa;
import br.com.pitanga.domain.Uf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Douglas Gusson
 */
public class PgEmpresaDAO implements EmpresaDAO {

    @Override
    public List<Empresa> listarTodos() {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            List<Empresa> empresas = new ArrayList<>();

            String SQL = "SELECT \n"
                    + "  cidade.cod_cidade,\n"
                    + "  cidade.nome_cidade, \n"
                    + "  cidade.sigla_uf, \n"
                    + "  empresa.cod_empresa, \n"
                    + "  empresa.razao_social, \n"
                    + "  empresa.nome_fantasia, \n"
                    + "  empresa.cnpj, \n"
                    + "  empresa.inscricao, \n"
                    + "  empresa.endereco, \n"
                    + "  empresa.numero, \n"
                    + "  empresa.bairro, \n"
                    + "  empresa.cep, \n"
                    + "  empresa.telefone, \n"
                    + "  empresa.celular, \n"
                    + "  empresa.email, \n"
                    + "  empresa.logomarca, \n"
                    + "  empresa.cod_cidade\n"
                    + "FROM \n"
                    + "  empresa, \n"
                    + "  cidade\n"
                    + "WHERE \n"
                    + "  empresa.cod_cidade = cidade.cod_cidade;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Cidade cidade = new Cidade();
                    Uf uf = new Uf();
                    cidade.setIdCidade(rs.getInt(1));
                    cidade.setNomeCidade(rs.getString(2));
                    uf.setSiglaUf(rs.getString(3));
                    cidade.setUf(uf);

                    Empresa empresa = new Empresa();
                    empresa.setIdPessoa(rs.getInt(4));
                    empresa.setNome(rs.getString(5));
                    empresa.setNomeFantasia(rs.getString(6));
                    empresa.setCnpj(rs.getString(7));
                    empresa.setInscricao(rs.getString(8));
                    empresa.setEndereco(rs.getString(9));
                    empresa.setNumero(rs.getString(10));
                    empresa.setBairro(rs.getString(11));
                    empresa.setCep(rs.getString(12));
                    empresa.setTelefone(rs.getString(13));
                    empresa.setCelular(rs.getString(14));
                    empresa.setEmail(rs.getString(15));
                    empresa.setLogomarca(rs.getString(16));
                    empresa.setCidade(cidade);

                    empresas.add(empresa);
                }
            }

            return empresas;

        } catch (SQLException ex) {
            Logger.getLogger(PgEmpresaDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(
                    "Falha ao listar Clientes em JDBCClienteDAO", ex);
        }
    }

    @Override
    public boolean alterar(Empresa empresa) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {

            String SQL = "UPDATE empresa \n"
                    + "SET \n"
                    + "razao_social = ?, \n"
                    + "nome_fantasia = ?,\n"
                    + "cnpj = ?, \n"
                    + "inscricao = ?, \n"
                    + "endereco = ?, \n"
                    + "numero = ?, \n"
                    + "bairro = ?, \n"
                    + "cep = ?, \n"
                    + "telefone = ?, \n"
                    + "celular = ?, \n"
                    + "email = ?,\n"
                    + "logomarca = ?, \n"
                    + "cod_cidade = ? \n"
                    + "WHERE cod_empresa = ?";

            PreparedStatement ps = connection.prepareStatement(SQL);

            ps.setString(1, empresa.getNome());
            ps.setString(2, empresa.getNomeFantasia());
            ps.setString(3, empresa.getCnpj());
            ps.setString(4, empresa.getInscricao());
            ps.setString(5, empresa.getEndereco());
            ps.setString(6, empresa.getNumero());
            ps.setString(7, empresa.getBairro());
            ps.setString(8, empresa.getCep());
            ps.setString(9, empresa.getTelefone());
            ps.setString(10, empresa.getCelular());
            ps.setString(11, empresa.getEmail());
            ps.setString(12, empresa.getLogomarca());
            ps.setInt(13, empresa.getCidade().getIdCidade());
            ps.setInt(14, empresa.getIdPessoa());

            int result = ps.executeUpdate();
            connection.close();
            return result > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao atualizar o registro da empresa " + "\nERRO : " + ex,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public Empresa buscarPorId(int id) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        Empresa empresa = new Empresa();

        try {
            String SQL = "SELECT \n"
                    + "      cod_empresa,\n"
                    + "      razao_social,\n"
                    + "      nome_fantasia,\n"
                    + "      cnpj,\n"
                    + "      inscricao,\n"
                    + "      endereco,\n"
                    + "      numero,\n"
                    + "      bairro,\n"
                    + "      cep,\n"
                    + "      telefone,\n"
                    + "      celular,\n"
                    + "      email,\n"
                    + "      logomarca,\n"
                    + "      cod_cidade\n"
                    + "  FROM empresa \n"
                    + "    WHERE cod_empresa = ?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int idEmpresa = rs.getInt(1);
                    String razaoSocial = rs.getString(2);
                    String nomeFantasia = rs.getString(3);
                    String cnpj = rs.getString(4);
                    String incricao = rs.getString(5);
                    String endereco = rs.getString(6);
                    String numero = rs.getString(7);
                    String bairro = rs.getString(8);
                    String cep = rs.getString(9);
                    String telefone = rs.getString(10);
                    String celular = rs.getString(11);
                    String email = rs.getString(12);
                    String logomarca = rs.getString(13);
                    int idCidade = rs.getInt(14);

                    CidadeDAO cidadeDAO = DAOFactory.getDefaultDAOFactory().getCidadeDAO();
                    Cidade c = cidadeDAO.buscarPorId(idCidade);

                    empresa = new Empresa(nomeFantasia, cnpj, incricao,
                            logomarca, idEmpresa, numero, endereco, numero,
                            bairro, cep, telefone, celular, email, c);

                }
            }
            connection.close();

            return empresa;

        } catch (SQLException ex) {
            throw new DAOException("Falha ao buscar empresa por id!", ex);
        }
    }

}
