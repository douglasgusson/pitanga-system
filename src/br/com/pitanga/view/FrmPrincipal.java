package br.com.pitanga.view;

import br.com.pitanga.dao.model.CidadeDAO;
import br.com.pitanga.dao.model.RelatorioDAO;
import br.com.pitanga.dao.model.UfDAO;
import br.com.pitanga.domain.Cidade;
import br.com.pitanga.domain.Uf;
import br.com.pitanga.util.GUIUtils;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.domain.Sessao;
import br.com.pitanga.report.Relatorio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Douglas Gusson
 */
public class FrmPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FrmPrincipal
     *
     */
    public FrmPrincipal() {
        initComponents();
        initialize();
        initListeners();
    }

    private void initListeners() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSaida();
            }
        });

        ActionListener telaCliente = (ActionEvent e) -> {
            FrmClienteNew frmClienteNew = new FrmClienteNew(this, true);
            frmClienteNew.setVisible(true);
        };

        itemClientes.addActionListener(telaCliente);
        btClientes.addActionListener(telaCliente);

        ActionListener saida = (ActionEvent e) -> {
            confirmarSaida();
        };

        itemSair.addActionListener(saida);
        btSair.addActionListener(saida);

        ActionListener telaSobre = (ActionEvent e) -> {
            FrmSobre obj = new FrmSobre(this, true);
            obj.setVisible(true);
        };

        itemSobre.addActionListener(telaSobre);

        ActionListener telaUnidade = (ActionEvent e) -> {
            FrmUnidade obj = new FrmUnidade(this, true);
            obj.setVisible(true);
        };

        itemUnidades.addActionListener(telaUnidade);

        ActionListener telaTipoProduto = (ActionEvent e) -> {
            FrmTipoProduto obj = new FrmTipoProduto(this, true);
            obj.setVisible(true);
        };

        itemTiposProduto.addActionListener(telaTipoProduto);

        ActionListener telaProduto = (ActionEvent e) -> {
            FrmProduto frmProduto = new FrmProduto(this, true);
            frmProduto.setVisible(true);
        };

        itemProdutos.addActionListener(telaProduto);

        ActionListener telaFornecedor = (ActionEvent e) -> {
            FrmFornecedor frmFornecedor = new FrmFornecedor(this, true);
            frmFornecedor.setVisible(true);
        };

        itemFornecedores.addActionListener(telaFornecedor);
        btFornecedores.addActionListener(telaFornecedor);

        ActionListener telaVenda = (ActionEvent e) -> {
            FrmVenda frmVenda = new FrmVenda(this, null, Sessao.getUsuario(), null, -1);
            frmVenda.setVisible(true);
        };

        itemEmissaoVenda.addActionListener(telaVenda);
        btVenda.addActionListener(telaVenda);

        ActionListener listagemClienteAnalitico = (ActionEvent e) -> {
            RelatorioDAO obj = DAOFactory.getDefaultDAOFactory().getRelatorioDAO();
            JasperViewer jv = obj.gerarRelatorioClientesAnalitico();
            FrmReport frmReport = new FrmReport(
                    this, "Relatório de Clientes - Analítico", true, jv);
            frmReport.setVisible(true);
        };

        itemListagemClienteAnalitico.addActionListener(listagemClienteAnalitico);

        ActionListener listagemClienteSintetico = (ActionEvent e) -> {
            RelatorioDAO obj = DAOFactory.getDefaultDAOFactory().getRelatorioDAO();
            JasperViewer jv = obj.gerarRelatorioClientesSintetico();
            FrmReport frmReport = new FrmReport(
                    this, "Relatório de Clientes - Sintético", true, jv);
            frmReport.setVisible(true);
        };

        itemListagemClienteSintetico.addActionListener(listagemClienteSintetico);

        ActionListener relatorioValorMedio = (ActionEvent e) -> {
            RelatorioDAO relatorioDAO = DAOFactory.getDefaultDAOFactory().getRelatorioDAO();
            JasperViewer jv = relatorioDAO.gerarRelatorioValorMedio();
            FrmReport frmReport = new FrmReport(this, "Relatório - Valor Médio de Venda por Produto", true, jv);
            frmReport.setVisible(true);
        };

        itemRelatorioValorMedio.addActionListener(relatorioValorMedio);

        ActionListener consultaVenda = (ActionEvent e) -> {
            FrmConsultaVenda frmConsultaVenda = new FrmConsultaVenda(
                    this, true, null, Sessao.getUsuario());
            frmConsultaVenda.setVisible(true);
        };

        itemConsultaVenda.addActionListener(consultaVenda);
        btConsultaVenda.addActionListener(consultaVenda);

        ActionListener emiteCompra = (ActionEvent e) -> {
            FrmCompra frmCompra = new FrmCompra(this, true);
            frmCompra.setVisible(true);
        };

        itemEmissaoCompra.addActionListener(emiteCompra);

        ActionListener listagemEstados = (ActionEvent e) -> {
            UfDAO ud = DAOFactory.getDefaultDAOFactory().getUfDAO();
            List<Uf> estados = ud.listar();
            Relatorio report = new Relatorio();
            try {
                report.gerarRelatorioEstados(estados);
            } catch (JRException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        };

        itemListagemEstados.addActionListener(listagemEstados);

        ActionListener listagemCidades = (ActionEvent e) -> {
            CidadeDAO cd = DAOFactory.getDefaultDAOFactory().getCidadeDAO();
            List<Cidade> cidades = cd.listar();
            Relatorio report = new Relatorio();
            try {
                report.gerarRelatorioCidades(cidades);
            } catch (JRException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        };

        itemListagemCidades.addActionListener(listagemCidades);

        ActionListener telaConfiguracao = (ActionEvent e) -> {
            FrmConfiguracao obj = new FrmConfiguracao(this, true);
            obj.setVisible(true);
        };

        itemConfiguracao.addActionListener(telaConfiguracao);

        ActionListener telaFuncionario = (ActionEvent e) -> {
            FrmFuncionario frmFuncionario = new FrmFuncionario(this, true);
            frmFuncionario.setVisible(true);
        };

        itemFuncionario.addActionListener(telaFuncionario);

        ActionListener telaFormaPagamento = (ActionEvent e) -> {
            FrmFormaPagamento frmFormaPagamento
                    = new FrmFormaPagamento(this, true);
            frmFormaPagamento.setVisible(true);
        };

        itemFormaPagamento.addActionListener(telaFormaPagamento);

        ActionListener telaTituloReceber = (ActionEvent e) -> {
            FrmConsultaTituloVenda consultaTituloVenda
                    = new FrmConsultaTituloVenda(this, true);
            consultaTituloVenda.setVisible(true);
        };

        itemTituloReceber.addActionListener(telaTituloReceber);
        btTituloReceber.addActionListener(telaTituloReceber);

        ActionListener telaListagemVendasProduto = (ActionEvent e) -> {
            FrmListagemProdutoVenda obj = new FrmListagemProdutoVenda(this, true);
            obj.setVisible(true);
        };

        itemListagemVendasProduto.addActionListener(telaListagemVendasProduto);

    }

    private void initialize() {
        GUIUtils obj = new GUIUtils();
        obj.setarIcone(this);
        lbUsuario.setText(Sessao.getUsuario().getNome());
        lbAcesso.setText(Sessao.acessoToString());

        this.setTitle("Pitanga System");

        setDefaultCloseOperation(FrmPrincipal.DO_NOTHING_ON_CLOSE);
    }

    public static void preencherSessao() {

    }

    private void confirmarSaida() {
        int i = JOptionPane.showConfirmDialog(null,
                "Deseja realmente sair deste sistema?\n",
                "Pitanga System - Confirmação de saída",
                JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.NO_OPTION) {
            repaint();
        } else {
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btVenda = new javax.swing.JButton();
        btClientes = new javax.swing.JButton();
        btFornecedores = new javax.swing.JButton();
        btProdutos = new javax.swing.JButton();
        btConsultaVenda = new javax.swing.JButton();
        btTituloReceber = new javax.swing.JButton();
        btLogout = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbUsuario = new javax.swing.JLabel();
        lbAcesso = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuManutecao = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        itemEmissaoVenda = new javax.swing.JMenuItem();
        itemConsultaVenda = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        itemEmissaoCompra = new javax.swing.JMenuItem();
        itemConsultaCompra = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        itemClientes = new javax.swing.JMenuItem();
        itemFornecedores = new javax.swing.JMenuItem();
        itemFuncionario = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        itemProdutos = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        itemTiposProduto = new javax.swing.JMenuItem();
        itemUnidades = new javax.swing.JMenuItem();
        itemFormaPagamento = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemSair = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        itemTituloReceber = new javax.swing.JMenuItem();
        menuListagens = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        itemListagemClienteAnalitico = new javax.swing.JMenuItem();
        itemListagemClienteSintetico = new javax.swing.JMenuItem();
        itemListagemVendasProduto = new javax.swing.JMenuItem();
        itemListagemEstados = new javax.swing.JMenuItem();
        itemListagemCidades = new javax.swing.JMenuItem();
        itemRelatorioValorMedio = new javax.swing.JMenuItem();
        menuUtilitarios = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        itemConfiguracao = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        itemSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/add-venda_32x32.png"))); // NOI18N
        btVenda.setText("Venda [F7]");
        btVenda.setFocusable(false);
        btVenda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btVenda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btVenda);

        btClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/cliente_32x32.png"))); // NOI18N
        btClientes.setText("Cliente [F2]");
        btClientes.setFocusable(false);
        btClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btClientes);

        btFornecedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/fonecedor_32x32.png"))); // NOI18N
        btFornecedores.setText("Fornecedor [F3]");
        btFornecedores.setFocusable(false);
        btFornecedores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btFornecedores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btFornecedores);

        btProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/produto_32x32.png"))); // NOI18N
        btProdutos.setText("Produto [F4]");
        btProdutos.setFocusable(false);
        btProdutos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btProdutos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btProdutos);

        btConsultaVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/consulta_venda_32x32.png"))); // NOI18N
        btConsultaVenda.setText("Consultar Venda [F8]");
        btConsultaVenda.setFocusable(false);
        btConsultaVenda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btConsultaVenda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btConsultaVenda);

        btTituloReceber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/titulos-a-receber_32x32.png"))); // NOI18N
        btTituloReceber.setText("Títulos a Receber [F12]");
        btTituloReceber.setFocusable(false);
        btTituloReceber.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btTituloReceber.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btTituloReceber);

        btLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/logout_32x32.png"))); // NOI18N
        btLogout.setText("Logout [Ctrl+L]");
        btLogout.setFocusable(false);
        btLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btLogout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLogoutActionPerformed(evt);
            }
        });
        jToolBar1.add(btLogout);

        btSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/sair_32x32.png"))); // NOI18N
        btSair.setText("Sair [ESC]");
        btSair.setFocusable(false);
        btSair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSair.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btSair);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sessão"));

        lbUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbUsuario.setForeground(new java.awt.Color(0, 0, 255));
        lbUsuario.setText("usuario autenticado...");

        lbAcesso.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbAcesso.setForeground(new java.awt.Color(0, 0, 255));
        lbAcesso.setText("inicio da sessão...");

        jLabel1.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jLabel1.setText("Usuário autenticado:");

        jLabel2.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jLabel2.setText("Início da sessão:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbAcesso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbUsuario)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbAcesso)
                    .addComponent(jLabel2))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        menuManutecao.setMnemonic('M');
        menuManutecao.setText("Manutenção");
        menuManutecao.setToolTipText("");

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/add-venda_16x16.png"))); // NOI18N
        jMenu3.setMnemonic('V');
        jMenu3.setText("Venda");

        itemEmissaoVenda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        itemEmissaoVenda.setMnemonic('E');
        itemEmissaoVenda.setText("Emissão");
        jMenu3.add(itemEmissaoVenda);

        itemConsultaVenda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        itemConsultaVenda.setMnemonic('s');
        itemConsultaVenda.setText("Consultar Venda");
        jMenu3.add(itemConsultaVenda);

        menuManutecao.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/compra_16x16.png"))); // NOI18N
        jMenu4.setMnemonic('C');
        jMenu4.setText("Compra");

        itemEmissaoCompra.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        itemEmissaoCompra.setMnemonic('E');
        itemEmissaoCompra.setText("Emissão");
        jMenu4.add(itemEmissaoCompra);

        itemConsultaCompra.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        itemConsultaCompra.setMnemonic('s');
        itemConsultaCompra.setText("Consultar Compra");
        jMenu4.add(itemConsultaCompra);

        menuManutecao.add(jMenu4);

        jMenuItem1.setText("Orçamento");
        menuManutecao.add(jMenuItem1);
        menuManutecao.add(jSeparator3);

        itemClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        itemClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/cliente_16x16.png"))); // NOI18N
        itemClientes.setMnemonic('t');
        itemClientes.setText("Cliente");
        menuManutecao.add(itemClientes);

        itemFornecedores.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        itemFornecedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/fonecedor_16x16.png"))); // NOI18N
        itemFornecedores.setMnemonic('F');
        itemFornecedores.setText("Forncedor");
        menuManutecao.add(itemFornecedores);

        itemFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/funcionario_16x16.png"))); // NOI18N
        itemFuncionario.setMnemonic('n');
        itemFuncionario.setText("Funcionario");
        menuManutecao.add(itemFuncionario);
        menuManutecao.add(jSeparator2);

        itemProdutos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        itemProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/produto_16x16.png"))); // NOI18N
        itemProdutos.setMnemonic('P');
        itemProdutos.setText("Produto");
        menuManutecao.add(itemProdutos);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/tabelas-op_16x16.png"))); // NOI18N
        jMenu2.setMnemonic('O');
        jMenu2.setText("Tabelas Operacional");

        itemTiposProduto.setMnemonic('T');
        itemTiposProduto.setText("Tipo de Produto ");
        jMenu2.add(itemTiposProduto);

        itemUnidades.setMnemonic('U');
        itemUnidades.setText("Unidade");
        jMenu2.add(itemUnidades);

        itemFormaPagamento.setMnemonic('F');
        itemFormaPagamento.setText("Forma de Pagamento");
        jMenu2.add(itemFormaPagamento);

        menuManutecao.add(jMenu2);
        menuManutecao.add(jSeparator1);

        itemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        itemSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/sair_16x16.png"))); // NOI18N
        itemSair.setMnemonic('S');
        itemSair.setText("Sair");
        menuManutecao.add(itemSair);

        jMenuBar1.add(menuManutecao);

        jMenu5.setMnemonic('T');
        jMenu5.setText("Títulos");

        itemTituloReceber.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        itemTituloReceber.setMnemonic('r');
        itemTituloReceber.setText("Títulos a receber");
        jMenu5.add(itemTituloReceber);

        jMenuBar1.add(jMenu5);

        menuListagens.setMnemonic('L');
        menuListagens.setText("Listagens");

        jMenu1.setMnemonic('C');
        jMenu1.setText("Cadastro de Clientes");

        itemListagemClienteAnalitico.setMnemonic('A');
        itemListagemClienteAnalitico.setText("Analítico");
        jMenu1.add(itemListagemClienteAnalitico);

        itemListagemClienteSintetico.setMnemonic('S');
        itemListagemClienteSintetico.setText("Sintético");
        jMenu1.add(itemListagemClienteSintetico);

        menuListagens.add(jMenu1);

        itemListagemVendasProduto.setText("Vendas por Produto");
        menuListagens.add(itemListagemVendasProduto);

        itemListagemEstados.setMnemonic('E');
        itemListagemEstados.setText("Estados");
        menuListagens.add(itemListagemEstados);

        itemListagemCidades.setMnemonic('d');
        itemListagemCidades.setText("Cidades");
        menuListagens.add(itemListagemCidades);

        itemRelatorioValorMedio.setText("Valor Médio de Venda");
        menuListagens.add(itemRelatorioValorMedio);

        jMenuBar1.add(menuListagens);

        menuUtilitarios.setMnemonic('U');
        menuUtilitarios.setText("Utilitários");

        jMenuItem3.setText("Mudar senha");
        menuUtilitarios.add(jMenuItem3);

        itemConfiguracao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/engrenagem_16x16.png"))); // NOI18N
        itemConfiguracao.setMnemonic('C');
        itemConfiguracao.setText("Configurações");
        menuUtilitarios.add(itemConfiguracao);

        jMenuBar1.add(menuUtilitarios);

        menuAjuda.setMnemonic('A');
        menuAjuda.setText("Ajuda");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/logout_16x16.png"))); // NOI18N
        jMenuItem2.setMnemonic('L');
        jMenuItem2.setText("Logout");
        jMenuItem2.setToolTipText("");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menuAjuda.add(jMenuItem2);

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/help_16x16.png"))); // NOI18N
        jMenuItem11.setMnemonic('M');
        jMenuItem11.setText("Manuais");
        menuAjuda.add(jMenuItem11);

        itemSobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/info_16x16.png"))); // NOI18N
        itemSobre.setMnemonic('S');
        itemSobre.setText("Sobre");
        menuAjuda.add(itemSobre);

        jMenuBar1.add(menuAjuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 372, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleParent(jToolBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLogoutActionPerformed
        FrmLogin login = new FrmLogin(this);
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btLogoutActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        FrmLogin login = new FrmLogin(this);
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClientes;
    private javax.swing.JButton btConsultaVenda;
    private javax.swing.JButton btFornecedores;
    private javax.swing.JButton btLogout;
    private javax.swing.JButton btProdutos;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btTituloReceber;
    private javax.swing.JButton btVenda;
    private javax.swing.JMenuItem itemClientes;
    private javax.swing.JMenuItem itemConfiguracao;
    private javax.swing.JMenuItem itemConsultaCompra;
    private javax.swing.JMenuItem itemConsultaVenda;
    private javax.swing.JMenuItem itemEmissaoCompra;
    private javax.swing.JMenuItem itemEmissaoVenda;
    private javax.swing.JMenuItem itemFormaPagamento;
    private javax.swing.JMenuItem itemFornecedores;
    private javax.swing.JMenuItem itemFuncionario;
    private javax.swing.JMenuItem itemListagemCidades;
    private javax.swing.JMenuItem itemListagemClienteAnalitico;
    private javax.swing.JMenuItem itemListagemClienteSintetico;
    private javax.swing.JMenuItem itemListagemEstados;
    private javax.swing.JMenuItem itemListagemVendasProduto;
    private javax.swing.JMenuItem itemProdutos;
    private javax.swing.JMenuItem itemRelatorioValorMedio;
    private javax.swing.JMenuItem itemSair;
    private javax.swing.JMenuItem itemSobre;
    private javax.swing.JMenuItem itemTiposProduto;
    private javax.swing.JMenuItem itemTituloReceber;
    private javax.swing.JMenuItem itemUnidades;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbAcesso;
    private javax.swing.JLabel lbUsuario;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenu menuListagens;
    private javax.swing.JMenu menuManutecao;
    private javax.swing.JMenu menuUtilitarios;
    // End of variables declaration//GEN-END:variables
}
