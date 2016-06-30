package br.com.pitanga.util;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class GUIUtils {

//    public void considerarEnterComoTab(Component comp) {
//
//        Set<AWTKeyStroke> newKeystrokes;
//
//        newKeystrokes = new HashSet<>(
//                comp.getFocusTraversalKeys(
//                        KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
//
//        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
//
//        comp.setFocusTraversalKeys(
//                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeystrokes);
//
//        newKeystrokes = new HashSet<>(
//                comp.getFocusTraversalKeys(
//                        KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
//
//        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER,
//                InputEvent.SHIFT_DOWN_MASK));
//
//        comp.setFocusTraversalKeys(
//                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newKeystrokes);
//    }
    /**
     * Este método faz com que o ENTER seja considerado como TAB, em componentes
     * como um JTextField. Além disso, permite a navegação com as setas para
     * esquerda e para direita.
     *
     * @param comp O componente.
     */
    public static void considerarEnterComoTab(Component comp) {
        Set<AWTKeyStroke> newKeystrokes;
        newKeystrokes = new HashSet<AWTKeyStroke>(
                comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_RIGHT, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_RIGHT, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                newKeystrokes);
        newKeystrokes = new HashSet<AWTKeyStroke>(
                comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER,
                InputEvent.SHIFT_DOWN_MASK));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_LEFT, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_LEFT, 0));
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
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_RIGHT, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_RIGHT, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                newKeystrokes);
        newKeystrokes = new HashSet<AWTKeyStroke>(
                comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_LEFT, 0));
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_LEFT, 0));
        comp.setFocusTraversalKeys(
                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newKeystrokes);
    }

    public void setarIcone(Frame frame) {
        frame.setIconImage(
                new ImageIcon(
                        getClass().getResource(
                                "/br/com/pitanga/img/icon.png")).getImage()
        );
    }

    /**
     * Confirma a autenticação do usuário ditador, para configuração do sistema.
     *
     * @return boolean - retorna true se a senha estiver correta.
     */
    public static boolean authDitador() {

        boolean result = false;

        JPasswordField tfSenha = new JPasswordField(10);
        tfSenha.setEchoChar('*');

        JLabel lbRotulo = new JLabel("Entre com a senha:");

        JPanel painel = new JPanel();
        painel.add(lbRotulo);
        painel.add(tfSenha);

        JOptionPane.showMessageDialog(
                null, painel, "Acesso restrito", JOptionPane.WARNING_MESSAGE);

        char[] senhaDigitada = tfSenha.getPassword();
        char[] senha = {'d', 'i', 't', 'a', 'd', 'o', 'r'};

        if (Arrays.equals(senhaDigitada, senha)) {
            result = true;
        } else {
            JOptionPane.showMessageDialog(null, "Senha inválida!");
        }
        return result;

    }

}
