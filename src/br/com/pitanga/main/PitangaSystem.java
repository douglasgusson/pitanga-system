package br.com.pitanga.main;

import br.com.pitanga.view.FrmLogin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Douglas Gusson
 */
public class PitangaSystem extends JWindow {

    private final int duration;

    public PitangaSystem(int d) {
        duration = d;
    }

    public void showSplash() {
        desenharSplash();
        try {
            Thread.sleep(duration);
            java.awt.EventQueue.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new FrmLogin().setVisible(true);
                } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(PitangaSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            setVisible(false);
        } catch (Exception e) {
        }
    }

    private void desenharSplash() {

        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        int comprimento = 400;
        int altura = 220;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - comprimento) / 2;
        int y = (screen.height - altura) / 2;
        setBounds(x, y, comprimento, altura);

        ImageIcon imageIcon = new ImageIcon(
                PitangaSystem.this.getClass().getResource("/br/com/pitanga/img/load.gif"));
        JLabel lbGif = new JLabel(imageIcon);

        JLabel lbTitulo = new JLabel("Pitanga System", JLabel.CENTER);
        lbTitulo.setFont(new Font("Sans-Serif", Font.BOLD, 14));

        JLabel lbCopyrt = new JLabel("Copyright 2016, Douglas Gusson", JLabel.CENTER);
        lbCopyrt.setFont(new Font("Sans-Serif", Font.BOLD, 10));

        content.add(lbTitulo, BorderLayout.NORTH);
        content.add(lbGif, BorderLayout.CENTER);
        content.add(lbCopyrt, BorderLayout.SOUTH);

        Color borda = new Color(201, 201, 201);
        content.setBorder(BorderFactory.createLineBorder(borda, 12));

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PitangaSystem pitanga = new PitangaSystem(4000);
        pitanga.showSplash();
    }

}
