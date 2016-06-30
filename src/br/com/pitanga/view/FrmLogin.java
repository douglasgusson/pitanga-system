package br.com.pitanga.view;

import br.com.pitanga.dao.model.UsuarioDAO;
import br.com.pitanga.domain.Usuario;
import br.com.pitanga.util.GUIUtils;
import br.com.pitanga.dao.DAOFactory;
import br.com.pitanga.domain.Sessao;
import br.com.pitanga.util.Seguranca;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Douglas Gusson
 */
public class FrmLogin extends javax.swing.JDialog {

    private boolean logout;

    /**
     * Creates new form FrmLogin
     */
    public FrmLogin() {
        initComponents();
        initialize();
    }

    public FrmLogin(Frame owner) {
        super(owner, DEFAULT_MODALITY_TYPE);
        initComponents();
        initialize();
        logout = true;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSaida();
            }
        });

        setDefaultCloseOperation(FrmPrincipal.DO_NOTHING_ON_CLOSE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfSenha = new javax.swing.JPasswordField();
        btEntrar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        lbStatus = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Usuário:");

        tfUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfUsuarioFocusGained(evt);
            }
        });

        jLabel2.setText("Senha:");

        tfSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfSenhaFocusGained(evt);
            }
        });

        btEntrar.setText("Entrar");
        btEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEntrarActionPerformed(evt);
            }
        });

        btSair.setText("Sair");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        lbStatus.setText("jLabel4");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pitanga/img/login_80x80.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btEntrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSair))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(tfUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(tfSenha))))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSair)
                    .addComponent(btEntrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEntrarActionPerformed

        this.lbStatus.setText("Checando dados...");

        String senha = Seguranca.criptografarSHA256(this.tfSenha.getText());

        Usuario user = new Usuario();
        user.setNome(this.tfUsuario.getText());
        user.setSenha(senha);

        if (!autenticar(user)) {
            JOptionPane.showMessageDialog(null,
                    "Usuário não encontrado ou inativo.");
            this.lbStatus.setText("");
        } else {
            this.dispose();
            FrmPrincipal frmPrincipal = new FrmPrincipal();
            frmPrincipal.setVisible(true);
        }
    }//GEN-LAST:event_btEntrarActionPerformed

    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        if (logout) {
            confirmarSaida();
        } else {
            System.exit(0);
        }
    }//GEN-LAST:event_btSairActionPerformed

    private void tfUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUsuarioFocusGained
        tfUsuario.selectAll();
    }//GEN-LAST:event_tfUsuarioFocusGained

    private void tfSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSenhaFocusGained
        tfSenha.selectAll();
    }//GEN-LAST:event_tfSenhaFocusGained

    private void initialize() {
        this.setTitle("Pitanga System - Login");
        GUIUtils.considerarSetaComoTab(btEntrar);
        GUIUtils.considerarSetaComoTab(btSair);
        GUIUtils.considerarEnterComoTab(tfUsuario);
        GUIUtils.considerarEnterComoTab(tfSenha);

        this.lbStatus.setText("");

        getRootPane().setDefaultButton(btEntrar);

        // Se teclarmos ESC nesta janela, ela irá se fechar:  
        this.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            FrmLogin.this.dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public boolean autenticar(Usuario usuario) {

        System.out.println("### AUTENTICAÇÃO ###"
                + "\nUSUÁRIO: " + usuario.getNome()
                + "\nSENHA: " + usuario.getSenha());

        UsuarioDAO usuarioDAO = DAOFactory.getDefaultDAOFactory().getUsuarioDAO();
        List<Usuario> lista = usuarioDAO.listarTodos();

        for (Usuario u : lista) {
            if (u.getNome().equals(usuario.getNome())
                    && u.getSenha().equals(usuario.getSenha())
                    && u.isAtivo()) {
                Sessao.setUsuario(u);
                Sessao.setAcesso(new GregorianCalendar());
                usuarioDAO.alterar(u);
                return true;
            }
        }

        return false;
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEntrar;
    private javax.swing.JButton btSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JPasswordField tfSenha;
    private javax.swing.JTextField tfUsuario;
    // End of variables declaration//GEN-END:variables
}
