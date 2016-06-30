package br.com.pitanga.util;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class EnterComoTab extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private JLabel lblTexto1 = null;

    private JLabel lblTexto2 = null;

    private JTextField txtNome = null;

    private JTextField txtEndereco = null;

    private JButton btnOK = null;

    /**
     * This is the default constructor
     */
    public EnterComoTab() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(300, 200);
        this.setContentPane(getJContentPane());
        this.getRootPane().setDefaultButton(btnOK); // Se teclarmos OK neste  
        // botão...  
        // Se teclarmos ESC nesta janela, ela irá se fechar:  
        this.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            EnterComoTab.this.dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        this.setTitle("JFrame");
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            lblTexto2 = new JLabel();
            lblTexto2.setBounds(new Rectangle(15, 60, 59, 14));
            lblTexto2.setText("Endereço");
            lblTexto1 = new JLabel();
            lblTexto1.setBounds(new Rectangle(15, 15, 63, 14));
            lblTexto1.setText("Nome");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(lblTexto1, null);
            jContentPane.add(lblTexto2, null);
            jContentPane.add(getTxtNome(), null);
            jContentPane.add(getTxtEndereco(), null);
            jContentPane.add(getBtnOK(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes txtNome
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTxtNome() {
        if (txtNome == null) {
            txtNome = new JTextField();
            txtNome.setBounds(new Rectangle(90, 15, 181, 31));
            considerarEnterComoTab(txtNome); // ####  
        }
        return txtNome;
    }

    /**
     * This method initializes txtEndereco
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTxtEndereco() {
        if (txtEndereco == null) {
            txtEndereco = new JTextField();
            txtEndereco.setBounds(new Rectangle(90, 60, 181, 31));
            considerarEnterComoTab(txtEndereco); // ####  
        }
        return txtEndereco;
    }

    /**
     * This method initializes btnOK
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnOK() {
        if (btnOK == null) {
            btnOK = new JButton();
            btnOK.setBounds(new Rectangle(90, 120, 106, 31));
            btnOK.setText("OK");
            considerarSetaComoTab(btnOK);
            btnOK.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    EnterComoTab.this.dispose();
                }
            });
        }
        return btnOK;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub  
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                EnterComoTab thisClass = new EnterComoTab();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    /**
     * Este método faz com que o ENTER seja considerado como TAB, em componentes
     * como um JTextField. Além disso, permite a navegação com as setas para
     * cima e para baixo.
     *
     * @param comp O componente.
     */
    public static void considerarEnterComoTab(Component comp) {
        Set<AWTKeyStroke> newKeystrokes;
        newKeystrokes = new HashSet<AWTKeyStroke>(
                comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_DOWN, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                newKeystrokes);
        newKeystrokes = new HashSet<AWTKeyStroke>(
                comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER,
                InputEvent.SHIFT_DOWN_MASK));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_UP, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_UP, 0));
        comp.setFocusTraversalKeys(
                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newKeystrokes);
    }

    /**
     * Este método faz com que as setas sejam consideradas como TAB, e deve ser
     * usado em um botão qualquer.
     *
     * @param comp O componente.
     */
    public static void considerarSetaComoTab(JButton comp) {
        Set<AWTKeyStroke> newKeystrokes;
        newKeystrokes = new HashSet<AWTKeyStroke>(
                comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_DOWN, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                newKeystrokes);
        newKeystrokes = new HashSet<AWTKeyStroke>(
                comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_UP, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_UP, 0));
        comp.setFocusTraversalKeys(
                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newKeystrokes);
    }
}
