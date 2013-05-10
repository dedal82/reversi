/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.l10n;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.UIManager;

/**
 *
 * @author vynnyk
 */
public class TranslateHelper {
    private static final String FILE_NAME = "ua.vynnyk.l10n.translate";
    private static final Locale [] locales = {Locale.US, 
                                        new Locale("uk", "UA"), 
                                        new Locale("ru", "RU")};  
    
    private static Locale currentLocale = Locale.US;    
    private static ResourceBundle resources;
    
    static {
        setResources(currentLocale);
    }
    
    public static String getString(String resource) {
        try { 
            return resources.getString(resource);
        } catch (Exception e) {
            return "No translation";
        }            
    }
    
    public static void setResources(Locale locale) {                 
        resources = ResourceBundle.getBundle(FILE_NAME, locale);
        currentLocale = locale;
        
        UIManager.put("OptionPane.cancelButtonText", getString("optionpain.cancel"));
        UIManager.put("OptionPane.noButtonText", getString("optionpain.no"));
        UIManager.put("OptionPane.okButtonText", getString("optionpain.ok"));
        UIManager.put("OptionPane.yesButtonText", getString("optionpain.yes"));
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
