package br.com.pitanga.util;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author Douglas Gusson
 */
public class ExtensionUtils {

    //Extensões de imagens 
    public final static String JPEG = "jpeg";
    public final static String JPG = "jpg";
    public final static String GIF = "gif";
    public final static String TIFF = "tiff";
    public final static String TIF = "tif";
    public final static String PNG = "png";

    /**
     * Pega a extensão de um arquivo.
     * @param file
     * @return A extensão do arquivo.
     */
    public static String getExtension(File file) {
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /**
     * Cria um ImageIcon a partir do caminho passado.
     * @param path
     * @return Retorna um ImageIcon, ou null se o caminho for inválido.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ExtensionUtils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Não foi possível localizar o arquivo: " + path);
            return null;
        }
    }
}
