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
public class FrmClienteNew extends javax.swing.JDialog {

    private List<Cliente> clientes;

    public FrmClienteNew(Window parent, boolean modal) {
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
                //preencherCamposCliente(c);
            }
        });

        ActionListener sair = (ActionEvent e) -> {
            FrmClienteNew.this.dispose();
        };

        btSair.addActionListener(sair);

        ActionListener cancela = (ActionEvent e) -> {
            initialize();
        };


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

    private void initialize() {
        // Preenche a tabela de clientes
        preencherTabela();
        // Habilita a tabela de clientes
        this.tbCliente.setEnabled(true);
        // Habilita a seleção na tabela
        this.tbCliente.setRowSelectionAllowed(true);
        // Habilita/desabilita os botões
        this.btSair.setEnabled(true);
        this.btNovo.setEnabled(true);
        this.btCancelar.setEnabled(false);
        this.btGravar.setEnabled(false);
        this.setTitle("Pitanga System - Clientes");
        GUIUtils.considerarEnterComoTab(this);

        // Se teclarmos ESC nesta janela, ela irá se fechar:  
        this.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            FrmClienteNew.this.dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
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
        jToolBar1 = new javax.swing.JToolBar();
        btNovo = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btGravar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCliente = new javax.swing.JTable();
        btSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clientes");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/new_20x20.png"))); // NOI18N
        btNovo.setText("Novo");
        btNovo.setToolTipText("Novo cadastro de cliente");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });
        jToolBar1.add(btNovo);

        btCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/cancel_20x20.png"))); // NOI18N
        btCancelar.setText("Cancelar");
        btCancelar.setToolTipText("Cancelar cadastro");
        jToolBar1.add(btCancelar);

        btGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/save_20x20.png"))); // NOI18N
        btGravar.setText("Gravar");
        btGravar.setToolTipText("Gravar o cadastro do cliente");
        jToolBar1.add(btGravar);

        tbCliente.setAutoscrolls(false);
        tbCliente.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbCliente);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );

        btSair.setText("Sair");
        btSair.setToolTipText("Sair");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 713, Short.MAX_VALUE)
                        .addComponent(btSair)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(167, 167, 167)
                .addComponent(btSair)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        FrmCliente cliente = new FrmCliente(this, true);
        cliente.setVisible(true);
    }//GEN-LAST:event_btNovoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btGravar;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSair;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tbCliente;
    // End of variables declaration//GEN-END:variables
}
