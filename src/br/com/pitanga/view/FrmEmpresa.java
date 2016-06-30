package br.com.pitanga.view;

import br.com.pitanga.domain.Cidade;
import br.com.pitanga.domain.Empresa;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.dao.model.EmpresaDAO;
import br.com.pitanga.util.GUIUtils;
import br.com.pitanga.util.ImageFilter;
import br.com.pitanga.util.MaskUtil;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Douglas Gusson
 */
public class FrmEmpresa extends javax.swing.JDialog {

    private List<Empresa> empresas = new ArrayList<>();
    private Empresa empresa;

    /**
     * Creates new form FrmEmpresa
     *
     * @param parent
     * @param modal
     */
    public FrmEmpresa(JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initialize();
    }

    private void definirManipuladores() {

        btSair.addActionListener((ActionEvent e) -> {
            FrmEmpresa.this.dispose();
        });

        btBuscaLogo.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new ImageFilter());
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                tfLogomarca.setText(file.getAbsolutePath());
            }
        });

        btAlterar.addActionListener((ActionEvent e) -> {
            habilitarEdicao();
        });

        btCancelar.addActionListener((ActionEvent e) -> {
            initialize();
        });

        btBuscarCidade.addActionListener((ActionEvent e) -> {
            FrmBuscaCidade obj = new FrmBuscaCidade(this, true, this);
            obj.setVisible(true);
        });

        btGravar.addActionListener((ActionEvent e) -> {
            gravarEmpresa();
        });
    }

    private void habilitarEdicao() {
        // Botões
        this.btSair.setEnabled(false);
        this.btAlterar.setEnabled(false);
        this.btCancelar.setEnabled(true);
        this.btGravar.setEnabled(true);
        this.btBuscarCidade.setEnabled(true);
        this.btBuscaLogo.setEnabled(true);
        // Campos
        this.tfNome.setEnabled(true);
        this.tfNomeFantasia.setEnabled(true);
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
        this.tfUf.setEnabled(true);
        this.tfLogomarca.setEnabled(true);
        // Setar foco
        this.tfNome.requestFocus();
    }

    private boolean gravarEmpresa() {
        if ((this.tfNome.getText().trim()).equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Favor informar o nome da empresa!",
                    "Campo obrigatório", JOptionPane.INFORMATION_MESSAGE);
            this.tfNome.requestFocus();
        } else if (this.tfCodigoCidade.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Favor informar a cidade da empresa!",
                    "Campo obrigatório", JOptionPane.INFORMATION_MESSAGE);
            this.btBuscarCidade.requestFocus();
        } else {
            Empresa emp = new Empresa();
            Cidade cidade = new Cidade();
            emp.setIdPessoa(empresa.getIdPessoa());
            emp.setNome(tfNome.getText().trim());
            emp.setNomeFantasia(tfNomeFantasia.getText().trim());
            emp.setCnpj(tfCpfCnpj.getText().trim());
            emp.setInscricao(tfRgInscricao.getText().trim());
            emp.setEndereco(tfEndereco.getText().trim());
            emp.setNumero(tfNumero.getText().trim());
            emp.setBairro(tfBairro.getText().trim());
            emp.setCep(tfCep.getText().trim());
            cidade.setIdCidade(Integer.parseInt(tfCodigoCidade.getText().trim()));
            emp.setCidade(cidade);
            emp.setTelefone(tfTelefone.getText().trim());
            emp.setCelular(tfCelular.getText().trim());
            emp.setEmail(tfEmail.getText().trim());
            emp.setLogomarca(tfLogomarca.getText());
            EmpresaDAO empresaDAO = DAOFactory.getDefaultDAOFactory().getEmpresaDAO();
            if (empresaDAO.alterar(emp)) {
                JOptionPane.showMessageDialog(rootPane, "Alterações gravadas!");
                initialize();
            }
            return true;
        }
        return false;
    }

    private void setEmpresa(Empresa empresa) {
        this.tfNome.setText(empresa.getNome());
        this.tfNomeFantasia.setText(empresa.getNomeFantasia());
        this.tfCpfCnpj.setText(empresa.getCnpj());
        this.tfRgInscricao.setText(empresa.getInscricao());
        this.tfEndereco.setText(empresa.getEndereco());
        this.tfNumero.setText(empresa.getNumero());
        this.tfBairro.setText(empresa.getBairro());
        this.tfCep.setText(empresa.getCep());
        this.tfCodigoCidade.setText("" + empresa.getCidade().getIdCidade());
        this.tfNomeCidade.setText(empresa.getCidade().getNomeCidade());
        this.tfUf.setText(empresa.getCidade().getUf());
        this.tfTelefone.setText(empresa.getTelefone());
        this.tfCelular.setText(empresa.getCelular());
        this.tfEmail.setText(empresa.getEmail());
        this.tfLogomarca.setText(empresa.getLogomarca());
    }

    private void initialize() {
        definirManipuladores();
        // Habilita/desabilita os botões
        this.btSair.setEnabled(true);
        this.btAlterar.setEnabled(true);
        this.btCancelar.setEnabled(false);
        this.btGravar.setEnabled(false);
        this.btBuscarCidade.setEnabled(false);
        this.btBuscaLogo.setEnabled(false);
        // Desabilita os campos    
        this.tfNome.setEnabled(false);
        this.tfNomeFantasia.setEnabled(false);
        this.tfCpfCnpj.setEnabled(false);
        this.tfRgInscricao.setEnabled(false);
        this.tfEndereco.setEnabled(false);
        this.tfNumero.setEnabled(false);
        this.tfBairro.setEnabled(false);
        this.tfCep.setEnabled(false);
        this.tfCodigoCidade.setEnabled(false);
        this.tfNomeCidade.setEnabled(false);
        this.tfUf.setEnabled(false);
        this.tfTelefone.setEnabled(false);
        this.tfCelular.setEnabled(false);
        this.tfEmail.setEnabled(false);
        this.tfLogomarca.setEnabled(false);
        this.setTitle("Pitanga System - Empresa");
        GUIUtils.considerarEnterComoTab(this);
        setarMascara();
        EmpresaDAO empresaDAO = DAOFactory.getDefaultDAOFactory().getEmpresaDAO();
        empresas = empresaDAO.listarTodos();
        empresa = empresas.get(0);
        setEmpresa(empresa);
    }

    public void setCidade(Cidade cidade) {
        tfCodigoCidade.setText("" + cidade.getIdCidade());
        tfNomeCidade.setText(cidade.getNomeCidade());
        tfUf.setText(cidade.getUf());
    }

    private void setarMascara() {
        try {
            MaskUtil mascara = new MaskUtil();
            mascara.maskCelular(tfCelular);
            mascara.maskTelFixo(tfTelefone);
            mascara.maskCep(tfCep);
        } catch (ParseException ex) {
            Logger.getLogger(FrmEmpresa.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        tfEndereco = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblCpfCnpj = new javax.swing.JLabel();
        lblRgInscricao = new javax.swing.JLabel();
        tfRgInscricao = new javax.swing.JTextField();
        btAlterar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
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
        tfCodigoCidade = new javax.swing.JTextField();
        tfNomeCidade = new javax.swing.JTextField();
        tfUf = new javax.swing.JTextField();
        btBuscarCidade = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        tfCelular = new javax.swing.JFormattedTextField();
        tfTelefone = new javax.swing.JFormattedTextField();
        tfCpfCnpj = new javax.swing.JFormattedTextField();
        tfCep = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        tfNomeFantasia = new javax.swing.JTextField();
        btSair = new javax.swing.JButton();
        btGravar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        tfLogomarca = new javax.swing.JTextField();
        btBuscaLogo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clientes");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Razão social:");

        jLabel2.setText("Endereço:");

        lblCpfCnpj.setText("CNPJ:");

        lblRgInscricao.setText("Inscrição:");

        btAlterar.setText("Alterar");

        btCancelar.setText("Cancelar");

        jLabel6.setText("Número:");

        jLabel7.setText("Bairro:");

        jLabel8.setText("Telefone:");

        jLabel9.setText("Celular:");

        jLabel10.setText("E-mail:");

        jLabel11.setText("CEP:");

        jLabel12.setText("UF:");

        jLabel13.setText("Cidade:");

        tfCodigoCidade.setEditable(false);
        tfCodigoCidade.setBackground(new java.awt.Color(255, 255, 255));
        tfCodigoCidade.setFocusable(false);

        tfNomeCidade.setEditable(false);
        tfNomeCidade.setBackground(new java.awt.Color(255, 255, 255));
        tfNomeCidade.setFocusable(false);

        tfUf.setEditable(false);
        tfUf.setBackground(new java.awt.Color(255, 255, 255));
        tfUf.setFocusable(false);

        btBuscarCidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/pesquisa_16x16.png"))); // NOI18N

        jLabel14.setText("Cod. cidade:");

        jLabel3.setText("Nome fantasia:");

        btSair.setText("Sair");

        btGravar.setText("Gravar");

        jLabel4.setText("Logomarca:");

        btBuscaLogo.setText("...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tfEndereco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tfNome, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(tfBairro)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tfNomeFantasia)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(tfTelefone))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(tfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(tfCep, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                    .addComponent(tfCodigoCidade))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btBuscarCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btGravar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btSair))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfUf, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12)))
                                    .addComponent(tfEmail)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCpfCnpj)
                                    .addComponent(tfCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRgInscricao)
                                    .addComponent(tfRgInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfLogomarca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btBuscaLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(tfCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblCpfCnpj)
                                .addGap(26, 26, 26))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblRgInscricao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfRgInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfCodigoCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tfUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(btBuscarCidade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tfCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfLogomarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btBuscaLogo))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAlterar)
                    .addComponent(btCancelar)
                    .addComponent(btSair)
                    .addComponent(btGravar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btBuscaLogo;
    private javax.swing.JButton btBuscarCidade;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btGravar;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCpfCnpj;
    private javax.swing.JLabel lblRgInscricao;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JFormattedTextField tfCelular;
    private javax.swing.JFormattedTextField tfCep;
    private javax.swing.JTextField tfCodigoCidade;
    private javax.swing.JFormattedTextField tfCpfCnpj;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEndereco;
    private javax.swing.JTextField tfLogomarca;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfNomeCidade;
    private javax.swing.JTextField tfNomeFantasia;
    private javax.swing.JTextField tfNumero;
    private javax.swing.JTextField tfRgInscricao;
    private javax.swing.JFormattedTextField tfTelefone;
    private javax.swing.JTextField tfUf;
    // End of variables declaration//GEN-END:variables
}
