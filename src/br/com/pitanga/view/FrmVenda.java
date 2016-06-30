package br.com.pitanga.view;

import br.com.pitanga.dao.model.ClienteDAO;
import br.com.pitanga.dao.model.ItemVendaDAO;
import br.com.pitanga.dao.model.ProdutoDAO;
import br.com.pitanga.dao.model.RelatorioDAO;
import br.com.pitanga.dao.model.TituloVendaDAO;
import br.com.pitanga.dao.model.VendaDAO;
import br.com.pitanga.domain.Cliente;
import br.com.pitanga.domain.Empresa;
import br.com.pitanga.domain.ItemVenda;
import br.com.pitanga.domain.Produto;
import br.com.pitanga.domain.TituloVenda;
import br.com.pitanga.domain.Usuario;
import br.com.pitanga.domain.Venda;
import br.com.pitanga.table.model.ItemVendaTableModel;
import br.com.pitanga.table.model.TituloVendaTableModel;
import br.com.pitanga.util.GUIUtils;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.mail.MailSender;
import br.com.pitanga.table.cellrenderer.ItemVendaCellRenderer;
import br.com.pitanga.util.MascaraNumerica;
import br.com.pitanga.util.StringUtils;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Douglas Gusson
 */
public class FrmVenda extends javax.swing.JDialog {

    private static final int OPCAO_INSERIR = 0;
    private static final int OPCAO_ALTERAR = 1;
    private List<ItemVenda> itens = new ArrayList<>();
    private List<TituloVenda> titulos = new ArrayList<>();
    private List<Venda> colecaoVendas = new ArrayList<>();
    private int opcao;
    private int indiceVenda;
    private Empresa empresa;
    private Usuario usuario;
    private Venda venda;
    private Produto produto;

    /**
     * Creates new form FrmCliente
     *
     * @param parent
     * @param empresa
     * @param usuario
     * @param vendas
     * @param indice
     */
    public FrmVenda(Window parent, Empresa empresa,
            Usuario usuario, List<Venda> vendas, int indice) {
        super(parent, DEFAULT_MODALITY_TYPE);

        initComponents();
        initialize();

        this.empresa = empresa;
        this.usuario = usuario;
        this.colecaoVendas = vendas;
        this.indiceVenda = indice;

        if (indiceVenda < 0 || indiceVenda >= colecaoVendas.size()) {
            opcao = OPCAO_INSERIR;
        } else {
            opcao = OPCAO_ALTERAR;
        }

        if (opcao == OPCAO_ALTERAR) {
            novaVenda(); //habilitar os campos para edição
            ItemVendaDAO itemVendaDAO = DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
            TituloVendaDAO tituloVendaDAO = DAOFactory.getDefaultDAOFactory().getTituloVendaDAO();
            venda = colecaoVendas.get(indice);
            setCliente(venda.getCliente());
            tfDataVenda.setDateFormatString(venda.getDataVendaFormatada());
            tfSubDesconto.setText(StringUtils.formatarDecimal(venda.getValorDesconto()));
            tfSubTotal.setText(StringUtils.formatarDecimal(venda.getValorVenda()));
            taObservacoes.setText(venda.getObservacoes());
            itens = venda.getItensVenda();
            tbItemVenda.setModel(new ItemVendaTableModel(itens));
            titulos = venda.getTitulosVenda();
            tbTituloVenda.setModel(new TituloVendaTableModel(titulos));
        }
    }

