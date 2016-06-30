package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.model.UsuarioDAO;
import br.com.pitanga.domain.Usuario;
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
public class PgUsuarioDAO implements UsuarioDAO {

    @Override
    public List<Usuario> listarTodos() {

        Connection con = PostgreSQLDAOFactory.getConnection();
        List<Usuario> usuarios = new ArrayList<>();

        try {
            String query = "SELECT "
                    + "nome_usuario, "
                    + "senha, "
                    + "ultimo_acesso, "
                    + "ativo "
                    + "FROM usuario;";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNome(rs.getString(1));
                u.setSenha(rs.getString(2));
                //u.setUltimoAcesso(rs.getTimestamp(3), Calendar.getInstance());
                u.setAtivo(rs.getBoolean(4));

                usuarios.add(u);
            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao listar usuarios em JDBCUsuarioDAO", ex);
        }

        return usuarios;
    }

    @Override
    public void inserir(Usuario usuario) {

        Connection con = PostgreSQLDAOFactory.getConnection();

        try {
            String SQL = "INSERT INTO usuario(nome_usuario, "
                    + "senha, ultimo_acesso, ativo) "
                    + "VALUES(?, ?, ?, ?);";

            try (PreparedStatement ps = con.prepareStatement(SQL)) {

                ps.setString(1, usuario.getNome());
                ps.setString(2, usuario.getSenha());

                ps.executeUpdate();
            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Falha ao inserir unidade em JDBCUnidadeDAO", ex);
        }
    }

    @Override
    public void alterar(Usuario usuario) {

        Connection connection = PostgreSQLDAOFactory.getConnection();

        try {
            String SQL
                    = "UPDATE public.usuario\n"
                    + "   SET nome_usuario=?, senha=?, ultimo_acesso=now(), ativo=?\n"
                    + " WHERE cod_usuario=?;";

            try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                ps.setString(1, usuario.getNome());
                ps.setString(2, usuario.getSenha());
                ps.setBoolean(3, usuario.isAtivo());
                ps.setInt(4, usuario.getId());

                ps.executeUpdate();
            }
            connection.close();

        } catch (SQLException ex) {
            throw new DAOException("Falha ao alterar usuário!", ex);
        }
    }

}
