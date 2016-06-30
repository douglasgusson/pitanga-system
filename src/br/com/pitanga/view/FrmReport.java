package br.com.pitanga.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Douglas Gusson
 */
public class FrmReport extends JDialog {

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension screenSize = toolkit.getScreenSize();
    private JPanel jContentPane;
    private JasperViewer jasperViewer;
    private String title;
    private JButton btnEnviar;

    public FrmReport(JDialog owner, String title, boolean modal, JasperViewer jv) {
        super(owner, title, modal);
        this.jasperViewer = jv;
        this.title = title;
        initialize();
    }

    public FrmReport(JFrame owner, String title, boolean modal, JasperViewer jv) {
        super(owner, title, modal);
        this.jasperViewer = jv;
        this.title = title;
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setBounds(0, 0, screenSize.width, screenSize.height - 40);
        this.setResizable(false);
        this.setContentPane(getJContentPane());

        // Se teclarmos ESC nesta janela, ela irá se fechar:  
        this.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            FrmReport.this.dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        this.setTitle(title);
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {

        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridLayout());
            jContentPane.add(jasperViewer.getContentPane());
        }
        return jContentPane;

    }

    /**
     * This method initializes btnOK
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnEnviar() {
        if (btnEnviar == null) {
            btnEnviar = new JButton();
            btnEnviar.setBounds(new Rectangle(90, 120, 106, 31));
            btnEnviar.setText("Enviar e-mail");
            btnEnviar.addActionListener((java.awt.event.ActionEvent e) -> {
                FrmReport.this.dispose();
            });
        }
        return btnEnviar;
    }

}
