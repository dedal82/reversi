/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.components;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;

/**
 *
 * @author Admin
 */
public class ColorChooser extends JColorChooser{

//    public ColorChooser(Color initialColor) {
//        super(initialColor);
//        setPreviewPanel(new JPanel());
//    }

    public ColorChooser() {
        createPreviewPanel();
    }

//    public ColorChooser(ColorSelectionModel model) {
//        super(model);
//        setPreviewPanel(new JPanel());
//    }
    
    private void createPreviewPanel() {
        final JPanel previewPanel = new JPanel();
        final JLabel colorLabel = new JLabel();
        colorLabel.setOpaque(true);
        colorLabel.setBackground(getColor());
        colorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        previewPanel.add(colorLabel);
        setPreviewPanel(previewPanel);
    }    
}
