/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Admin
 */
public class CountBoard extends JPanel {
    
    private JLabel firstCount;
    private JLabel secondCount;
    private JLabel separator;
    
    public CountBoard() {
        initComponents();
    } 
    
    public CountBoard(Color firstColor, Color secondColor) {        
        initComponents();
        this.firstCount.setForeground(firstColor);
        this.secondCount.setForeground(secondColor);
    }
    
    public void setCount(int firstCount, int secondCount) {
        this.firstCount.setText(Integer.toString(firstCount));
        this.secondCount.setText(Integer.toString(secondCount));
    }
    
    public void setFirstCount(int firstCount) {
        this.firstCount.setText(Integer.toString(firstCount));
    }

    public void setSecondCount(int secondCount) {
        this.secondCount.setText(Integer.toString(secondCount));
    }

    public void setSeparator(String separator) {
        this.separator.setText(separator);
    }

    public void setFirstColor(Color color) {
        firstCount.setForeground(color);
    }
    
    public void setSecondColor(Color color) {
        secondCount.setForeground(color);
    }
    
    private void initComponents() {
        setPreferredSize(new Dimension(150, 0));
        setLayout(new MigLayout("","[grow][][grow]",""));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        
        firstCount = new JLabel("0", JLabel.RIGHT);
        secondCount = new JLabel("0", JLabel.LEFT);
        separator = new JLabel(":", JLabel.CENTER);
        
        final Font font = new Font(firstCount.getFont().getName(), Font.BOLD, 45);
        
        firstCount.setFont(font);
        secondCount.setFont(font);
        separator.setFont(font); 
        
        add(firstCount, "grow");
        add(separator);
        add(secondCount, "grow");
    }
    
    
}
