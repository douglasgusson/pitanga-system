package br.com.pitanga.util;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Douglas Gusson
 */
public class StringUtils {

    public static String lpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = filler + valueToPad;
        }
        return valueToPad;
    }

    public static String rpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = valueToPad + filler;
        }
        return valueToPad;
    }

    public void somenteNumeros(KeyEvent evt, Component comp) {
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }

    public String formatarDecimalTabela(double valor) {
        DecimalFormat df = new DecimalFormat("##0.00");
        String saida = df.format(valor);
        return saida;
    }

    public String formatarBigDecimal(BigDecimal valor) {
        DecimalFormat df = new DecimalFormat("##0.00");
        String saida = df.format(valor);
        return saida;
    }

    public static String formataData(Date data) {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formatador.format(data);
        return dataFormatada;
    }

    public static String formatarDecimal(double valor) {
        DecimalFormat df = new DecimalFormat("##0.00");
        String saida = df.format(valor);
        return saida;
    }

    public static String inteiroCincoDig(int numero) {
        String result = String.format("%05d", numero);
        return result;
    }

    public static String inteiroCincoDig(Long numero) {
        String result = String.format("%05d", numero);
        return result;
    }

    public static String inteiroOitoDig(int numero) {
        String result = String.format("%08d", numero);
        return result;
    }

    public static String inteiroOitoDig(Long numero) {
        String result = String.format("%08d", numero);
        return result;
    }

    public static String inteiroDoisDig(int numero) {
        String result = String.format("%02d", numero);
        return result;
    }

}
