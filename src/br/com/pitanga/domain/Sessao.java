package br.com.pitanga.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author douglas
 */
public class Sessao {

    private static Usuario usuario;
    private static Empresa empresa;
    private static Calendar acesso;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario aUsuario) {
        usuario = aUsuario;
    }

    public static Empresa getEmpresa() {
        return empresa;
    }

    public static void setEmpresa(Empresa aEmpresa) {
        empresa = aEmpresa;
    }

    public static Calendar getAcesso() {
        return acesso;
    }

    public static void setAcesso(Calendar aAcesso) {
        acesso = aAcesso;
    }

    public static String acessoToString() {
        Date date = getAcesso().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String data = format1.format(date);
        return data;
    }

}
