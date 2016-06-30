package br.com.pitanga.dao.mysql;

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
public class MySQLDAOFactory extends DAOFactory {

    private static final Database DATABASE = getDatabase();
    private static final String URL_BANCO
            = "jdbc:mysql://"
            + DATABASE.getIp()
            + ":" + DATABASE.getPorta()
            + "/" + DATABASE.getNome();
    private static final String DRIVER = "com.mysql.jdbc.Driver";
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
                    "Não foi possível estabelecer conexão com o banco de dados.\n\n"
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CidadeDAO getCidadeDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClienteDAO getClienteDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UnidadeDAO getUnidadeDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FornecedorDAO getFornecedorDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoProdutoDAO getTipoProdutoDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProdutoDAO getProdutoDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItemVendaDAO getItemVendaDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VendaDAO getVendaDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TituloVendaDAO getTituloVendaDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RelatorioDAO getRelatorioDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FormaPagamentoDAO getFormaPagamentoDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmpresaDAO getEmpresaDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RecebimentoDAO getRecebimentoDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItemCompraDAO getItemCompraDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CompraDAO getCompraDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TituloCompraDAO getTituloCompraDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
