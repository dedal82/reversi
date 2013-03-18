/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class ColorChooser extends JColorChooser {

//    public ColorChooser(Color initialColor) {
//        super(initialColor);
//        setPreviewPanel(new JPanel());
//    }

    public ColorChooser(Color initial) {
        super(initial);
        createPreviewPanel();
    }

//    public ColorChooser(ColorSelectionModel model) {
//        super(model);
//        setPreviewPanel(new JPanel());
//    }
    
    private void createPreviewPanel() {
        final JPanel previewPanel = new JPanel(new GridLayout(1, 2));
        final JLabel oldColorLabel = createColorLabel(getColor());        
        final JLabel colorLabel = createColorLabel(getColor());
        previewPanel.add(oldColorLabel);
        previewPanel.add(colorLabel);
        setPreviewPanel(previewPanel);
    }  
    
    public static Color showDialog(Component component, String title, Color initial) {
        ColorChooser choose = new ColorChooser(initial);  
        JDialog dialog = createDialog(component, title, true, choose, null, null);
        dialog.getContentPane().add(choose);
        dialog.pack();
        dialog.setVisible(true);
        return choose.getColor();
    }
    
    private JLabel createColorLabel(Color color) {
        final JLabel colorLabel = new JLabel();
        colorLabel.setOpaque(true);
        colorLabel.setBackground(color);
        colorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return colorLabel;
    }
}
