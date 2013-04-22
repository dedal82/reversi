/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.plaf.OptionPaneUI;
import net.miginfocom.swing.MigLayout;
import ua.vynnyk.components.ColorChooser;
import ua.vynnyk.game.EnumPlayer;
import ua.vynnyk.options.Options;
import ua.vynnyk.options.OptionsList;
import ua.vynnyk.translations.TranslateHelper;

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
    private JComboBox<Locale> localeBox;
    private JButton buttonApply;
    private JButton buttonCancel;

    public OptionsForm(GameForm gameForm, boolean modal) {
        super(gameForm, modal);        
        this.gameForm = gameForm;
        this.level = gameForm.getAILevel();
        this.players = gameForm.getPlayers();
        
        initComponents();
    }    

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);        
        setTitle(TranslateHelper.getString("options"));  
        setPreferredSize(new Dimension(400, 250));
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        
        //Players
        JPanel gamePanel = new JPanel(new MigLayout("fillx, wrap 1", "", ""));        
        
        JPanel playersPanel = new JPanel();
        playersPanel.setBorder(BorderFactory.createTitledBorder(TranslateHelper.getString("options.game.players")));
        JRadioButton plVsPl = new JRadioButton(TranslateHelper.getString("options.game.plvspl"));
        plVsPl.setActionCommand("0");        
        JRadioButton plVsAI = new JRadioButton(TranslateHelper.getString("options.game.plvsai"));
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
        switch (players) {
            case 0: plVsPl.setSelected(true);
                    break;
            case 1: plVsAI.setSelected(true);
                    break;    
        }        
        playersPanel.add(plVsPl);
        playersPanel.add(plVsAI);
        
        JPanel levelAIPanel = new JPanel();
        levelAIPanel.setBorder(BorderFactory.createTitledBorder(TranslateHelper.getString("options.game.ailevel")));
        
        JRadioButton level0 = new JRadioButton(TranslateHelper.getString("options.game.begginer"));
        level0.setActionCommand("0");        
        JRadioButton level1 = new JRadioButton(TranslateHelper.getString("options.game.normal"));
        level1.setActionCommand("1");        
        JRadioButton level2 = new JRadioButton(TranslateHelper.getString("options.game.advanced"));
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
        switch (level) {
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
        
        tabbedPane.add(TranslateHelper.getString("options.game"), gamePanel);
        //Players
        //Graphic
        
        JPanel colorPanel = new JPanel(new MigLayout("fillx, wrap 2", "[align right]10[max]", ""));
        colorPanel.setBorder(BorderFactory.createTitledBorder(TranslateHelper.getString("options.view.colors")));
        
        MouseListener colorLabelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JComponent colorComponent = (JComponent) e.getSource();
                Color newColor = ColorChooser.showDialog(
                                     OptionsForm.this, 
                                     TranslateHelper.getString("options.view.choosecolor"), 
                                     colorComponent.getBackground());            
                if (newColor != null) {
                    colorComponent.setBackground(newColor);
                }
            }            
        };
        
        JLabel boardColorLabel = new JLabel(TranslateHelper.getString("options.view.cells"));
        boardColor = createColorLabel(gameForm.getBoardColor(), colorLabelListener);
        
        JLabel lineColorLabel = new JLabel(TranslateHelper.getString("options.view.lines"));
        lineColor = createColorLabel(gameForm.getLineColor(), colorLabelListener);
        
        JLabel firstColorLabel = new JLabel(TranslateHelper.getString("options.view.firstplayer"));
        firstColor = createColorLabel(gameForm.getCoinColor(EnumPlayer.FIRST), colorLabelListener);
        
        JLabel secondColorLabel = new JLabel(TranslateHelper.getString("options.view.secondplayer"));
        secondColor = createColorLabel(gameForm.getCoinColor(EnumPlayer.SECOND), colorLabelListener);
        
        colorPanel.add(boardColorLabel);
        colorPanel.add(boardColor, "grow");
        
        colorPanel.add(lineColorLabel);
        colorPanel.add(lineColor, "grow");
        
        colorPanel.add(firstColorLabel);
        colorPanel.add(firstColor, "grow");
        
        colorPanel.add(secondColorLabel);
        colorPanel.add(secondColor, "grow");
        
        tabbedPane.add(TranslateHelper.getString("options.view"), colorPanel);
        //View 
        //Language
        JPanel langPanel = new JPanel();
        langPanel.setBorder(BorderFactory.createTitledBorder(TranslateHelper.getString("options.chooselang")));
        localeBox = new JComboBox<>(TranslateHelper.getLocalizations());
        localeBox.setSelectedItem(TranslateHelper.getLocale());
        langPanel.add(localeBox);
        tabbedPane.add(TranslateHelper.getString("options.language"), langPanel);        
        //Language
                
        JPanel buttonPanel = new JPanel();
                    
        buttonApply = new JButton(TranslateHelper.getString("options.apply"));
        buttonCancel = new JButton(TranslateHelper.getString("options.cancel"));
        
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
                boolean isChanged = false;
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
                    Options.setOption(OptionsList.PLAYERS, Integer.toString(players));
                    isChanged = true;
                }
                if (gameForm.getAILevel() != level) {                    
                    gameForm.setAILevel(level);                    
                    Options.setOption(OptionsList.LEVEL, Integer.toString(level));
                    isChanged = true;
                }
                if (!TranslateHelper.getLocale().equals(localeBox.getSelectedItem())) {                    
                    TranslateHelper.setResources((Locale) localeBox.getSelectedItem());
                    Options.setOption(OptionsList.LOCALE, localeBox.getSelectedItem().toString());
                    isChanged = true;
                    JOptionPane.showMessageDialog(OptionsForm.this, TranslateHelper.getString("options.reloadmessage"));
                }
                if (isChanged) {
                    Options.save();
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
