package br.com.pitanga.dao;

import javax.swing.JOptionPane;

/**
 *
 * @author Douglas Gusson
 */
public class DAOException extends RuntimeException {

    /**
     * Construtor default
     */
    public DAOException() {
    }

    /**
     * Construtor
     *
     * @param message
     */
    public DAOException(String message) {
        super(message);
        alert(message);
    }

    private void alert(String message) {
        JOptionPane.showMessageDialog(null, message,
                "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Construtor
     *
     * @param cause
     */
    public DAOException(Throwable cause) {
        super(cause);
        alert(cause);
    }

    private void alert(Throwable cause) {
        JOptionPane.showMessageDialog(null, cause,
                "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Construtor
     *
     * @param message
     * @param cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
        alert(message, cause);
    }

    private void alert(String message, Throwable cause) {
        JOptionPane.showMessageDialog(null,
                "\n" + message
                + "\n\nDETALHES:\n" + cause,
                "Erro", JOptionPane.ERROR_MESSAGE);
    }

}
