package br.com.pitanga.view;

import br.com.pitanga.dao.model.ClienteDAO;
import br.com.pitanga.dao.model.RelatorioDAO;
import br.com.pitanga.dao.model.TituloVendaDAO;
import br.com.pitanga.domain.Cliente;
import br.com.pitanga.domain.TituloVenda;
import br.com.pitanga.domain.Venda;
import br.com.pitanga.table.cellrenderer.ConsultaTituloVendaCellRenderer;
import br.com.pitanga.table.model.ConsultaTituloVendaTableModel;
import br.com.pitanga.util.GUIUtils;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Douglas Gusson
 * @date 22/09/2015
 * @time 21:35:18
 */
public class FrmConsultaTituloVenda extends javax.swing.JDialog {

    private List<TituloVenda> titulos = new ArrayList<>();
    private FrmRecebimento frmRecebimento;

    /**
     * Creates new form FrmImprimirVenda
     *
     * @param parent
     * @param modal
     */
    public FrmConsultaTituloVenda(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inicializarFrame();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupBuscarPor = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbConsultaTituloVenda = new javax.swing.JTable();
        btSair = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfDataInicio = new com.toedter.calendar.JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
        jLabel3 = new javax.swing.JLabel();
        tfDataFim = new com.toedter.calendar.JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
        rbEmissao = new javax.swing.JRadioButton();
        rbVencimento = new javax.swing.JRadioButton();
        btAplicarFiltro = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tfCodigoCliente = new javax.swing.JTextField();
        btFolderCliente = new javax.swing.JButton();
        tfNome = new javax.swing.JTextField();
        tfValorTotal = new javax.swing.JTextField();
        ckBTituloPagos = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        tbConsultaTituloVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbConsultaTituloVenda.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbConsultaTituloVenda.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbConsultaTituloVenda);

        btSair.setMnemonic('S');
        btSair.setText("Sair");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/print_16x16.png"))); // NOI18N
        jButton1.setMnemonic('p');
        jButton1.setText("imprimir venda");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/baixar_16x16.png"))); // NOI18N
        jButton3.setText("Baixar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Período de");

        jLabel3.setText("a");

        buttonGroup1.add(rbEmissao);
        rbEmissao.setText("Emissão");

