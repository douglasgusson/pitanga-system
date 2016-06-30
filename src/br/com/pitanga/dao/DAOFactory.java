package br.com.pitanga.dao;

import br.com.pitanga.dao.postgresql.PostgreSQLDAOFactory;
import br.com.pitanga.dao.model.CidadeDAO;
import br.com.pitanga.dao.model.ClienteDAO;
import br.com.pitanga.dao.model.CompraDAO;
import br.com.pitanga.dao.model.EmpresaDAO;
import br.com.pitanga.dao.model.FormaPagamentoDAO;
import br.com.pitanga.dao.model.FornecedorDAO;
import br.com.pitanga.dao.model.ItemCompraDAO;
import br.com.pitanga.dao.model.ItemVendaDAO;
import br.com.pitanga.dao.model.ProdutoDAO;
import br.com.pitanga.dao.model.RecebimentoDAO;
import br.com.pitanga.dao.model.RelatorioDAO;
import br.com.pitanga.dao.model.TipoProdutoDAO;
import br.com.pitanga.dao.model.TituloCompraDAO;
import br.com.pitanga.dao.model.TituloVendaDAO;
import br.com.pitanga.dao.model.UfDAO;
import br.com.pitanga.dao.model.UnidadeDAO;
import br.com.pitanga.dao.model.UsuarioDAO;
import br.com.pitanga.dao.model.VendaDAO;
import br.com.pitanga.dao.mysql.MySQLDAOFactory;

/**
 *
 * @author Douglas Gusson
 */
public abstract class DAOFactory {

    public static final int POSTGRESQL = 1;
    public static final int MYSQL = 2;
    public static final int WEB = 3;

    public abstract UfDAO getUfDAO();
    public abstract CidadeDAO getCidadeDAO();
    public abstract CompraDAO getCompraDAO();
    public abstract ClienteDAO getClienteDAO();
    public abstract UnidadeDAO getUnidadeDAO();
    public abstract FornecedorDAO getFornecedorDAO();
    public abstract TipoProdutoDAO getTipoProdutoDAO();
    public abstract ProdutoDAO getProdutoDAO();
    public abstract ItemVendaDAO getItemVendaDAO();
    public abstract VendaDAO getVendaDAO();
    public abstract TituloVendaDAO getTituloVendaDAO();
    public abstract RelatorioDAO getRelatorioDAO();
    public abstract FormaPagamentoDAO getFormaPagamentoDAO();
    public abstract EmpresaDAO getEmpresaDAO();
    public abstract UsuarioDAO getUsuarioDAO();
    public abstract RecebimentoDAO getRecebimentoDAO();
    public abstract ItemCompraDAO getItemCompraDAO();
    public abstract TituloCompraDAO getTituloCompraDAO();
    
    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case POSTGRESQL:
                return new PostgreSQLDAOFactory();
            case MYSQL:
                return new MySQLDAOFactory();
            case WEB:
            default:
                return null;
        }
    }

    public static DAOFactory getDefaultDAOFactory() {
        return getDAOFactory(POSTGRESQL);
    }

}
