package br.com.pitanga.util;

import javax.swing.JOptionPane;

/**
 * Classe para validação de CPF e CNPJ adaptada, com base na fonte:
 * http://www.vivaolinux.com.br/script/Codigo-para-validar-CPF-e-CNPJ-otimizado
 * Acesso feito em 04/11/2015
 */
public class UtilCpfCnpj {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    private static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) {
            return false;
        }

        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public static String imprimeCPF(String CPF) {
        if (isValidCPF(CPF)) {
            return (CPF.substring(0, 3) + "."
                    + CPF.substring(3, 6) + "."
                    + CPF.substring(6, 9) + "-"
                    + CPF.substring(9, 11));
        } else {
            JOptionPane.showMessageDialog(null, "CPF inválido!");
        }
        return CPF;
    }

    public static String imprimeCNPJ(String CNPJ) {
        if (isValidCNPJ(CNPJ)) {
            return (CNPJ.substring(0, 2) + "."
                    + CNPJ.substring(2, 5) + "."
                    + CNPJ.substring(5, 8) + "/"
                    + CNPJ.substring(8, 12) + "-"
                    + CNPJ.substring(12, 14));
        } else {
            JOptionPane.showMessageDialog(null, "CNPJ inválido!");
        }
        return CNPJ;
    }
}
