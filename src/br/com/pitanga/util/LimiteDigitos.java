/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Douglas Gusson
 */
public class LimiteDigitos extends PlainDocument {

    private int quantMax;

    public LimiteDigitos(int maxLen) {
        super();
        if (maxLen <= 0) {
            throw new IllegalArgumentException("Especifique a quantidade!");
        }
        quantMax = maxLen;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null || getLength() == quantMax) {
            return;
        }
        int totalQuantia = (getLength() + str.length());
        if (totalQuantia <= quantMax) {
            super.insertString(offset, str, attr);
            return;
        }
        String nova = str.substring(0, getLength() - quantMax);
        super.insertString(offset, nova, attr);
    }
}
