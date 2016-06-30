package br.com.pitanga.view;

import br.com.pitanga.dao.model.ClienteDAO;
import br.com.pitanga.domain.Cidade;
import br.com.pitanga.domain.Cliente;
import br.com.pitanga.table.cellrenderer.ClienteCellRenderer;
import br.com.pitanga.table.model.ClienteTableModel;
import br.com.pitanga.util.GUIUtils;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.util.MaskUtil;
import br.com.pitanga.util.UtilCpfCnpj;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Douglas Gusson
 */
public class FrmCliente extends javax.swing.JDialog {

    private List<Cliente> clientes;

    public FrmCliente(Window parent, boolean modal) {
        super(parent, DEFAULT_MODALITY_TYPE);
        initComponents();
        initListeners();
        initialize();
    }

    private void initListeners() {

        tbCliente.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            int selected = tbCliente.getSelectedRow();
            if (selected != -1) {
                ClienteTableModel clienteTableModel = new ClienteTableModel(clientes);
                Cliente c = clienteTableModel.get(selected);
                preencherCamposCliente(c);
            }
        });

        ActionListener sair = (ActionEvent e) -> {
            FrmCliente.this.dispose();
        };

        btSair.addActionListener(sair);

        ActionListener novo = (ActionEvent e) -> {
            novoCliente();
        };

        btNovo.addActionListener(novo);

        ActionListener cancela = (ActionEvent e) -> {
            initialize();
        };

        btCancelar.addActionListener(cancela);

        ActionListener grava = (ActionEvent e) -> {
            if (tfCodigo.isVisible()) {
                gravarAlteracoesCliente();
            } else {
                gravarCliente();
            }
        };

        btGravar.addActionListener(grava);

        ActionListener exclui = (ActionEvent e) -> {
            excluirCliente();
        };

        btExcluir.addActionListener(exclui);

        ActionListener altera = (ActionEvent e) -> {
            alterarCliente();
        };

        btAlterar.addActionListener(altera);

        ActionListener buscaCidade = (ActionEvent e) -> {
            FrmBuscaCidade obj = new FrmBuscaCidade(this, true, this);
            obj.setVisible(true);
        };

        btBuscarCidade.addActionListener(buscaCidade);

        KeyAdapter pesquisaCliente = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String termo = tfPesquisa.getText();
                preencherTabelaPesquisa(termo);
            }
        };

        tfPesquisa.addKeyListener(pesquisaCliente);

        FocusAdapter saidaFocoCpf = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                verificarDocumento();
            }
        };

        FocusAdapter entradaFocoCpf = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                removerSeparadores();
            }
        };

        tfCpfCnpj.addFocusListener(saidaFocoCpf);
        tfCpfCnpj.addFocusListener(entradaFocoCpf);

    }

    private void verificarDocumento() {
        String s = tfCpfCnpj.getText()
                .replaceAll("\\.", "")
                .replace("-", "")
                .replace("/", "");
        if (s.length() == 11) {
            tfCpfCnpj.setText(UtilCpfCnpj.imprimeCPF(s));
        } else if (s.length() == 14) {
            tfCpfCnpj.setText(UtilCpfCnpj.imprimeCNPJ(s));
        } else {
            JOptionPane.showMessageDialog(
                    null, "Documento inválido!");
        }
    }

    private void removerSeparadores() {
        String s = tfCpfCnpj.getText()
                .replaceAll("\\.", "")
                .replace("-", "")
                .replace("/", "");
        tfCpfCnpj.setText(s);
    }

    private void preencherTabela() {
        ClienteDAO cd = DAOFactory.getDefaultDAOFactory().getClienteDAO();
        clientes = cd.listarTodos();
        if (clientes != null) {
            tbCliente.setModel(new ClienteTableModel(clientes));
            tbCliente.setDefaultRenderer(Object.class, new ClienteCellRenderer());
        }
    }

    private void preencherTabelaPesquisa(String nome) {
        ClienteDAO cd = DAOFactory.getDefaultDAOFactory().getClienteDAO();
        clientes = cd.buscarPorNome(nome);
        tbCliente.setModel(new ClienteTableModel(clientes));
        tbCliente.setDefaultRenderer(Object.class, new ClienteCellRenderer());
    }

    private void novoCliente() {
        initialize();
        // Botões
        this.btAlterar.setEnabled(false);
        this.btExcluir.setEnabled(false);
        this.btSair.setEnabled(false);
        this.btNovo.setEnabled(false);
        this.btCancelar.setEnabled(true);
        this.btGravar.setEnabled(true);
        this.btBuscarCidade.setEnabled(true);
        tfCodigoCidade.setEnabled(true);
        tfNomeCidade.setEnabled(true);
        tfUfCidade.setEnabled(true);
        // Tabela
        this.tbCliente.setEnabled(false);
        this.tbCliente.setRowSelectionAllowed(false);
        // Campos
        this.tfPesquisa.setEnabled(false);
        this.tfNome.setEnabled(true);
        this.tfApelido.setEnabled(true);
        this.tfCpfCnpj.setEnabled(true);
        this.tfRgInscricao.setEnabled(true);
        this.tfEndereco.setEnabled(true);
        this.tfNumero.setEnabled(true);
        this.tfBairro.setEnabled(true);
        this.tfCep.setEnabled(true);
        this.tfTelefone.setEnabled(true);
        this.tfCelular.setEnabled(true);
        this.tfContato.setEnabled(true);
        this.tfEmail.setEnabled(true);
        this.tfCodigoCidade.setEnabled(true);
        this.tfNomeCidade.setEnabled(true);
        this.tfUfCidade.setEnabled(true);
        // Setar foco
        this.tfNome.requestFocus();
    }

    private void gravarCliente() {

        if ((this.tfNome.getText().trim()).equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Favor informar o nome do cliente!",
                    "Campo obrigatório", JOptionPane.INFORMATION_MESSAGE);
            this.tfNome.requestFocus();
        } else if (this.tfCodigoCidade.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Favor informar a cidade do cliente!",
                    "Campo obrigatório", JOptionPane.INFORMATION_MESSAGE);
            this.btBuscarCidade.requestFocus();
        } else {

            Cliente cliente = new Cliente();
            Cidade cidade = new Cidade();

            cliente.setNome(this.tfNome.getText().trim());
            cliente.setApelido(this.tfApelido.getText().trim());
            cliente.setCpfCnpj(this.tfCpfCnpj.getText().trim());
            cliente.setRgInscricao(this.tfRgInscricao.getText().trim());
            cliente.setEndereco(this.tfEndereco.getText().trim());
            cliente.setNumero(this.tfNumero.getText().trim());
            cliente.setBairro(this.tfBairro.getText().trim());
            cliente.setCep(this.tfCep.getText().trim());
            cidade.setIdCidade(Integer.parseInt(this.tfCodigoCidade.getText().trim()));
            cliente.setCidade(cidade);
            cliente.setTelefone(this.tfTelefone.getText().trim());
            cliente.setCelular(this.tfCelular.getText().trim());
            cliente.setContato(this.tfContato.getText().trim());
            cliente.setEmail(this.tfEmail.getText().trim());

            ClienteDAO cliDao = DAOFactory.getDefaultDAOFactory().getClienteDAO();
            cliDao.inserir(cliente);

            initialize();
        }
    }

    private void excluirCliente() {

        int selecionada = this.tbCliente.getSelectedRow();

        if (selecionada == -1) {
            JOptionPane.showMessageDialog(rootPane,
                    "- NENHUM CLIENTE SELECIONADO -",
                    "Pitanga System - EXCLUIR",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {

            ClienteTableModel ctm = new ClienteTableModel(clientes);
            Cliente cli = ctm.get(selecionada);

            int codigo = cli.getId();
            String nome = cli.getNome();
            String cpf = cli.getCpfCnpj();

            int i = JOptionPane.showConfirmDialog(null,
                    "DESEJA MESMO EXCLIUR O CLIENTE ABAIXO?\n\n"
                    + "Código: " + String.format("%05d", codigo)
                    + "\nNome: " + nome
                    + "\nCPF/CNPJ: " + cpf + "\n\n",
                    "Pitanga System - Confirmação de exclusão",
                    JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                ClienteDAO cd = DAOFactory.getDefaultDAOFactory().getClienteDAO();
                if (cd.remover(codigo)) {
                    JOptionPane.showMessageDialog(rootPane, "Cliente removido!");
                }
            }
        }
        initialize();
    }

    private void alterarCliente() {

        int selecionada = this.tbCliente.getSelectedRow();

        if (selecionada == -1) {
            JOptionPane.showMessageDialog(rootPane,
                    "- NENHUM CLIENTE SELECIONADO -",
                    "Pitanga System - ALTERAR",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {

//            ClienteDAO cliDao = DAOFactory.getDefaultDAOFactory().getClienteDAO();
//            Cliente cli = cliDao.buscar(Integer.parseInt(
//                    (this.tbCliente.getValueAt(selecionada, 0)).toString()));
            ClienteTableModel ctm = new ClienteTableModel(clientes);
            Cliente cli = ctm.get(selecionada);

            novoCliente();
            this.tfCodigo.setVisible(true);
            this.lbCodigo.setVisible(true);
            this.btGravar.setEnabled(true);
            this.btAlterar.setEnabled(false);
            this.btExcluir.setEnabled(false);
            this.tfPesquisa.setEnabled(false);
            this.tbCliente.setEnabled(false);

            preencherCamposCliente(cli);
        }
    }

    private void preencherCamposCliente(Cliente cli) {
        tfNome.setText(cli.getNome());
        tfApelido.setText(cli.getApelido());
        tfCpfCnpj.setText(cli.getCpfCnpj());
        tfRgInscricao.setText(cli.getRgInscricao());
        tfEndereco.setText(cli.getEndereco());
        tfNumero.setText(cli.getNumero());
        tfBairro.setText(cli.getBairro());
        tfCep.setText(cli.getCep());
        tfCodigoCidade.setText("" + cli.getCidade().getIdCidade());
        tfNomeCidade.setText(cli.getCidade().getNomeCidade());
        tfUfCidade.setText(cli.getCidade().getUf());
        tfTelefone.setText(cli.getTelefone());
        tfCelular.setText(cli.getCelular());
        tfContato.setText(cli.getContato());
        tfEmail.setText(cli.getEmail());
        tfCodigo.setText(String.format("%05d", cli.getId()));
    }

    private void gravarAlteracoesCliente() {

        if ((this.tfNome.getText().trim()).equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Favor informar o nome do cliente!",
                    "Campo obrigatório", JOptionPane.INFORMATION_MESSAGE);
            this.tfNome.requestFocus();
        } else if (this.tfCodigoCidade.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Favor informar a cidade do cliente!",
                    "Campo obrigatório", JOptionPane.INFORMATION_MESSAGE);
            this.btBuscarCidade.requestFocus();
        } else {
            Cliente cliente = new Cliente();
            Cidade cidade = new Cidade();

            cliente.setNome(this.tfNome.getText().trim());
            cliente.setApelido(this.tfApelido.getText().trim());
            cliente.setCpfCnpj(this.tfCpfCnpj.getText().trim());
            cliente.setRgInscricao(this.tfRgInscricao.getText().trim());
            cliente.setEndereco(this.tfEndereco.getText().trim());
            cliente.setNumero(this.tfNumero.getText().trim());
            cliente.setBairro(this.tfBairro.getText().trim());
            cliente.setCep(this.tfCep.getText().trim());
            cidade.setIdCidade(Integer.parseInt(this.tfCodigoCidade.getText().trim()));
            cliente.setCidade(cidade);
            cliente.setTelefone(this.tfTelefone.getText().trim());
            cliente.setCelular(this.tfCelular.getText().trim());
            cliente.setContato(this.tfContato.getText().trim());
            cliente.setEmail(this.tfEmail.getText().trim());
            cliente.setId(Integer.parseInt(this.tfCodigo.getText()));

            ClienteDAO cliDao = DAOFactory.getDefaultDAOFactory().getClienteDAO();
            cliDao.alterar(cliente);
            initialize();
        }
    }

    private void initialize() {
        // Preenche a tabela de clientes
        preencherTabela();

        buttonGroup1.clearSelection();
        lblCpfCnpj.setText("CPF/CNPJ:");
        lblRgInscricao.setText("RG/Insc.:");

        // Habilita o campo de pesquisa
        this.tfPesquisa.setEnabled(true);
        // Habilita a tabela de clientes
        this.tbCliente.setEnabled(true);
        // Habilita a seleção na tabela
        this.tbCliente.setRowSelectionAllowed(true);
        // Habilita/desabilita os botões
        this.btSair.setEnabled(true);
        this.btNovo.setEnabled(true);
        this.btCancelar.setEnabled(false);
        this.btGravar.setEnabled(false);
        this.btAlterar.setEnabled(true);
        this.btExcluir.setEnabled(true);
        this.btBuscarCidade.setEnabled(false);
        tfCodigoCidade.setEnabled(false);
        tfNomeCidade.setEnabled(false);
        tfUfCidade.setEnabled(false);
        // Desabilita os campos    
        this.tfCodigo.setVisible(false);
        this.lbCodigo.setVisible(false);
        this.tfNome.setEnabled(false);
        this.tfApelido.setEnabled(false);
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
        this.tfContato.setEnabled(false);
        this.tfEmail.setEnabled(false);
        // Limpa os campos
        this.tfCodigo.setText("");
        this.tfNome.setText("");
        this.tfApelido.setText("");
        this.tfEndereco.setText("");
        this.tfNumero.setText("");
        this.tfBairro.setText("");
        this.tfCep.setText("");
        this.tfCodigoCidade.setText("");
        this.tfNomeCidade.setText("");
        this.tfUfCidade.setText("");
        this.tfCpfCnpj.setText("");
        this.tfRgInscricao.setText("");
        this.tfTelefone.setText("");
        this.tfCelular.setText("");
        this.tfContato.setText("");
        this.tfEmail.setText("");
        this.tfPesquisa.setText("");
        this.setTitle("Pitanga System - Clientes");
        GUIUtils.considerarEnterComoTab(this);
        setarMascara();

        // Se teclarmos ESC nesta janela, ela irá se fechar:  
        this.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            FrmCliente.this.dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void preencherCamposCidade(Cidade cidade) {
        this.tfCodigoCidade.setText("" + cidade.getIdCidade());
        this.tfNomeCidade.setText(cidade.getNomeCidade());
        this.tfUfCidade.setText(cidade.getUf());
    }

    private void setarMascara() {
        try {
            MaskUtil mascara = new MaskUtil();
            mascara.maskCelular(tfCelular);
            mascara.maskTelFixo(tfTelefone);
            mascara.maskCep(tfCep);
        } catch (ParseException ex) {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCliente = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        tfEndereco = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblCpfCnpj = new javax.swing.JLabel();
        lblRgInscricao = new javax.swing.JLabel();
        tfRgInscricao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfNumero = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfBairro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbCodigo = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        tfCodigoCidade = new javax.swing.JTextField();
        tfNomeCidade = new javax.swing.JTextField();
        tfUfCidade = new javax.swing.JTextField();
        btBuscarCidade = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        tfCelular = new javax.swing.JFormattedTextField();
        tfTelefone = new javax.swing.JFormattedTextField();
        tfCpfCnpj = new javax.swing.JFormattedTextField();
        tfCep = new javax.swing.JFormattedTextField();
        tfApelido = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfContato = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        btNovo = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btGravar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        tfPesquisa = new javax.swing.JTextField();
        btSair = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clientes");
        setResizable(false);

        tbCliente.setAutoscrolls(false);
        tbCliente.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbCliente);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nome / Razão social:");

        tfNome.setToolTipText("Nome / Razão Social");

        jLabel2.setText("Endereço:");

        lblCpfCnpj.setText("CPF / CNPJ:");

        lblRgInscricao.setText("RG / Inscrição:");

        jLabel6.setText("Número:");

        jLabel7.setText("Bairro:");

        jLabel8.setText("Telefone:");

        jLabel9.setText("Celular:");

        jLabel10.setText("E-mail:");

        jLabel11.setText("CEP:");

        jLabel12.setText("UF:");

        jLabel13.setText("Cidade:");

        lbCodigo.setText("Código:");

        tfCodigo.setEditable(false);
        tfCodigo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tfCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfCodigo.setFocusable(false);

        tfCodigoCidade.setEditable(false);
        tfCodigoCidade.setBackground(new java.awt.Color(255, 255, 255));
        tfCodigoCidade.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tfCodigoCidade.setFocusable(false);

        tfNomeCidade.setEditable(false);
        tfNomeCidade.setBackground(new java.awt.Color(255, 255, 255));
        tfNomeCidade.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tfNomeCidade.setFocusable(false);

        tfUfCidade.setEditable(false);
        tfUfCidade.setBackground(new java.awt.Color(255, 255, 255));
        tfUfCidade.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tfUfCidade.setFocusable(false);

        btBuscarCidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/pesquisa_16x16.png"))); // NOI18N
        btBuscarCidade.setToolTipText("Buscar cidade");

        jLabel14.setText("Cod. cidade:");

        tfApelido.setToolTipText("Apelido / Nome Fantasia");

        jLabel3.setText("Apelido / Nome fantasia:");

        jLabel4.setText("Contato:");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/new_20x20.png"))); // NOI18N
        btNovo.setText("Novo");
        btNovo.setToolTipText("Novo cadastro de cliente");
        jToolBar1.add(btNovo);

        btCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/cancel_20x20.png"))); // NOI18N
        btCancelar.setText("Cancelar");
        btCancelar.setToolTipText("Cancelar cadastro");
        jToolBar1.add(btCancelar);

        btGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/save_20x20.png"))); // NOI18N
        btGravar.setText("Gravar");
        btGravar.setToolTipText("Gravar o cadastro do cliente");
        jToolBar1.add(btGravar);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(tfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tfBairro)
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(tfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(tfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(tfContato)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                        .addComponent(tfCep, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(tfCodigoCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btBuscarCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel13)
                                                    .addComponent(tfNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(tfUfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(tfApelido)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCpfCnpj)
                                    .addComponent(tfCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRgInscricao)
                                    .addComponent(tfRgInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfApelido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRgInscricao)
                            .addComponent(lblCpfCnpj))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfRgInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tfCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfCodigoCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btBuscarCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(tfUfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfTelefone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(26, 26, 26))))
                    .addComponent(tfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(26, 26, 26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbCodigo)
                        .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setText("Pesquisa:");

        btSair.setText("Sair");
        btSair.setToolTipText("Sair");

        btExcluir.setText("Excluir");
        btExcluir.setToolTipText("Excluir cadastro de cliente");

        btAlterar.setText("Alterar");
        btAlterar.setToolTipText("Alterar cadastro de cliente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btSair))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfPesquisa)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btExcluir)
                    .addComponent(btAlterar)
                    .addComponent(tfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSair)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btBuscarCidade;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btGravar;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSair;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lblCpfCnpj;
    private javax.swing.JLabel lblRgInscricao;
    private javax.swing.JTable tbCliente;
    private javax.swing.JTextField tfApelido;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JFormattedTextField tfCelular;
    private javax.swing.JFormattedTextField tfCep;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfCodigoCidade;
    private javax.swing.JTextField tfContato;
    private javax.swing.JFormattedTextField tfCpfCnpj;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEndereco;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfNomeCidade;
    private javax.swing.JTextField tfNumero;
    private javax.swing.JTextField tfPesquisa;
    private javax.swing.JTextField tfRgInscricao;
    private javax.swing.JFormattedTextField tfTelefone;
    private javax.swing.JTextField tfUfCidade;
    // End of variables declaration//GEN-END:variables
}
