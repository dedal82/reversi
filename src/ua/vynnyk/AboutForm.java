/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Admin
 */
public class AboutForm extends JDialog implements ActionListener {

    public AboutForm(Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        setTitle("Про Реверсі");        
        setLayout(new MigLayout());
        add(new JLabel("Реверсі v.1a"), "wrap, align center");
        add(new JLabel("Copiright Andrii Vynnyk"), "wrap, align center");
        add(new JLabel("2013"), "wrap, align center");
        JButton button = new JButton("Ок");
        button.addActionListener(this);
        add(button, "align center");
        pack();                        
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }      
}
