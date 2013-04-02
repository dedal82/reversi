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
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.miginfocom.swing.MigLayout;
import ua.vynnyk.board.*;
import ua.vynnyk.components.CountBoard;
import ua.vynnyk.controler.BoardGameControlerInterface;
import ua.vynnyk.game.*;
import ua.vynnyk.layout.SquareLayout;
import ua.vynnyk.translations.TranslateHelper;

/**
 *
 * @author Admin
 */
public class GameForm extends JFrame {
    private static final String SAVE_EXTENTION = ".sav";
    private BoardGameInterface game;
    private BoardGameControlerInterface controler;
    private GameBoard board;
    private JPanel panel;
    private CountBoard countBoard;
    private JMenuBar menuBar; 
    private JPanel panelActivePlayer;
    private JMenuItem startAIBattleItem;
    private JMenuItem stopAIBattleItem;
    private JMenuItem showBestMoveItem;

    public GameForm(BoardGameInterface game, BoardGameControlerInterface controler) {
        this.game = game;
        this.controler = controler;
        setTitle("Reversi");
        initComponents();        
    }    

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);                
        setPreferredSize(new Dimension(800, 675));  
        setMinimumSize(new Dimension(400, 300));        
        //
        menuBar = new JMenuBar() {{
            
            add(new JMenu(TranslateHelper.getString("main.menu.game")) {{
                
                add(new JMenuItem(TranslateHelper.getString("main.menu.game.newgame")) {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showBestMoveItem.setEnabled(true);
                            startAIBattleItem.setEnabled(true);
                            stopAIBattleItem.setEnabled(false);
                            controler.newGame();
                        }
                    });
                }});
                
                add(showBestMoveItem = new JMenuItem(TranslateHelper.getString("main.menu.game.showthebestmove")) {{
                    setEnabled(false);
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            controler.getBestMove();
                        }
                    });
                }});
                
                add(new JMenuItem(TranslateHelper.getString("main.menu.game.undolastmove")) {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            controler.undoMove();
                        }
                    });
                }});
                
                addSeparator();
                                                
                add(startAIBattleItem = new JMenuItem(TranslateHelper.getString("main.menu.game.startaivsaibattle")) {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            setEnabled(false);
                            stopAIBattleItem.setEnabled(true);
                            controler.startAIBattle();                            
                        }
                    });
                }});
                                                
                add(stopAIBattleItem = new JMenuItem(TranslateHelper.getString("main.menu.game.stopaivsaibattle")) {{
                    setEnabled(false);                            
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            setEnabled(false);
                            startAIBattleItem.setEnabled(true); 
                            controler.stopAIBattle();                            
                        }
                    });
                }});
                                
                addSeparator();
                
                add(new JMenuItem(TranslateHelper.getString("main.menu.game.savegame")) {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            saveGame();
                        }
                    });
                }});
                
                add(new JMenuItem(TranslateHelper.getString("main.menu.game.loadgame")) {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            loadGame();                           
                        }
                    });
                }});
                
                addSeparator();
                
                add(new JMenuItem(TranslateHelper.getString("main.menu.game.exit")) {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                }});                
            }});
            
            add(new JMenu(TranslateHelper.getString("main.menu.network")) {{
                
                add(new JMenuItem(TranslateHelper.getString("main.menu.network.createnetworkgame")) {{
                    
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            controler.newGame();
                        }
                    });
                }});
                
                add(new JMenuItem(TranslateHelper.getString("main.menu.network.connecttonetworkgame")) {{
                    
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                        }
                    });
                }});
            }});
            
            add(new JMenu(TranslateHelper.getString("main.menu.options")) {{
                
                add(new JMenuItem(TranslateHelper.getString("main.menu.options.options")) {{
                    
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new OptionsForm(GameForm.this, true).setVisible(true);
                        }
                    });
                }});
            }});
            
            add(new JMenu(TranslateHelper.getString("main.menu.help")) {{
                
                add(new JMenuItem(TranslateHelper.getString("main.menu.help.about")) {{
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
        board.setControler(controler);
        controler.setBoard(board);
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
        panel.add(new JLabel(TranslateHelper.getString("main.turn")), "align center, wrap");
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
                final PutCoinEvent evt = e;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        board.drawCoin(evt.getX(), evt.getY());               
                    }
                });                                 
            }            
        });
        game.addChangeCountListener(new ChangeCountEventListener() {
            @Override
            public void ChangeCount(ChangeCountEvent e) { 
                final ChangeCountEvent evt = e;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        changeCount(evt.getFirst(), evt.getSecond());
                    }
                });                 
            }
        });
        game.addGameOverListener(new GameOverEventListener() {

            @Override
            public void GameOver(GameOverEvent e) {
                final String winmsg;
                final EnumPlayer winner = e.getWinner();
                if (winner == EnumPlayer.FIRST) {
                    winmsg = TranslateHelper.getString("main.firstwin");
                } else if (winner == EnumPlayer.SECOND) {
                    winmsg = TranslateHelper.getString("main.secondwin");
                } else {
                    winmsg = TranslateHelper.getString("main.draw");
                } 
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        startAIBattleItem.setEnabled(true);
                        stopAIBattleItem.setEnabled(false);
                        JOptionPane.showMessageDialog(frame, winmsg, TranslateHelper.getString("main.gameover"), JOptionPane.INFORMATION_MESSAGE);
                    }
                });                
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
    
    public int getPlayers() {
        return controler.getStatus();
    }
    
    public void setPlayers(int players) {
        controler.setStatus(players);
    }
    
    public int getAILevel() {
        return (int) game.getOption("OPTION_AI_LEVEL");
    }
    
    public void setAILevel(int level) {
        game.setOption("OPTION_AI_LEVEL", level);
    }    
    
    private void saveGame() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Save game file (" + SAVE_EXTENTION + ")", SAVE_EXTENTION));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {       
            if (!fc.getSelectedFile().getAbsolutePath().endsWith(SAVE_EXTENTION)) {
                GameUtility.saveGame(game, new File(fc.getSelectedFile().getAbsolutePath() + SAVE_EXTENTION));
            } else {
                GameUtility.saveGame(game, fc.getSelectedFile());
            }                                  
        }
    }
    
    private void loadGame() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Save game file (" + SAVE_EXTENTION + ")", SAVE_EXTENTION));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {       
            game = GameUtility.loadGame(fc.getSelectedFile());
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
        panelActivePlayer.validate();
        panelActivePlayer.repaint();        
    }

    //refresh data from model to view
    private void refreshState() {
        board.refreshCoins();
        changeCount(game.getCount(EnumPlayer.FIRST), game.getCount(EnumPlayer.SECOND));
    }        
}
