/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.miginfocom.swing.MigLayout;
import ua.vynnyk.board.*;
import ua.vynnyk.components.CountBoard;
import ua.vynnyk.game.*;
import ua.vynnyk.layout.SquareLayout;

/**
 *
 * @author Admin
 */
public class GameForm extends JFrame {
    private BoardGameInterface game;
    private GameBoard board;
    private JPanel panel;
    private CountBoard countBoard;
    private JMenuBar menuBar; 
    private JPanel panelActivePlayer;

    public GameForm(BoardGameInterface game) {
        this.game = game;
        setTitle("Reversi");
        initComponents();        
    }    

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);                
        setPreferredSize(new Dimension(800, 675));  
        setMinimumSize(new Dimension(400, 300));        
        //
        menuBar = new JMenuBar() {{
            
            add(new JMenu("Game") {{
                
                add(new JMenuItem("New Game") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            board.clear();
                            game.newGame();                            
                        }
                    });
                }});
                
                add(new JMenuItem("Undo Move") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //NOT IMPLEMENTED YET
                        }
                    });
                }});
                
                add(new JMenuItem("Show The Best Move") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //NOT IMPLEMENTED YET                          
                        }
                    });
                }});
                
                addSeparator();
                
                add(new JMenuItem("Save Game...") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            saveGame();
                        }
                    });
                }});
                
                add(new JMenuItem("Load Game...") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            loadGame();                           
                        }
                    });
                }});
                
                addSeparator();
                
                add(new JMenuItem("Exit") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                }});                
            }});
            
            add(new JMenu("Options") {{
                
                add(new JMenuItem("Options...") {{
                    
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new OptionsForm(GameForm.this, true).setVisible(true);
                        }
                    });
                }});
            }});
            
            add(new JMenu("Help") {{
                
                add(new JMenuItem("About...") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new AboutForm(GameForm.this, true).setVisible(true);
                        }
                    });
                }});
            }});
        }};
                
        setJMenuBar(menuBar);
        
        //
        board = new GameBoard();
        board.setPoolCoin(EnumPlayer.NONE, new EmptyCoinPool());
        board.setPoolCoin(EnumPlayer.FIRST, new SimpleCoinPool());
        board.setPoolCoin(EnumPlayer.SECOND, new SimpleCoinPool(Color.RED));
        board.setGame(game);
                
        add(SquareLayout.createSquareContainer(board), BorderLayout.CENTER);        
        
        countBoard = new CountBoard(board.getCoinColor(EnumPlayer.FIRST),
                                    board.getCoinColor(EnumPlayer.SECOND));
        
        panelActivePlayer = new JPanel(new GridLayout());            
        panelActivePlayer.setPreferredSize(new Dimension(150, 100)); 
        panelActivePlayer.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new MigLayout());
        panel.add(countBoard, "wrap 20px");  
        panel.add(new JLabel("Turn"), "align center, wrap");
        panel.add(panelActivePlayer);       
        add(panel, BorderLayout.EAST);
        addListeners();
        pack();
        setLocationRelativeTo(null);
    }

    private void addListeners() {
        final JFrame frame = this;
        
        game.addPutCoinListener(new PutCoinEventListener() {
            @Override
            public void PutCoin(PutCoinEvent e) {
                board.drawCoin(e.getX(), e.getY());               
            }            
        });
        game.addChangeCountListener(new ChangeCountEventListener() {
            @Override
            public void ChangeCount(ChangeCountEvent e) {
                changeCount(e.getFirst(), e.getSecond());
            }
        });
        game.addGameOverListener(new GameOverEventListener() {

            @Override
            public void GameOver(GameOverEvent e) {
                final String winmsg;
                final EnumPlayer winner = e.getWinner();
                if (winner == EnumPlayer.FIRST) {
                    winmsg = "First player win game!";
                } else if (winner == EnumPlayer.SECOND) {
                    winmsg = "Second player win game!";
                } else {
                    winmsg = "Drawn game!";
                } 
                JOptionPane.showMessageDialog(frame, winmsg, "Game over", JOptionPane.INFORMATION_MESSAGE);
            }
        });                
    }
    
    public Color getBoardColor() {
        return board.getBoardColor();
    }
    
    public void setBoardColor(Color color) {
        board.setBoardColor(color);
    }
    
    public Color getLineColor() {
        return board.getLineColor();
    }
    
    public void setLineColor(Color color) {
        board.setLineColor(color);
    }
    
    public Color getCoinColor(EnumPlayer player) {
        return board.getCoinColor(player);
    }
    
    public void setCoinColor(EnumPlayer player, Color color) {
        board.setCoinColor(player, color);
        
        if (player == EnumPlayer.FIRST) {
            countBoard.setFirstColor(color);
        } else {
            countBoard.setSecondColor(color);
        }        
        repaint();
    }
    
    private void saveGame() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Save game file (.sav)", "sav"));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {       
            GameUtility.saveGame(game, fc.getSelectedFile());            
        }
    }
    
    private void loadGame() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Save game file (.sav)", "sav"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {       
            game = GameUtility.loadGame(fc.getSelectedFile());
            board.clear();
            board.setGame(game);
            addListeners();
            refreshState(); // to adjust view to model state;
        }        
    }
    
    // change count and active player on view
    private void changeCount(int first, int second) {
        countBoard.setCount(first, second);
        panelActivePlayer.removeAll();
        panelActivePlayer.add((Component) board.getCoin(game.getActivePlayer()));
        panelActivePlayer.repaint();
    }

    //refresh data from model to view
    private void refreshState() {
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                board.drawCoin(i, j);
            }            
        }
        changeCount(game.getCount(EnumPlayer.FIRST), game.getCount(EnumPlayer.SECOND));
    }
}
