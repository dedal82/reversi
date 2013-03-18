/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.miginfocom.swing.MigLayout;
import ua.vynnyk.components.ColorChooser;
import ua.vynnyk.game.EnumPlayer;

/**
 *
 * @author Admin
 */
public class OptionsForm extends JDialog {
    
    private GameForm gameForm;
    
    private JLabel boardColor;
    private JLabel lineColor;
    private JLabel firstColor;
    private JLabel secondColor;

    public OptionsForm(GameForm gameForm, boolean modal) {
        super(gameForm, modal);        
        this.gameForm = gameForm;
        
        initComponents();
    }    

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        setTitle("Налаштування");  
        setPreferredSize(new Dimension(400, 400));
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        
        //Графіка        
        JPanel colorPanel = new JPanel(new MigLayout("fillx, wrap 2", "[align right]10[max]", ""));
        colorPanel.setBorder(BorderFactory.createTitledBorder("Колір"));
        
        MouseListener colorLabelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JComponent colorComponent = (JComponent) e.getSource();
                Color newColor = ColorChooser.showDialog(
                                     OptionsForm.this, 
                                     "Виберіть колір", 
                                     colorComponent.getBackground());            
                if (newColor != null) {
                    colorComponent.setBackground(newColor);
                }
            }            
        };
        
        JLabel boardColorLabel = new JLabel("Клітинки");
        boardColor = createColorLabel(gameForm.getBoardColor(), colorLabelListener);
        
        JLabel lineColorLabel = new JLabel("Лінії");
        lineColor = createColorLabel(gameForm.getLineColor(), colorLabelListener);
        
        JLabel firstColorLabel = new JLabel("Перший гравець");
        firstColor = createColorLabel(gameForm.getCoinColor(EnumPlayer.FIRST), colorLabelListener);
        
        JLabel secondColorLabel = new JLabel("Другий гравець");
        secondColor = createColorLabel(gameForm.getCoinColor(EnumPlayer.SECOND), colorLabelListener);
        
        colorPanel.add(boardColorLabel);
        colorPanel.add(boardColor, "grow");
        
        colorPanel.add(lineColorLabel);
        colorPanel.add(lineColor, "grow");
        
        colorPanel.add(firstColorLabel);
        colorPanel.add(firstColor, "grow");
        
        colorPanel.add(secondColorLabel);
        colorPanel.add(secondColor, "grow");
        
        tabbedPane.add("Вигляд", colorPanel);
        //Графіка        
        
        JButton button = new JButton("Ок");
        
        add(button, BorderLayout.SOUTH);
        pack();                        
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
    
    private JLabel createColorLabel(Color color, MouseListener listener) {
        final JLabel colorLabel = new JLabel();
        colorLabel.setOpaque(true);
        colorLabel.setBackground(color);
        colorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        colorLabel.addMouseListener(listener);
        return colorLabel;
    }
}