        buttonGroup1.add(rbVencimento);
        rbVencimento.setText("Vencimento");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbEmissao)
                        .addGap(18, 18, 18)
                        .addComponent(rbVencimento))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(16, 16, 16)
                        .addComponent(tfDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbEmissao)
                    .addComponent(rbVencimento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tfDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        btAplicarFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/filtro_16x16.png"))); // NOI18N
        btAplicarFiltro.setText("Aplicar");
        btAplicarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAplicarFiltroActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/limpar_filtro_16x16.png"))); // NOI18N
        jButton4.setText("Limpar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setText("Cliente:");

        tfCodigoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfCodigoClienteFocusLost(evt);
            }
        });
        tfCodigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfCodigoClienteKeyTyped(evt);
            }
        });

        btFolderCliente.setForeground(new java.awt.Color(255, 255, 255));
        btFolderCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/pesquisa_16x16.png"))); // NOI18N
        btFolderCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFolderClienteActionPerformed(evt);
            }
        });

        tfNome.setEditable(false);
        tfNome.setBackground(new java.awt.Color(255, 255, 255));
        tfNome.setFocusable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btAplicarFiltro))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(tfCodigoCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btFolderCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btFolderCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfCodigoCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAplicarFiltro)
                            .addComponent(jButton4))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tfValorTotal.setEditable(false);
        tfValorTotal.setBackground(java.awt.Color.white);
        tfValorTotal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tfValorTotal.setText("0,00");
        tfValorTotal.setFocusable(false);

        ckBTituloPagos.setText("Exibir títulos pagos");
        ckBTituloPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckBTituloPagosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSair))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ckBTituloPagos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckBTituloPagos))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(btSair))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btSairActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        imprimirVenda();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        baixarTitulo();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btAplicarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAplicarFiltroActionPerformed

        TituloVendaDAO tvd = DAOFactory.getDefaultDAOFactory().getTituloVendaDAO();

        Date dataInicio = this.tfDataInicio.getDate();
        Date dataFim = this.tfDataFim.getDate();

        if (dataInicio == null) {
            JOptionPane.showMessageDialog(null, "Informe a data inicial!");
        } else if (dataFim == null) {
            JOptionPane.showMessageDialog(null, "Informe a data final!");
        } else {

            ckBTituloPagos.setSelected(true);
            ckBTituloPagos.setEnabled(false);

            if ((this.tfCodigoCliente.getText().equals(""))
                    || (this.tfCodigoCliente.getText().equals("00000"))) {

                titulos = tvd.listarFiltro(rbVencimento.isSelected(),
                        rbEmissao.isSelected(), dataInicio, dataFim);
            } else {

                int idCliente = Integer.parseInt(tfCodigoCliente.getText());

                titulos = tvd.listarFiltro(rbVencimento.isSelected(),
                        rbEmissao.isSelected(), dataInicio, dataFim, idCliente);
            }

            if (titulos != null) {
                somaValorExibido();
                tbConsultaTituloVenda.setModel(
                        new ConsultaTituloVendaTableModel(titulos));
                tbConsultaTituloVenda.setDefaultRenderer(
                        Object.class, new ConsultaTituloVendaCellRenderer());
            }

        }
    }//GEN-LAST:event_btAplicarFiltroActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        inicializarFrame();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tfCodigoClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfCodigoClienteFocusLost
        if (!((this.tfCodigoCliente.getText().trim()).equals(""))) {
            Cliente cliente = new Cliente();
            ClienteDAO cd = DAOFactory.getDefaultDAOFactory().getClienteDAO();
            cliente = cd.buscarPorId(Integer.parseInt(this.tfCodigoCliente.getText()));
            setCliente(cliente);
        }
    }//GEN-LAST:event_tfCodigoClienteFocusLost

    private void tfCodigoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCodigoClienteKeyTyped
        StringUtils obj = new StringUtils();
        obj.somenteNumeros(evt, this);
    }//GEN-LAST:event_tfCodigoClienteKeyTyped

    private void btFolderClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFolderClienteActionPerformed
        FrmBuscaCliente obj = new FrmBuscaCliente(this, true, this);
        obj.setVisible(true);
    }//GEN-LAST:event_btFolderClienteActionPerformed

    private void ckBTituloPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckBTituloPagosActionPerformed
        preencherTabela();
    }//GEN-LAST:event_ckBTituloPagosActionPerformed

    private void inicializarFrame() {
        tfDataInicio.setDate(null);
        tfDataFim.setDate(null);
        rbEmissao.setSelected(true);
        tfCodigoCliente.setText(null);
        tfNome.setText(null);
        ckBTituloPagos.setEnabled(true);
        ckBTituloPagos.setSelected(false);

        this.setTitle("Pitanga System - Títulos a receber");
        preencherTabela();

        GUIUtils obj = new GUIUtils();
        GUIUtils.considerarEnterComoTab(this);
        //obj.setarIcone(this);
    }

    private void preencherTabela() {
        TituloVendaDAO tituloVendaDAO = DAOFactory.getDefaultDAOFactory().getTituloVendaDAO();
        if (ckBTituloPagos.isSelected()) {
            titulos = tituloVendaDAO.listarTodos();
        } else {
            titulos = tituloVendaDAO.listarTodosAberto();
        }
        somaValorExibido();
        tbConsultaTituloVenda.setModel(
                new ConsultaTituloVendaTableModel(titulos));
    }

    private void imprimirVenda() {
        int selecionada = -1;
        selecionada = this.tbConsultaTituloVenda.getSelectedRow();
        if (selecionada == -1) {
            JOptionPane.showMessageDialog(rootPane,
                    "- NENHUM ITEM SELECIONADO -",
                    "Pitanga System - IMPRIMIR",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            RelatorioDAO obj = DAOFactory.getDefaultDAOFactory().getRelatorioDAO();
            somaValorExibido();
            ConsultaTituloVendaTableModel vtm = new ConsultaTituloVendaTableModel(titulos);
            Venda venda = vtm.get(selecionada).getVenda();
            JasperViewer jv = obj.imprimirVenda(venda);
            FrmReport frmReport = new FrmReport(this, venda.getDescricaoTela(), true, jv);
            frmReport.setVisible(true);
        }
    }

    private void somaValorExibido() {
        double valor = 0.00;
        for (TituloVenda tv : titulos) {
            valor += (tv.getValorParcela() - tv.getValorRecebido());
        }
        tfValorTotal.setText(StringUtils.formatarDecimal(valor));
    }

    private void baixarTitulo() {
        int selecionada = this.tbConsultaTituloVenda.getSelectedRow();
        if (selecionada == -1) {
            JOptionPane.showMessageDialog(rootPane,
                    "- NENHUM TÍTULO SELECIONADO -",
                    "Pitanga System - BAIXAR",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            ConsultaTituloVendaTableModel obj = new ConsultaTituloVendaTableModel(titulos);
            TituloVenda titulo = obj.get(selecionada);

            if (titulo.getValorParcela() == titulo.getValorRecebido()) {
                JOptionPane.showMessageDialog(null,
                        "Esse título já foi quitado.");
            } else {
                frmRecebimento = new FrmRecebimento(titulo, this, true);
                frmRecebimento.setVisible(true);
            }
        }
    }

    public void setCliente(Cliente cliente) {
        this.tfCodigoCliente.setText(
                StringUtils.inteiroCincoDig(cliente.getId()));
        this.tfNome.setText(cliente.getNome());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAplicarFiltro;
    private javax.swing.JButton btFolderCliente;
    private javax.swing.JButton btSair;
    private javax.swing.ButtonGroup btnGroupBuscarPor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox ckBTituloPagos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbEmissao;
    private javax.swing.JRadioButton rbVencimento;
    private javax.swing.JTable tbConsultaTituloVenda;
    private javax.swing.JTextField tfCodigoCliente;
    private com.toedter.calendar.JDateChooser tfDataFim;
    private com.toedter.calendar.JDateChooser tfDataInicio;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfValorTotal;
    // End of variables declaration//GEN-END:variables
}
