package br.com.pitanga.util;

import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Douglas Gusson
 */
public class MaskUtil {

    public MaskFormatter maskData(JFormattedTextField textfield) throws ParseException {
        MaskFormatter mask = null;
        mask = new MaskFormatter("##/##/####");
        mask.setOverwriteMode(true);
        mask.setValidCharacters("0123456789");
        mask.setPlaceholderCharacter('_');
        mask.install(textfield);
        return mask;
    }

    public MaskFormatter maskCpf(JFormattedTextField textfield) throws ParseException {
        MaskFormatter mask = null;
        mask = new MaskFormatter("###.###.###-##");
        mask.setOverwriteMode(true);
        mask.setValidCharacters("0123456789");
        mask.setPlaceholderCharacter('_');
        mask.install(textfield);
        return mask;
    }

    public MaskFormatter maskCnpj(JFormattedTextField textfield) throws ParseException {
        MaskFormatter mask = null;
        mask = new MaskFormatter("##.###.###/####-##");
        mask.setOverwriteMode(true);
        mask.setValidCharacters("0123456789");
        mask.setPlaceholderCharacter('_');
        mask.install(textfield);
        return mask;
    }

    public MaskFormatter maskCelular(JFormattedTextField textfield) throws ParseException {
        MaskFormatter mask = null;
        mask = new MaskFormatter("(##) #####-####");
        mask.setOverwriteMode(true);
        mask.setValidCharacters("0123456789");
        mask.setPlaceholderCharacter('_');
        mask.install(textfield);
        return mask;
    }

    public MaskFormatter maskTelFixo(JFormattedTextField textfield) throws ParseException {
        MaskFormatter mask = null;
        mask = new MaskFormatter("(##) ####-####");
        mask.setOverwriteMode(true);
        mask.setValidCharacters("0123456789");
        mask.setPlaceholderCharacter('_');
        mask.install(textfield);
        return mask;
    }

    public MaskFormatter maskCep(JFormattedTextField textfield) throws ParseException {
        MaskFormatter mask = null;
        mask = new MaskFormatter("#####-###");
        mask.setOverwriteMode(true);
        mask.setValidCharacters("0123456789");
        mask.setPlaceholderCharacter('_');
        mask.install(textfield);
        return mask;
    }
  
}
