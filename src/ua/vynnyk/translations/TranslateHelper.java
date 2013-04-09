/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.translations;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author vynnyk
 */
public class TranslateHelper {
    private static final String FILE_NAME = "ua.vynnyk.translations.translate";
    
    private static ResourceBundle resources = ResourceBundle.getBundle(FILE_NAME);
    
    public static String getString(String resource) {
        try { 
            return resources.getString(resource);
        } catch (Exception e) {
            return "No translation";
        }            
    }
    
    public static void setResources(Locale l) {
        resources = ResourceBundle.getBundle(FILE_NAME, l);
    }
    
    //not implemented yet. get list localization file in package
    public static String[] getLocalizations() {
        return null;
    }
    
    //to avoid create instance
    private TranslateHelper() {
    }        
}
