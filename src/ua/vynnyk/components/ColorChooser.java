/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Admin
 */
public class ColorChooser extends JColorChooser {

    public ColorChooser() {
        createPreviewPanel();
    }

    public ColorChooser(Color initialColor) {
        super(initialColor);
        createPreviewPanel();
    }

    public ColorChooser(ColorSelectionModel model) {
        super(model);
        createPreviewPanel();
    }   
    
    private void createPreviewPanel() {
        final JPanel previewPanel = new JPanel(new GridLayout(1, 2));
        final JLabel oldColorLabel = createColorLabel(getColor());        
        final JLabel colorLabel = createColorLabel(getColor());
        previewPanel.setPreferredSize(new Dimension(200, 100));
        previewPanel.add(oldColorLabel);
        previewPanel.add(colorLabel);
        setPreviewPanel(previewPanel);
        
        getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color newColor = getColor();
                colorLabel.setBackground(newColor);
            }
        });
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
        //colorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return colorLabel;
    }
}
