
package br.com.pitanga.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Douglas Gusson
 */
public class NumeroDocument extends PlainDocument {

    private int iMaxLength;
    private int iQtdDec;
    private String sMil;
    private String sDec;

    // Construtor para indicando apenas o tamanho máximo (incluindo o  ponto decimal), será assumido como padrao 2 decimais  
    public NumeroDocument(int maxLen) {
        this(maxLen, 2, '.', ',');
    }

    // Construtor para indicando apenas o tamanho máximo (incluindo o  ponto decimal) e a quantidade de decimais  
    public NumeroDocument(int maxLen, int qtdDec) {
        this(maxLen, qtdDec, '.', ',');
    }

    // Construtor para indicando apenas o tamanho máximo (incluindo o  ponto decimal), a quantidade de decimais, o separador de milhares e dos decimais  
    public NumeroDocument(int maxLen, int qtdDec, char mil, char dec) {
        super();

        iMaxLength = 0;
        if (maxLen > 0) {
            iMaxLength = maxLen;
        }

        iQtdDec = 0;
        if (qtdDec > 0) {
            iQtdDec = qtdDec;
        }

        sMil = Character.toString(mil);
        sDec = Character.toString(dec);
    }

    @Override
    public void insertString(int offs, String str, AttributeSet attrSet) throws BadLocationException {

        String texto = getText(0, getLength());
        String sinal = "";

        // Verificar se eh um numero negativo  
        if (texto.indexOf("-") >= 0) {
            sinal = "-";
            texto = texto.replace("-", "");
        }

        // Remover os zeros a esquerda para nao gerar uma validacao incorreta  
        while ((texto.length() > 0)
                && ((texto.charAt(0) == '0') || (texto.charAt(0) == sDec.charAt(0)))) {
            texto = texto.substring(1);
        }

        // verificar se o que esta sendo digitado ou colado é um valor válido      
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((!Character.isDigit(c))
                    && (!sMil.equals(Character.toString(c)))
                    && (!(sDec.equals(Character.toString(c)) && (iQtdDec > 0)))) {
                // o sinal de negativo deve ser sempre o primeiro a ser informado  
                if (!((texto.length() == 0)
                        && (i == 0)
                        && ("-".equals(Character.toString(c))))) {
                    return;
                }
            }
        }

        texto = texto.replace(sMil, ""); // retirar os simbolos do milhar, para nao interferir no tamanho do campo  

        if ((texto.length() < this.iMaxLength)
                || (this.iMaxLength <= 0)) {
            texto = texto.replace(sDec, ""); // retirar o simbolo do decimal  

            // Verificar se eh um numero negativo  
            if (str.indexOf("-") >= 0) {
                sinal = "-";
                str = str.replace("-", "");
            }

            // Concatenar o texto existente no campo e o que esta sendo digitado ou colado  
            StringBuffer s = new StringBuffer(texto + str.replace(sDec, "").replace(sMil, ""));

            // Remover os zeros a esquerda para nao gerar uma formatacao incorreta  
            while ((s.length() > 0)
                    && (s.charAt(0) == '0')) {
                s.deleteCharAt(0);
            }

            // Verificar o tamanho do conteúdo  (texto antigo + novo + separador decimal)    
            if (((s.length() + (iQtdDec == 0 ? 0 : 1)) > this.iMaxLength)
                    && (this.iMaxLength != 0)) {
                return;
            }

            // mostar zeros a esquerda quando o valor for menor que "1"  
            for (int i = s.length(); i <= iQtdDec; i++) {
                s.insert(0, "0");
            }

            // Adicionar o separador da parte decimal  
            if (iQtdDec > 0) {
                s.insert(s.length() - iQtdDec, sDec);
            }

            // Adicionar os simbolos de "milhar"  
            int iPos = iQtdDec;
            int nQtdDec = iQtdDec;
            if (nQtdDec > 0) {
                iPos++;
                nQtdDec++; // tratar o separador do decimal  
            }
            int iQtdMil = 0;
            while (iPos < s.length() - 1) {
                iPos++;

                if ((((iPos - nQtdDec) % 3) == 0)
                        && (iPos < s.length() - iQtdMil)) {
                    s.insert((s.length() - iPos - iQtdMil), sMil);
                    iQtdMil++;
                }
            }

            // Caso seja um numero negativo, adicionar o sinal  
            if (sinal.equals("-")) {
                s.insert(0, "-");
            }

            // Limpar a informacao atual    
            super.remove(0, getLength());
            // retorna o valor formatado    
            super.insertString(0, s.toString(), attrSet);
        }
    }

    @Override
    public void remove(int offset, int length) throws BadLocationException {
        double dValue;
        super.remove(offset, length);
        String texto = getText(0, getLength());
        texto = texto.replace(sMil, "");
        texto = texto.replace(sDec, "");
        try {
            dValue = Double.parseDouble(texto);
            if (dValue == 0) {
                texto = "";
            }
        } catch (Exception e) {
            // nao faz nada    
        }
        super.remove(0, getLength());
//        insertString(0, texto, null);
    }
}
