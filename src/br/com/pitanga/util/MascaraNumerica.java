package br.com.pitanga.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import javax.swing.JTextField;

/**
 *
 * @author Douglas Gusson
 */
public class MascaraNumerica implements KeyListener {

    JTextField jTextField;
    DecimalFormat mascara;
    int inteiro, decimal;

    public MascaraNumerica(JTextField jt, int tam, int dec) {
        jTextField = jt;
        inteiro = tam - dec;
        decimal = dec;

        String mask = "";

        if ((tam - dec) > 0) {
            for (int i = 0; i < dec; i++) {
                mask += "0";
            }
            if (mask.length() > 0) {
                mask = "." + mask;
            }
//            for (int i = 0; i < tam - dec; i++) {
//                mask = "#" + mask;
//                if (((i + 1) % 3) == 0 && (i + 1) < (tam - dec)) {
//                    mask = "," + mask;
//                }
//            }
        }
        mascara = new DecimalFormat(mask);
    }

    @Override
    public void keyTyped(KeyEvent e) {

        char c = e.getKeyChar();

        String textNum = jTextField.getText().replace(".", "").replace(",", "");

        if (!Character.isDigit(c) || textNum.length() >= (inteiro + decimal)) {
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        String textoAntes = jTextField.getText();

        if (textoAntes.replace(".", "").replace(",", "").length() <= decimal) {
            if (inteiro >= decimal) {
                jTextField.setText(textoAntes.replace(".", "").replace(",", ""));
            }
        } else if (textoAntes.replace(".", "").replace(",", "").length() > decimal) {

            Double num = 0.0;

            String textNum = textoAntes.replace(".", "").replace(",", "");

            if (decimal > 0) {
                textNum = textNum.substring(0, textNum.length() - decimal) + "."
                        + textNum.substring(textNum.length() - decimal, textNum.length());
            } else {
                textNum = textNum + ".0";
            }

            try {
                num = Double.parseDouble(textNum);
            } catch (Exception exp) {
            }

            String fim = mascara.format(num);
            if (fim.substring(0, 1).equals(",")) {
                fim = "0" + fim;
            }
            jTextField.setText(fim);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

}
