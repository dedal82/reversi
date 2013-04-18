/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Options {
    private static final String FILE_NAME = "./conf/init.xml";
    private static Properties p = new Properties();
    
    static {
        try (InputStream in = new FileInputStream(FILE_NAME)) {        
            p.loadFromXML(in);
        } catch (InvalidPropertiesFormatException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, e);
        }                                        
    }
    
    public static void setOption(String key, String value) {
        p.setProperty(key, value);
    }
    
    public static String getOption(String key) {
        return p.getProperty(key);
    }
    
    public static void save() {        
        try (OutputStream out = new FileOutputStream(FILE_NAME)) {
            p.storeToXML(out, null);
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
