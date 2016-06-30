
package br.com.pitanga.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Douglas Gusson
 */
public class ImageFilter extends FileFilter {
 
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String extension = ExtensionUtils.getExtension(f);
        if (extension != null) {
            if (extension.equals(ExtensionUtils.TIFF) ||
                extension.equals(ExtensionUtils.TIF) ||
                extension.equals(ExtensionUtils.GIF) ||
                extension.equals(ExtensionUtils.JPEG) ||
                extension.equals(ExtensionUtils.JPG) ||
                extension.equals(ExtensionUtils.PNG)) {
                    return true;
            } else {
                return false;
            }
        }
 
        return false;
    }

    @Override
    public String getDescription() {
        return "Somente imagens";
    }
}
