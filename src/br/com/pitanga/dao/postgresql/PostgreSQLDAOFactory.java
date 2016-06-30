package br.com.pitanga.dao.postgresql;

import br.com.pitanga.dao.DAOException;
import br.com.pitanga.dao.model.CidadeDAO;
import br.com.pitanga.dao.model.ClienteDAO;
import br.com.pitanga.dao.model.EmpresaDAO;
import br.com.pitanga.dao.model.FormaPagamentoDAO;
import br.com.pitanga.dao.model.FornecedorDAO;
import br.com.pitanga.dao.model.ItemVendaDAO;
import br.com.pitanga.dao.model.ProdutoDAO;
import br.com.pitanga.dao.model.RelatorioDAO;
import br.com.pitanga.dao.model.TipoProdutoDAO;
import br.com.pitanga.dao.model.TituloVendaDAO;
import br.com.pitanga.dao.model.UfDAO;
import br.com.pitanga.dao.model.UnidadeDAO;
import br.com.pitanga.dao.model.UsuarioDAO;
import br.com.pitanga.dao.model.VendaDAO;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.dao.model.CompraDAO;
import br.com.pitanga.dao.model.ItemCompraDAO;
import br.com.pitanga.dao.model.RecebimentoDAO;
import br.com.pitanga.dao.model.TituloCompraDAO;
import br.com.pitanga.domain.Database;
import br.com.pitanga.view.FrmConfiguraBanco;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/*
 @author Douglas Gusson
 */
public class PostgreSQLDAOFactory extends DAOFactory {

    private static final Database DATABASE = getDatabase();
    private static final String URL_BANCO
            = "jdbc:postgresql://"
            + DATABASE.getIp()
            + ":" + DATABASE.getPorta()
            + "/" + DATABASE.getNome();
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USUARIO = DATABASE.getUsuario();
    private static final String SENHA = DATABASE.getSenha();

    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL_BANCO, USUARIO, SENHA);
            return connection;
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "Conector para PostgreSQL não foi encontrado.",
                    "Erro de conexão",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            throw new DAOException("Não foi possível estabelecer conexão com o banco de dados.", ex);            
        }
        return null;
    }

    public static String getHost() {
        String host = null;
        try {
            Scanner scanner = new Scanner(new FileReader("host.txt"));
            while (scanner.hasNext()) {
                host = scanner.next().trim();
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo host.txt não encontrado.");
        }
        return host;
    }

    /**
     * @return O objeto Database.
     */
    public static Database getDatabase() {
        try {
            StringBuilder xml = new StringBuilder();
            Scanner scanner = new Scanner(new FileReader("database.xml"));
            while (scanner.hasNext()) {
                xml.append(scanner.next());
            }

            XStream xStream = new XStream(new DomDriver());
            Database banco = (Database) xStream.fromXML(xml.toString());

            return banco;

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível estabelecer conexão com o banco.\n\n"
                    + "ERRO: Arquivo database.xml não encontrado.",
                    "Erro de conexão",
                    JOptionPane.ERROR_MESSAGE);
            FrmConfiguraBanco configuraBanco = new FrmConfiguraBanco(null, true);
            configuraBanco.setVisible(true);
        }
        return null;
    }

    @Override
    public UfDAO getUfDAO() {
        return new PgUfDAO();
    }

    @Override
    public CidadeDAO getCidadeDAO() {
        return new PgCidadeDAO();
    }

    @Override
    public ClienteDAO getClienteDAO() {
        return new PgClienteDAO();
    }

    @Override
    public UnidadeDAO getUnidadeDAO() {
        return new PgUnidadeDAO();
    }

    @Override
    public FornecedorDAO getFornecedorDAO() {
        return new PgFornecedorDAO();
    }

    @Override
    public TipoProdutoDAO getTipoProdutoDAO() {
        return new PgTipoProdutoDAO();
    }

    @Override
    public ProdutoDAO getProdutoDAO() {
        return new PgProdutoDAO();
    }

    @Override
    public ItemVendaDAO getItemVendaDAO() {
        return new PgItemVendaDAO();
    }

    @Override
    public VendaDAO getVendaDAO() {
        return new PgVendaDAO();
    }

    @Override
    public TituloVendaDAO getTituloVendaDAO() {
        return new PgTituloVendaDAO();
    }

    @Override
    public RelatorioDAO getRelatorioDAO() {
        return new PgRelatorioDAO();
    }

    @Override
    public FormaPagamentoDAO getFormaPagamentoDAO() {
        return new PgFormaPagamentoDAO();
    }

    @Override
    public EmpresaDAO getEmpresaDAO() {
        return new PgEmpresaDAO();
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new PgUsuarioDAO();
    }

    @Override
    public RecebimentoDAO getRecebimentoDAO() {
        return new PgRecebimentoDAO();
    }

    @Override
    public ItemCompraDAO getItemCompraDAO() {
        return new PgItemCompraDAO();
    }

    @Override
    public CompraDAO getCompraDAO() {
        return new PgCompraDAO();
    }

    @Override
    public TituloCompraDAO getTituloCompraDAO() {
        return new PgTituloCompraDAO();
    }

}
