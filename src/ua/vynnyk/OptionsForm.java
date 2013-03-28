/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
    private int players;
    private int level;
    private JButton buttonApply;
    private JButton buttonCancel;

    public OptionsForm(GameForm gameForm, boolean modal) {
        super(gameForm, modal);        
        this.gameForm = gameForm;
        
        initComponents();
    }    

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        setTitle("Options");  
        setPreferredSize(new Dimension(300, 250));
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        
        //Players
        JPanel gamePanel = new JPanel(new MigLayout("fillx, wrap 1", "", ""));        
        
        JPanel playersPanel = new JPanel();
        playersPanel.setBorder(BorderFactory.createTitledBorder("Players"));
        JRadioButton plVsPl = new JRadioButton("Player Vs Player");
        plVsPl.setActionCommand("0");        
        JRadioButton plVsAI = new JRadioButton("Player Vs Ai");
        plVsAI.setActionCommand("1");
        ButtonGroup btnGroupPlayers = new ButtonGroup();
        btnGroupPlayers.add(plVsPl);
        btnGroupPlayers.add(plVsAI);        
        ActionListener lstnrPlayers = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                players = Integer.parseInt(ae.getActionCommand());
            }
        };
        plVsPl.addActionListener(lstnrPlayers);
        plVsAI.addActionListener(lstnrPlayers);
        switch (gameForm.getPlayers()) {
            case 0: plVsPl.setSelected(true);
                    break;
            case 1: plVsAI.setSelected(true);
                    break;    
        }        
        playersPanel.add(plVsPl);
        playersPanel.add(plVsAI);
        
        JPanel levelAIPanel = new JPanel();
        levelAIPanel.setBorder(BorderFactory.createTitledBorder("AI Level"));
        
        JRadioButton level0 = new JRadioButton("Begginer");
        level0.setActionCommand("0");        
        JRadioButton level1 = new JRadioButton("Normal");
        level1.setActionCommand("1");        
        JRadioButton level2 = new JRadioButton("Advanced");
        level2.setActionCommand("2");
        ButtonGroup btnGroupLevel = new ButtonGroup();
        btnGroupLevel.add(level0);
        btnGroupLevel.add(level1);
        btnGroupLevel.add(level2);
        ActionListener lstnrLevel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                level = Integer.parseInt(ae.getActionCommand());
            }
        };
        level0.addActionListener(lstnrLevel);
        level1.addActionListener(lstnrLevel);
        level2.addActionListener(lstnrLevel);       
        switch (gameForm.getAILevel()) {
            case 0: level0.setSelected(true);
                    break;
            case 1: level1.setSelected(true);
                    break;
            case 2: level2.setSelected(true);
                    break;    
        }
        levelAIPanel.add(level0);
        levelAIPanel.add(level1);
        levelAIPanel.add(level2);
        
        gamePanel.add(playersPanel, "grow");
        gamePanel.add(levelAIPanel, "grow");
        
        tabbedPane.add("Game", gamePanel);
        //Players
        //Graphic
        
        JPanel colorPanel = new JPanel(new MigLayout("fillx, wrap 2", "[align right]10[max]", ""));
        colorPanel.setBorder(BorderFactory.createTitledBorder("Colors"));
        
        MouseListener colorLabelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JComponent colorComponent = (JComponent) e.getSource();
                Color newColor = ColorChooser.showDialog(
                                     OptionsForm.this, 
                                     "Choose Color", 
                                     colorComponent.getBackground());            
                if (newColor != null) {
                    colorComponent.setBackground(newColor);
                }
            }            
        };
        
        JLabel boardColorLabel = new JLabel("Cells");
        boardColor = createColorLabel(gameForm.getBoardColor(), colorLabelListener);
        
        JLabel lineColorLabel = new JLabel("Lines");
        lineColor = createColorLabel(gameForm.getLineColor(), colorLabelListener);
        
        JLabel firstColorLabel = new JLabel("First Player");
        firstColor = createColorLabel(gameForm.getCoinColor(EnumPlayer.FIRST), colorLabelListener);
        
        JLabel secondColorLabel = new JLabel("Second Player");
        secondColor = createColorLabel(gameForm.getCoinColor(EnumPlayer.SECOND), colorLabelListener);
        
        colorPanel.add(boardColorLabel);
        colorPanel.add(boardColor, "grow");
        
        colorPanel.add(lineColorLabel);
        colorPanel.add(lineColor, "grow");
        
        colorPanel.add(firstColorLabel);
        colorPanel.add(firstColor, "grow");
        
        colorPanel.add(secondColorLabel);
        colorPanel.add(secondColor, "grow");
        
        tabbedPane.add("Graphic", colorPanel);
        //Графіка          
                
        JPanel buttonPanel = new JPanel();
                    
        buttonApply = new JButton("Apply");
        buttonCancel = new JButton("Cancel");
        
        buttonPanel.add(buttonApply);
        buttonPanel.add(buttonCancel);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();                        
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        addListeners();
    }
    
    private JLabel createColorLabel(Color color, MouseListener listener) {
        final JLabel colorLabel = new JLabel();
        colorLabel.setOpaque(true);
        colorLabel.setBackground(color);
        colorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        colorLabel.addMouseListener(listener);
        return colorLabel;
    }

    private void addListeners() {
        buttonApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameForm.getBoardColor() != boardColor.getBackground()) {
                    gameForm.setBoardColor(boardColor.getBackground());
                }
                if (gameForm.getLineColor() != lineColor.getBackground()) {
                    gameForm.setLineColor(lineColor.getBackground());
                }
                if (gameForm.getCoinColor(EnumPlayer.FIRST) != firstColor.getBackground()) {
                    gameForm.setCoinColor(EnumPlayer.FIRST, firstColor.getBackground());
                }
                if (gameForm.getCoinColor(EnumPlayer.SECOND) != secondColor.getBackground()) {
                    gameForm.setCoinColor(EnumPlayer.SECOND, secondColor.getBackground());
                }  
                if (gameForm.getPlayers() != players) {
                    gameForm.setPlayers(players);
                }
                if (gameForm.getAILevel() != level) {
                    gameForm.setAILevel(level);
                }
                setVisible(false);
                dispose();
            }
        });
        
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }
}