    private void initListener() {

        tfNome.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                avancarAba();
            }
        });

        tfValorItem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (tfCodigoProduto.getText().isEmpty()
                        || Integer.parseInt(tfCodigoProduto.getText()) == 0) {
                    avancarAba();
                } else {
                    double desconto = Double.parseDouble(tfDesconto.getText().replaceAll("\\.", "").replace(",", "."));
                    double valor = Double.parseDouble(tfValorVenda.getText().replaceAll("\\.", "").replace(",", "."));
                    double quantidade = Double.parseDouble(tfQuantidade.getText().replaceAll("\\.", "").replace(",", "."));
                    Produto p = new Produto();
                    p.setIdProduto(Integer.parseInt(tfCodigoProduto.getText()));
                    p.setDescricao(tfDescricao.getText());
                    adicionarItem(desconto, valor, quantidade, p);
                }
            }
        });

        btSair.addActionListener((ActionEvent e) -> {
            FrmVenda.this.dispose();
        });

        btNovo.addActionListener((ActionEvent e) -> {
            novaVenda();
        });

        btBuscarProduto.addActionListener((ActionEvent e) -> {
            FrmBuscaProduto fbp = new FrmBuscaProduto(this, this);
            fbp.setVisible(true);
        });

        btBuscarCliente.addActionListener((ActionEvent e) -> {
            FrmBuscaCliente fbc = new FrmBuscaCliente(this, true, this);
            fbc.setVisible(true);
        });

        btCancelar.addActionListener((ActionEvent e) -> {
            confirmarCancelamento();
        });

        btGravar.addActionListener((ActionEvent e) -> {
            if (this.tfCodigoCliente.getText().isEmpty()
                    || this.tfCodigoCliente.getText().equals("00000")) {
                JOptionPane.showMessageDialog(
                        null, "Por favor, informe o cliente.");
                tpPrincipal.setSelectedIndex(0);
                tfCodigoCliente.requestFocus();
            } else if (itens.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Lance pelo menos um item.");
                tpPrincipal.setSelectedIndex(1);
                tfCodigoProduto.requestFocus();
            } else if (titulos.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Informe as parcelas.");
                tpPrincipal.setSelectedIndex(2);
                tfQuantParcelas.requestFocus();
            } else {
                gravarVenda();
            }
        });

        tfValorVenda.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (tfValorVenda.getText().equals("")) {
                    tfValorVenda.setText("0,00");
                }
                if (!(tfCodigoProduto.getText().trim()).equals("")) {
                    double valor = Double.parseDouble(tfValorVenda.getText().replaceAll("\\.", "").replace(",", "."));
                    if (valor == 0) {
                        JOptionPane.showMessageDialog(null,
                                "O valor unitário não deve ser zero (0,00).");
                    }
                }
                calcularValorItem();
            }
        });

        tfValorVenda.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                tfValorVenda.selectAll();
            }
        });

        tfCodigoProduto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!((tfCodigoProduto.getText().trim()).equals(""))) {
                    ProdutoDAO pd = DAOFactory.getDefaultDAOFactory().getProdutoDAO();
                    Produto p = pd.buscar(Integer.parseInt(tfCodigoProduto.getText()));
                    setProduto(p);
                } else if ((tfCodigoProduto.getText().trim()).equals("00000")) {
                    FrmBuscaProduto frmBuscaProduto
                            = new FrmBuscaProduto(FrmVenda.this, FrmVenda.this);
                    frmBuscaProduto.setVisible(true);
                }
            }
        });

        tfCodigoProduto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                StringUtils obj = new StringUtils();
                obj.somenteNumeros(e, tfCodigoProduto);
            }
        });

        tfCodigoCliente.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!(tfCodigoCliente.getText().equals(""))) {
                    Cliente cliente = new Cliente();

                    ClienteDAO cd = DAOFactory.getDefaultDAOFactory().getClienteDAO();
                    cliente = cd.buscarPorId(Integer.parseInt(tfCodigoCliente.getText()));

                    setCliente(cliente);
                } else if (((tfCodigoCliente.getText().trim()).equals(""))
                        || (Integer.parseInt(tfCodigoCliente.getText()) == 0)) {
                }
            }
        });

        tfCodigoCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                StringUtils obj = new StringUtils();
                obj.somenteNumeros(e, tfCodigoCliente);
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btSair = new javax.swing.JButton();
        btNovo = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btGravar = new javax.swing.JButton();
        tpPrincipal = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        tfDataVenda = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        tfEndereco = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfCpfCnpj = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfRgInscricao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfNumero = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfBairro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tfTelefone = new javax.swing.JTextField();
        tfCelular = new javax.swing.JTextField();
        tfEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfCep = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbCodigo = new javax.swing.JLabel();
        tfCodigoCidade = new javax.swing.JTextField();
        tfNomeCidade = new javax.swing.JTextField();
        tfUfCidade = new javax.swing.JTextField();
        btBuscarCliente = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        tfCodigoCliente = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        tfApelido = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        tfContato = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbItemVenda = new javax.swing.JTable();
        btBuscarProduto = new javax.swing.JButton();
        tfDescricao = new javax.swing.JTextField();
        tfCodigoProduto = new javax.swing.JTextField();
        tfValorVenda = new javax.swing.JTextField();
        tfValorItem = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        tfSubDesconto = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        tfSubTotal = new javax.swing.JTextField();
        tfDesconto = new javax.swing.JFormattedTextField();
        tfQuantidade = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        tfValor = new javax.swing.JTextField();
        btLimpar = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        tfParcela = new javax.swing.JTextField();
        btAlterar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        tfQuantParcelas = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        tfPrazo = new javax.swing.JTextField();
        btCalcular = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbTituloVenda = new javax.swing.JTable();
        tfVencimento = new com.toedter.calendar.JDateChooser();
        tfTotalParcelas = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taObservacoes = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clientes");
        setResizable(false);

        btSair.setMnemonic('S');
        btSair.setText("Sair");

        btNovo.setMnemonic('N');
        btNovo.setText("Novo");

        btCancelar.setMnemonic('C');
        btCancelar.setText("Cancelar");

        btGravar.setMnemonic('G');
        btGravar.setText("Gravar");

        tpPrincipal.setToolTipText("");

        jLabel20.setText("Data venda:");

        tfDataVenda.setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Cliente"));

        jLabel1.setText("Nome / Razão social:");

        tfNome.setEditable(false);
        tfNome.setBackground(new java.awt.Color(255, 255, 255));

        tfEndereco.setEditable(false);
        tfEndereco.setBackground(new java.awt.Color(255, 255, 255));
        tfEndereco.setFocusable(false);

        jLabel2.setText("Endereço:");

        jLabel3.setText("CPF / CNPJ:");

        tfCpfCnpj.setEditable(false);
        tfCpfCnpj.setBackground(new java.awt.Color(255, 255, 255));
        tfCpfCnpj.setFocusable(false);

        jLabel4.setText("RG / Inscrição:");

        tfRgInscricao.setEditable(false);
        tfRgInscricao.setBackground(new java.awt.Color(255, 255, 255));
        tfRgInscricao.setFocusable(false);

        jLabel6.setText("Número:");

        tfNumero.setEditable(false);
        tfNumero.setBackground(new java.awt.Color(255, 255, 255));
        tfNumero.setFocusable(false);

        jLabel7.setText("Bairro:");

        tfBairro.setEditable(false);
        tfBairro.setBackground(new java.awt.Color(255, 255, 255));
        tfBairro.setFocusable(false);

        jLabel8.setText("Telefone:");

        jLabel9.setText("Celular:");

        jLabel10.setText("E-mail:");

        tfTelefone.setEditable(false);
        tfTelefone.setBackground(new java.awt.Color(255, 255, 255));
        tfTelefone.setFocusable(false);

        tfCelular.setEditable(false);
        tfCelular.setBackground(new java.awt.Color(255, 255, 255));
        tfCelular.setFocusable(false);

        tfEmail.setEditable(false);
        tfEmail.setBackground(new java.awt.Color(255, 255, 255));
        tfEmail.setFocusable(false);

        jLabel11.setText("CEP:");

        tfCep.setEditable(false);
        tfCep.setBackground(new java.awt.Color(255, 255, 255));
        tfCep.setFocusable(false);

        jLabel12.setText("UF:");

        jLabel13.setText("Cidade:");

        lbCodigo.setText("Cod. cliente:");

        tfCodigoCidade.setEditable(false);
        tfCodigoCidade.setBackground(new java.awt.Color(255, 255, 255));
        tfCodigoCidade.setFocusable(false);

        tfNomeCidade.setEditable(false);
        tfNomeCidade.setBackground(new java.awt.Color(255, 255, 255));
        tfNomeCidade.setFocusable(false);

        tfUfCidade.setEditable(false);
        tfUfCidade.setBackground(new java.awt.Color(255, 255, 255));
        tfUfCidade.setFocusable(false);

        btBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/pesquisa_16x16.png"))); // NOI18N

        jLabel14.setText("Cod. cidade:");

        jLabel29.setText("Apelido / Nome fantasia:");

        tfApelido.setEditable(false);
        tfApelido.setBackground(new java.awt.Color(255, 255, 255));
        tfApelido.setFocusable(false);

        jLabel30.setText("Contato:");

        tfContato.setEditable(false);
        tfContato.setBackground(new java.awt.Color(255, 255, 255));
        tfContato.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfEmail)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(tfCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(tfRgInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(tfCep, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfCodigoCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(tfNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tfUfCidade)))
                    .addComponent(jLabel10)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(tfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfCodigoCliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tfApelido)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(tfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(tfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tfContato))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfApelido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tfRgInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel14))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfCodigoCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfUfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel13)
                            .addComponent(tfNomeCidade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDataVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfDataVenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        tpPrincipal.addTab("Dados da Venda", jPanel3);

        tbItemVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbItemVenda.setNextFocusableComponent(btGravar);
        tbItemVenda.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbItemVenda.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbItemVenda);

        btBuscarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/pesquisa_16x16.png"))); // NOI18N
        btBuscarProduto.setToolTipText("Buscar produto");

        tfDescricao.setEditable(false);
        tfDescricao.setToolTipText("");

        tfCodigoProduto.setToolTipText("Código do Produto");

        jLabel15.setText("Cód. produto:");

        jLabel16.setText("Descrição:");

        jLabel17.setText("Quant.:");

        jLabel18.setText("Valor Unit.:");

        jLabel19.setText("Valor Total:");

        jLabel5.setText("Desc. (%) :");

        jLabel21.setText("Desconto (R$) :");

        tfSubDesconto.setEditable(false);
        tfSubDesconto.setBackground(new java.awt.Color(153, 255, 153));
        tfSubDesconto.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tfSubDesconto.setText("0,00");

        jLabel22.setText("Sub-total:");

        tfSubTotal.setEditable(false);
        tfSubTotal.setBackground(new java.awt.Color(153, 255, 153));
        tfSubTotal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tfSubTotal.setText("0,00");

        tfDesconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfDescontoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfDescontoFocusLost(evt);
            }
        });

        tfQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfQuantidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfQuantidadeFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfCodigoProduto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btBuscarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(tfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(tfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(tfValorVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(tfDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfValorItem)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(87, 87, 87)))
                        .addGap(0, 171, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSubDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btBuscarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel17)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(26, 26, 26))
                        .addComponent(tfDesconto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tfValorVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfValorItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(tfSubDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(tfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        tpPrincipal.addTab("Itens", jPanel2);

        jLabel23.setText("Valor:");

        jLabel24.setText("Vencimento:");

        btLimpar.setText("Remover parcelas");
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });

        jLabel25.setText("Parcela:");

        tfParcela.setEditable(false);
        tfParcela.setFocusable(false);

        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED)));

        jLabel26.setText("Quant. Parcelas:");

        tfQuantParcelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfQuantParcelasKeyTyped(evt);
            }
        });

        jLabel27.setText("Prazo:");

        tfPrazo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfPrazoKeyTyped(evt);
            }
        });

        btCalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/engrenagem_16x16.png"))); // NOI18N
        btCalcular.setText("Gerar");
        btCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCalcularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tfQuantParcelas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(tfPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btCalcular)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfQuantParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCalcular))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbTituloVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTituloVenda.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbTituloVenda.getTableHeader().setResizingAllowed(false);
        tbTituloVenda.getTableHeader().setReorderingAllowed(false);
        tbTituloVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTituloVendaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbTituloVenda);

        tfTotalParcelas.setEditable(false);

        jLabel31.setText("Total parcelas:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfTotalParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(tfValor, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(tfVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(413, 413, 413))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btAlterar)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btLimpar)
                    .addComponent(tfTotalParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addContainerGap())
        );

        tpPrincipal.addTab("Parcelamento", jPanel4);

        jLabel28.setText("Observações:");

        taObservacoes.setColumns(20);
        taObservacoes.setRows(5);
        jScrollPane3.setViewportView(taObservacoes);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(0, 763, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(238, Short.MAX_VALUE))
        );

        tpPrincipal.addTab("Complemento", jPanel6);

        jMenu1.setText("Cadastros");

        jMenuItem1.setText("Cliente");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Produto");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btGravar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSair))
                    .addComponent(tpPrincipal))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSair)
                    .addComponent(btNovo)
                    .addComponent(btCancelar)
                    .addComponent(btGravar))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tfDescontoFocusLost(java.awt.event.FocusEvent evt) {

        if (tfDesconto.getText().equals("")) {
            tfDesconto.setText("0,00");
        }

        StringUtils obj = new StringUtils();

        double desconto = Double.parseDouble(this.tfDesconto.getText().replaceAll("\\.", "").replace(",", "."));
        if (desconto > 100) {
            JOptionPane.showMessageDialog(rootPane, "O valor do desconto não pode\n"
                    + "ser maior que 100%. (" + obj.formatarDecimalTabela(desconto) + "%)");
            this.tfDesconto.requestFocus();
        } else if (desconto < 0) {
            JOptionPane.showMessageDialog(rootPane, "O valor do desconto não pode\n"
                    + "ser menor que 0%. (" + obj.formatarDecimalTabela(desconto) + "%)");
            this.tfDesconto.requestFocus();
        } else {
            tfDesconto.setText(StringUtils.formatarDecimal(desconto));
            calcularValorItem();
        }
    }

    private void tfDescontoFocusGained(java.awt.event.FocusEvent evt) {
        tfDesconto.selectAll();
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        FrmCliente frmCliente = new FrmCliente(this, true);
        frmCliente.setVisible(true);
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        FrmProduto frmProduto = new FrmProduto(this, true);
        frmProduto.setVisible(true);
    }

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {
        this.titulos.removeAll(titulos);
        this.tbTituloVenda.setModel(new TituloVendaTableModel(titulos));
    }

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {
        this.tfParcela.setText(null);
        this.tfValor.setText(null);
        this.tfVencimento.setDate(null);
        this.tfValor.setEnabled(false);
        this.tfVencimento.setEnabled(false);
        this.btAlterar.setEnabled(false);
    }

    private void tfQuantParcelasKeyTyped(java.awt.event.KeyEvent evt) {
        StringUtils obj = new StringUtils();
        obj.somenteNumeros(evt, this);
    }

    private void tfPrazoKeyTyped(java.awt.event.KeyEvent evt) {
        StringUtils obj = new StringUtils();
        obj.somenteNumeros(evt, this);
    }

    private void btCalcularActionPerformed(java.awt.event.ActionEvent evt) {
        if (!this.titulos.isEmpty()) {
            this.titulos.removeAll(titulos);
            this.tbTituloVenda.setModel(new TituloVendaTableModel(titulos));
            Long quantParcelas = Long.parseLong(this.tfQuantParcelas.getText());
            Long prazo = Long.parseLong(this.tfPrazo.getText());
            lancarParcelas(quantParcelas, prazo);
        } else {
            Long quantParcelas = Long.parseLong(this.tfQuantParcelas.getText());
            Long prazo = Long.parseLong(this.tfPrazo.getText());
            lancarParcelas(quantParcelas, prazo);
        }
    }

    private void tbTituloVendaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int selecionada = tbTituloVenda.getSelectedRow();

            this.tfParcela.setText(this.tbTituloVenda.getValueAt(selecionada, 0).toString());
            this.tfValor.setText(this.tbTituloVenda.getValueAt(selecionada, 1).toString());
            this.tfVencimento.setDate(this.titulos.get(selecionada).getVencimento());
            this.tfValor.setEnabled(true);
            this.tfVencimento.setEnabled(true);
            this.btAlterar.setEnabled(true);

        }
    }

    private void tfQuantidadeFocusGained(java.awt.event.FocusEvent evt) {
        tfQuantidade.selectAll();
    }

    private void tfQuantidadeFocusLost(java.awt.event.FocusEvent evt) {
        if (tfQuantidade.getText().equals("")) {
            tfQuantidade.setText("0,00");
        }
    }

    /**
     * Método para avançar as abas do form.
     * <br /><br />Define o index da próxima aba:<br />
     * <code>// Atual + 1 <br />
     * int proximo = tpPrincipal.getSelectedIndex() + 1;
     * <br /></code><br /><br />
     * Pega a quantidade de abas contidas no painel:<br />
     * <code>int quantidade = tpPrincipal.getTabCount();</code><br /><br />
     * Verifica se o valor do index para a próxima aba é menor que quantidade de
     * abas contidas no painel, if true seta o index:<br /><code>
     * if (proximo < quantidade) { <br />
     * tpPrincipal.setSelectedIndex(proximo);<br />}</code>
     */
    private void avancarAba() {
        int proximo = tpPrincipal.getSelectedIndex() + 1;
        int quantidade = tpPrincipal.getTabCount();
        if (proximo < quantidade) {
            tpPrincipal.setSelectedIndex(proximo);
        }
    }

    private void novaVenda() {
        tpPrincipal.setEnabled(true);
        // Botões
        this.btNovo.setEnabled(false);
        this.btSair.setEnabled(false);
        this.btCancelar.setEnabled(true);
        this.btBuscarCliente.setEnabled(true);
        this.btGravar.setEnabled(true);
        this.btBuscarProduto.setEnabled(true);
        // Tabela 
        this.tbItemVenda.setEnabled(true);
        // Campos
        this.tfCodigoCliente.setEnabled(true);
        this.tfNome.setEnabled(true);
        this.tfCpfCnpj.setEnabled(true);
        this.tfRgInscricao.setEnabled(true);
        this.tfEndereco.setEnabled(true);
        this.tfNumero.setEnabled(true);
        this.tfBairro.setEnabled(true);
        this.tfCep.setEnabled(true);
        this.tfTelefone.setEnabled(true);
        this.tfCelular.setEnabled(true);
        this.tfEmail.setEnabled(true);
        this.tfCodigoCidade.setEnabled(true);
        this.tfNomeCidade.setEnabled(true);
        this.tfUfCidade.setEnabled(true);
        this.tfCodigoProduto.setEnabled(true);
        this.tfDescricao.setEnabled(true);
        this.tfQuantidade.setEnabled(true);
        this.tfValorVenda.setEnabled(true);
        this.tfDesconto.setEnabled(true);
        this.tfValorItem.setEnabled(true);
        this.tfSubDesconto.setEnabled(true);
        this.tfSubTotal.setEnabled(true);
        this.taObservacoes.setEnabled(true);
        this.tfDataVenda.setEnabled(true);
        this.tfDataVenda.setDate(new Date());
        // Setar foco
        this.tfCodigoCliente.requestFocus();

        // Se teclarmos ESC nesta janela, ela irá se fechar:  
        this.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            FrmVenda.this.confirmarCancelamento();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /**
     * Define um estado inicial aos componentes do form.
     */
    private void initialize() {
        initListener();
        GUIUtils.considerarEnterComoTab(this);
        menuContextoTabelaItens();
        menuContextoTabelaTitulos();
        preencherTabelaItens();
        mascaraNumerica();
        tpPrincipal.setEnabled(false);
        tpPrincipal.setSelectedIndex(0);
        // Habilita/desabilita os botões
        this.btNovo.setEnabled(true);
        this.btSair.setEnabled(true);
        this.btCancelar.setEnabled(false);
        this.btBuscarCliente.setEnabled(false);
        this.btGravar.setEnabled(false);
        this.btBuscarProduto.setEnabled(false);
        // Desabilita a tabela
        this.tbItemVenda.setEnabled(false);
        // Desabilita os campos  
        this.tfDataVenda.setEnabled(false);
        this.tfCodigoCliente.setEnabled(false);
        this.tfNome.setEnabled(false);
        this.tfCpfCnpj.setEnabled(false);
        this.tfRgInscricao.setEnabled(false);
        this.tfEndereco.setEnabled(false);
        this.tfNumero.setEnabled(false);
        this.tfBairro.setEnabled(false);
        this.tfCep.setEnabled(false);
        this.tfCodigoCidade.setEnabled(false);
        this.tfNomeCidade.setEnabled(false);
        this.tfUfCidade.setEnabled(false);
        this.tfTelefone.setEnabled(false);
        this.tfCelular.setEnabled(false);
        this.tfEmail.setEnabled(false);
        this.tfCodigoProduto.setEnabled(false);
        this.tfDescricao.setEnabled(false);
        this.tfQuantidade.setEnabled(false);
        this.tfValorVenda.setEnabled(false);
        this.tfDesconto.setEnabled(false);
        this.tfValorItem.setEnabled(false);
        this.tfSubDesconto.setEnabled(false);
        this.tfSubTotal.setEnabled(false);
        this.taObservacoes.setEnabled(false);
        // Limpa os campos
        limparTodosCampos(rootPane);
        this.tfSubDesconto.setText("0,00");
        this.tfSubTotal.setText("0,00");
        this.taObservacoes.setText("");
        this.titulos.removeAll(titulos);
        this.setTitle("Pitanga System - Venda");
        this.itens.removeAll(itens);

        // Se teclarmos ESC nesta janela, ela irá se fechar:  
        this.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            FrmVenda.this.dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

    }

    /**
     * Adiciona máscara aos campos numéricos, usando a classe MascaraNumerica.
     */
    private void mascaraNumerica() {
        tfValorVenda.addKeyListener(new MascaraNumerica(tfValorVenda, 8, 2));
        tfQuantidade.addKeyListener(new MascaraNumerica(tfQuantidade, 8, 2));
        tfDesconto.addKeyListener(new MascaraNumerica(tfDesconto, 8, 2));
        tfValorItem.addKeyListener(new MascaraNumerica(tfValorItem, 18, 2));
    }

    public void limparTodosCampos(Container container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JFormattedTextField) {
                JFormattedTextField field = (JFormattedTextField) component;
                field.setValue(null);
            } else if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setText("");
            } else if (component instanceof JDateChooser) {
                JDateChooser field = (JDateChooser) component;
                field.setDate(null);
            } else if (component instanceof Container) {
                limparTodosCampos((Container) component);
            }
        }
    }

    /**
     * @param cliente Preenche os campos do cliente com os dados do objetos
     * recebido como argumento.
     */
    public void setCliente(Cliente cliente) {
        this.tfCodigoCliente.setText(StringUtils.inteiroCincoDig(cliente.getId()));
        this.tfNome.setText(cliente.getNome());
        this.tfApelido.setText(cliente.getApelido());
        this.tfCpfCnpj.setText(cliente.getCpfCnpj());
        this.tfRgInscricao.setText(cliente.getRgInscricao());
        this.tfEndereco.setText(cliente.getEndereco());
        this.tfNumero.setText(cliente.getNumero());
        this.tfBairro.setText(cliente.getBairro());
        this.tfCep.setText(cliente.getCep());
        this.tfCodigoCidade.setText("" + cliente.getCidade().getIdCidade());
        this.tfNomeCidade.setText(cliente.getCidade().getNomeCidade());
        this.tfUfCidade.setText(cliente.getCidade().getUf());
        this.tfTelefone.setText(cliente.getTelefone());
        this.tfCelular.setText(cliente.getCelular());
        this.tfEmail.setText(cliente.getEmail());
        this.tfContato.setText(cliente.getContato());
    }

    private void calcularValorItem() {
        double quantidade = Double.parseDouble(this.tfQuantidade.getText().replaceAll("\\.", "").replace(",", "."));
        double valorUnitario = Double.parseDouble(this.tfValorVenda.getText().replaceAll("\\.", "").replace(",", "."));
        if (tfDesconto.getText().equals("")) {
            tfDesconto.setText("0,00");
        }
        double desconto = Double.parseDouble(this.tfDesconto.getText().replaceAll("\\.", "").replace(",", "."));
        double valorItem = (quantidade * valorUnitario) - ((quantidade * valorUnitario) * (desconto / 100));
        this.tfValorItem.setText(new DecimalFormat("###0.00").format(valorItem));
    }

    /**
     * Preenche a tabela de itens.
     */
    private void preencherTabelaItens() {
        ItemVendaDAO cd = DAOFactory.getDefaultDAOFactory().getItemVendaDAO();
        this.itens = cd.listarTodos();
        if (this.itens != null) {
            this.tbItemVenda.setModel(new ItemVendaTableModel(this.itens));
            this.tbItemVenda.setDefaultRenderer(Object.class, new ItemVendaCellRenderer());
        }
    }

    private void gravarVenda() {
        Venda v = new Venda();
        Cliente cliente = new Cliente();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVenda = tfDataVenda.getDate();
        v.setDataVenda(dataVenda);
        cliente.setId(Integer.parseInt(this.tfCodigoCliente.getText()));
        v.setObservacoes(this.taObservacoes.getText());
        v.setCliente(cliente);
        v.setEmpresa(empresa);
        VendaDAO vendaDao = DAOFactory.getDefaultDAOFactory().getVendaDAO();

        switch (opcao) {
            case OPCAO_INSERIR:
                if (vendaDao.inserir(v)) {
                    atualizarVendaNova();
                }
                break;
            case OPCAO_ALTERAR:
                atualizarVenda();
                break;
        }

    }

    /**
     * ygujfho
     */
    private void adicionarItem(double desconto, double valor,
            double quantidade, Produto produto) {

        ItemVenda item = new ItemVenda();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValor(valor);
        item.setDesconto(desconto);

        if (!itens.isEmpty()) {
            for (ItemVenda iv : itens) {
                if (iv.getProduto().getIdProduto() != item.getProduto().getIdProduto()) {
                    addItem(item);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Não é possível lançar o mesmo produto mais de uma vez.");
                }
            }
        } else {
            addItem(item);
        }
    }

    /**
     * @param iv Adiciona o item na lista.
     */
    private void addItem(ItemVenda iv) {
        this.itens.add(iv);
        this.tbItemVenda.setModel(new ItemVendaTableModel(itens));
        this.tfCodigoProduto.setText(null);
        this.tfDescricao.setText(null);
        this.tfQuantidade.setText("");
        this.tfValorVenda.setText("");
        this.tfDesconto.setText("");
        this.tfValorItem.setText("");
        this.tfCodigoProduto.requestFocus();
        calcularSub();
    }

    /**
     * <b>Calcula e seta (nos campos) o valor total do desconto e o valor total
     * da venda, de acordo com o lançamento dos itens.</b><br/><br/>
     * Para cada item da lista serão feitos os seguintes cálculos:<br/><br/>
     * <code>
     * subDesconto += ((quantidade * valorUnit) * (valorDesconto / 100));
     * <br/><br/>
     * subTotal += ((quantidade * valorUnit) - ((quantidade * valorUnit) *
     * (valorDesconto / 100)));
     * </code>
     * <br/><br/>
     * Após serem calculados, os valores de subDesconto e subTotal são setados
     * nos seus repectivos campos.
     */
    private void calcularSub() {
        double subDesconto = 0.00;
        double subTotal = 0.00;

        for (ItemVenda iv : itens) {

            subDesconto += ((iv.getQuantidade()
                    * iv.getValor())
                    * (iv.getDesconto() / 100));

            subTotal += ((iv.getValor()
                    * iv.getQuantidade()) - ((iv.getValor()
                    * iv.getQuantidade()) * (iv.getDesconto() / 100)));
        }

        this.tfSubDesconto.setText(StringUtils.formatarDecimal(subDesconto));
        this.tfSubTotal.setText(StringUtils.formatarDecimal(subTotal));
    }

    private void atualizarVendaNova() {
        VendaDAO vendaDao = DAOFactory.getDefaultDAOFactory().getVendaDAO();
        Venda ultimaVenda = vendaDao.getUltimaVenda();
        Venda v = new Venda();
        Date dataVenda = tfDataVenda.getDate();
        v.setIdVenda(ultimaVenda.getIdVenda());
        v.setDataVenda(dataVenda);
        v.setCliente(ultimaVenda.getCliente());
        v.setValorVenda(Double.parseDouble(
                tfSubTotal.getText().replaceAll("\\.", "").replace(",", ".")));
        v.setValorDesconto(Double.parseDouble(
                tfSubDesconto.getText().replaceAll("\\.", "").replace(",", ".")));
        v.setQuantParcela(titulos.size());
        v.setTitulosVenda(titulos);
        v.setItensVenda(itens);
        vendaDao.alterarVendaNova(v);
        initialize();
        imprimirVenda(ultimaVenda);
    }

    private void atualizarVenda() {
        Venda v = new Venda();
        Date dataVenda = tfDataVenda.getDate();
        v.setIdVenda(venda.getIdVenda());
        v.setDataVenda(dataVenda);
        Cliente cliente = new Cliente();
        cliente.setId(Integer.parseInt(tfCodigoCliente.getText()));
        v.setCliente(cliente);
        v.setEmpresa(empresa);
        v.setValorVenda(Double.parseDouble(
                tfSubTotal.getText().replaceAll("\\.", "").replace(",", ".")));
        v.setValorDesconto(Double.parseDouble(
                tfSubDesconto.getText().replaceAll("\\.", "").replace(",", ".")));
        v.setObservacoes(taObservacoes.getText());
        v.setQuantParcela(titulos.size());
        v.setTitulosVenda(titulos);
        v.setItensVenda(itens);
        VendaDAO vendaDAO = DAOFactory.getDefaultDAOFactory().getVendaDAO();
        vendaDAO.alterarVenda(v);
        initialize();
        imprimirVenda(v);
    }

    private void imprimirVenda(Venda venda) {
        int i = JOptionPane.showConfirmDialog(null,
                "Deseja imprimir?",
                "Pitanga System - Confirmação de impressão",
                JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            RelatorioDAO obj = DAOFactory.getDefaultDAOFactory().getRelatorioDAO();
            JasperViewer jv = obj.imprimirVenda(venda);
            FrmReport frmReport = new FrmReport(this, venda.getDescricaoTela(),
                    true, jv);
            frmReport.setVisible(true);
        }
        int op = JOptionPane.showConfirmDialog(null,
                "Deseja enviar por e-mail?",
                "Pitanga System - Confirmação de envio",
                JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            MailSender mailSender = new MailSender();
            mailSender.enviarVenda(venda);
        }
    }

    private boolean verificaValorVenda() {
        double somaParcelas = 0.00;
        double valorVenda = Double.parseDouble(
                tfSubTotal.getText().replaceAll("\\.", "").replace(",", "."));
        if (!titulos.isEmpty()) {
            for (TituloVenda tv : titulos) {
                somaParcelas += tv.getValorParcela();
            }
        }
        return somaParcelas == valorVenda;
    }

    private boolean verificaData() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataString = sdf.format(tfDataVenda.getDate());
            Date data = sdf.parse(dataString);
            return true;
        } catch (ParseException e) {
            System.err.println("Data inválida");
        }
        return false;
    }

    private void confirmarCancelamento() {
        int i = JOptionPane.showConfirmDialog(null,
                "Deseja realmente interromper o lançamento desta venda?\n",
                "Pitanga System - Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            initialize();
        }
    }

    public void setProduto(Produto p) {
        produto = p;
        tfCodigoProduto.setText(String.format("%05d", produto.getIdProduto()));
        tfDescricao.setText(produto.getDescricao());
        tfValorVenda.setText(new DecimalFormat("##0.00").format(produto.getValorVenda()));
    }

    public void preencherItem(ItemVenda item) {
        this.tfCodigoProduto.setText(String.format("%05d", item.getProduto().getIdProduto()));
        this.tfDescricao.setText(item.getProduto().getDescricao());
        this.tfQuantidade.setText(new DecimalFormat("##0.00").format(item.getQuantidade()));
        this.tfDesconto.setText(new DecimalFormat("##0.00").format(item.getDesconto()));
        this.tfValorVenda.setText(new DecimalFormat("##0.00").format(item.getValor()));
        this.tfValorItem.setText(new DecimalFormat("##0.00").format((item.getQuantidade() * item.getValor())
                - ((item.getQuantidade() * item.getValor())
                * (item.getDesconto() / 100))));
    }

    private void preencherTitulo(TituloVenda tv) {
        tfParcela.setText("" + tv.getNumParcela());
        tfVencimento.setDate(tv.getVencimento());
        tfValor.setText("" + tv.getValorParcela());
    }

    private void lancarParcelas(Long quantParcela, Long prazo) {

        double valorVenda = Double.parseDouble(tfSubTotal.getText().replaceAll("\\.", "").replace(",", "."));
        double valorParcela = (valorVenda / quantParcela);

        Calendar c = Calendar.getInstance();
        c.setTime(tfDataVenda.getDate());

        for (int i = 0; i < quantParcela; i++) {
            c.add(Calendar.DATE, +prazo.intValue());
            Date vencimento = c.getTime();

            TituloVenda titulo = new TituloVenda();
            titulo.setNumParcela(i + 1);
            titulo.setValorParcela(valorParcela);
            titulo.setVencimento(vencimento);

            titulos.add(titulo);
    }
        this.tbTituloVenda.setModel(new TituloVendaTableModel(titulos));
    }

    private void menuContextoTabelaItens() {

        JPopupMenu popUpMenu = new JPopupMenu();

        JMenuItem itemAlterar = new JMenuItem("Alterar");

        itemAlterar.addActionListener(
                (ActionEvent e) -> {
                    int index = tbItemVenda.getSelectedRow();
                    if (index != -1) {
                        ItemVendaTableModel obj = new ItemVendaTableModel(itens);
                        ItemVenda item = obj.get(index);
                        preencherItem(item);
                        tfCodigoProduto.requestFocus();
                        itens.remove(item);
                        tbItemVenda.setModel(new ItemVendaTableModel(itens));
                        calcularSub();
                    }
                }
        );

        JMenuItem itemExcluir = new JMenuItem("Excluir");

        itemExcluir.addActionListener(
                (ActionEvent e) -> {
                    int index = tbItemVenda.getSelectedRow();
                    if (index != -1) {
                        ItemVendaTableModel obj = new ItemVendaTableModel(itens);
                        ItemVenda item = obj.get(index);
                        itens.remove(item);
                        tbItemVenda.setModel(new ItemVendaTableModel(itens));
                        calcularSub();
                    }
                }
        );

        popUpMenu.add(itemAlterar);
        popUpMenu.addSeparator();
        popUpMenu.add(itemExcluir);

        tbItemVenda.addMouseListener(
                new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e
            ) {
                // Se o botão direito do mouse foi pressionado
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // Exibe o popup menu na posição do mouse.
                    popUpMenu.show(tbItemVenda, e.getX(), e.getY());
                }
            }
        }
        );

        tbItemVenda.addKeyListener(
                new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Se a tecla de menu contexto do teclado for pressionada
                if (e.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
                    if (!itens.isEmpty()) {
                        popUpMenu.show(tbItemVenda, 200, 100);
                    }
                }
            }
        });
    }

    private void menuContextoTabelaTitulos() {

        JPopupMenu popUpMenu = new JPopupMenu();

        JMenuItem itemAlterar = new JMenuItem("Alterar");

        itemAlterar.addActionListener((ActionEvent e) -> {
            int index = tbTituloVenda.getSelectedRow();
            if (index != -1) {
                TituloVendaTableModel obj = new TituloVendaTableModel(titulos);
                TituloVenda tituloVenda = obj.get(index);
                preencherTitulo(tituloVenda);
                tfValor.requestFocus();
                titulos.remove(tituloVenda);
                tbTituloVenda.setModel(new TituloVendaTableModel(titulos));
            }
        });

        JMenuItem itemExcluir = new JMenuItem("Excluir");

        itemExcluir.addActionListener((ActionEvent e) -> {
            int index = tbTituloVenda.getSelectedRow();
            if (index != -1) {
                TituloVendaTableModel obj = new TituloVendaTableModel(titulos);
                TituloVenda tituloVenda = obj.get(index);
                titulos.remove(tituloVenda);
                tbTituloVenda.setModel(new TituloVendaTableModel(titulos));
            }
        });

        popUpMenu.add(itemAlterar);
        popUpMenu.addSeparator();
        popUpMenu.add(itemExcluir);

        tbTituloVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Se o botão direito do mouse foi pressionado
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // Exibe o popup menu na posição do mouse.
                    popUpMenu.show(tbTituloVenda, e.getX(), e.getY());
                }
            }
        });

        tbTituloVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Se a tecla de menu contexto do teclado for pressionada
                if (e.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
                    if (!itens.isEmpty()) {
                        popUpMenu.show(tbTituloVenda, 200, 100);
                    }
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btBuscarCliente;
    private javax.swing.JButton btBuscarProduto;
    private javax.swing.JButton btCalcular;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btGravar;
    private javax.swing.JButton btLimpar;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbCodigo;
    private javax.swing.JTextArea taObservacoes;
    private javax.swing.JTable tbItemVenda;
    private javax.swing.JTable tbTituloVenda;
    private javax.swing.JTextField tfApelido;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JTextField tfCelular;
    private javax.swing.JTextField tfCep;
    private javax.swing.JTextField tfCodigoCidade;
    private javax.swing.JTextField tfCodigoCliente;
    private javax.swing.JTextField tfCodigoProduto;
    private javax.swing.JTextField tfContato;
    private javax.swing.JTextField tfCpfCnpj;
    private com.toedter.calendar.JDateChooser tfDataVenda;
    private javax.swing.JFormattedTextField tfDesconto;
    private javax.swing.JTextField tfDescricao;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEndereco;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfNomeCidade;
    private javax.swing.JTextField tfNumero;
    private javax.swing.JTextField tfParcela;
    private javax.swing.JTextField tfPrazo;
    private javax.swing.JTextField tfQuantParcelas;
    private javax.swing.JTextField tfQuantidade;
    private javax.swing.JTextField tfRgInscricao;
    private javax.swing.JTextField tfSubDesconto;
    private javax.swing.JTextField tfSubTotal;
    private javax.swing.JTextField tfTelefone;
    private javax.swing.JTextField tfTotalParcelas;
    private javax.swing.JTextField tfUfCidade;
    private javax.swing.JTextField tfValor;
    private javax.swing.JTextField tfValorItem;
    private javax.swing.JTextField tfValorVenda;
    private com.toedter.calendar.JDateChooser tfVencimento;
    private javax.swing.JTabbedPane tpPrincipal;
    // End of variables declaration//GEN-END:variables
}
