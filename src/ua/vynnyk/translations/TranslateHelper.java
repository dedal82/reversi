/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.translations;

import java.nio.file.Files;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author vynnyk
 */
public class TranslateHelper {
    private static final String FILE_NAME = "ua.vynnyk.translations.translate";
    private static Locale [] locales = {Locale.US, 
                                        new Locale("uk_UA"), 
                                        new Locale("ru_RU")};  
    
    private static Locale currentLocale = Locale.US;    
    private static ResourceBundle resources = ResourceBundle.getBundle(FILE_NAME, currentLocale);
    
    public static String getString(String resource) {
        try { 
            return resources.getString(resource);
        } catch (Exception e) {
            return "No translation";
        }            
    }
    
    public static void setResources(Locale l) {        
        resources = ResourceBundle.getBundle(FILE_NAME, l);
        currentLocale = l;
    }
    
    //not implemented yet. get list localization file in package
    public static Locale[] getLocalizations() {
        return locales;
    }
    
    public static Locale getLocale() {
        return currentLocale;
    }
    
    //to avoid create instance
    private TranslateHelper() {
    }   
}
