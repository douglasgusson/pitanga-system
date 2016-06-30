package br.com.pitanga.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FormDecimal extends PlainDocument {

    private static final long serialVersionUID = 1L;

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        String texto = getText(0, getLength());
        if (texto.length() < 9) {
            if (texto.length() > 0) {
                if (texto.substring(0, 1).equals("0")) {
                    texto = texto.substring(1, texto.length());
                }
            }

            remove(0, getLength());
            StringBuilder strBuild = new StringBuilder(texto.replaceAll(",", "") + str);
            if (strBuild.length() < 3) {
                strBuild.insert(0, "0,");
            } else {
                strBuild.insert(strBuild.length() - 2, ",");
            }
            super.insertString(0, strBuild.toString(), a);
        }
    }

}
