/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.vynnyk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import ua.vynnyk.board.BoardClickEvent;
import ua.vynnyk.board.BoardClickEventListener;
import ua.vynnyk.board.GameBoard;
import ua.vynnyk.board.SimpleCoin;
import ua.vynnyk.game.BoardGameInterface;
import ua.vynnyk.game.ChangeCountEvent;
import ua.vynnyk.game.ChangeCountEventListener;
import ua.vynnyk.game.EnumPlayer;
import ua.vynnyk.game.GameOverEvent;
import ua.vynnyk.game.GameOverEventListener;
import ua.vynnyk.game.PutCoinEvent;
import ua.vynnyk.game.PutCoinEventListener;

/**
 *
 * @author Admin
 */
public class GameForm extends JFrame {
    private BoardGameInterface game;
    private GameBoard board;
    private JPanel panel;
    private JLabel label;
    private JMenuBar menuBar; 
    private JPanel panelActivePlayer;

    public GameForm(BoardGameInterface game) {
        this.game = game;
        setTitle("Реверсі");
        initComponents();        
    }    

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);        
        setPreferredSize(new Dimension(701, 621));  
        
        //
        menuBar = new JMenuBar() {{
            add(new JMenu("Гра") {{
                add(new JMenuItem("Нова гра") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            game.newGame();
                            board.repaint();
                        }
                    });
                }});
                addSeparator();
                add(new JMenuItem("Вихід") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                }});
            }});
            add(new JMenu("Налаштування"));
            add(new JMenu("Допомога") {{
                add(new JMenuItem("Про Реверсі...") {{
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
        board.setCoin(EnumPlayer.FIRST, new SimpleCoin());
        board.setCoin(EnumPlayer.SECOND, new SimpleCoin(Color.RED));
        board.setGame(game);
        add(board, BorderLayout.CENTER);        
        label = new JLabel("0:0", JLabel.CENTER);
        label.setPreferredSize(new Dimension(150, 60));
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 50));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));                
        panel = new JPanel();
        panel.setLayout(new MigLayout());
        panel.add(label, "wrap");        
        panelActivePlayer = new JPanel();
        panelActivePlayer.setPreferredSize(label.getPreferredSize());      
        panel.add(panelActivePlayer);
        add(panel, BorderLayout.EAST);
        addListeners();
        pack();
    }

    private void addListeners() {
        final JFrame frame = this;
        
        game.addPutCoinListener(new PutCoinEventListener() {
            @Override
            public void PutCoin(PutCoinEvent e) {
                board.drawCoin(board.getGraphics(), e.getX(), e.getY());               
            }            
        });
        game.addChangeCountListener(new ChangeCountEventListener() {
            @Override
            public void ChangeCount(ChangeCountEvent e) {
                label.setText(e.getFirst() + ":" + e.getSecond());                
            }
        });
        game.addGameOverListener(new GameOverEventListener() {

            @Override
            public void GameOver(GameOverEvent e) {
                final String winmsg;
                final EnumPlayer winner = e.getWinner();
                if (winner == EnumPlayer.FIRST) {
                    winmsg = "Виграв перший гравець";
                } else if (winner == EnumPlayer.SECOND) {
                    winmsg = "Виграв другий гравець";
                } else {
                    winmsg = "Бойова нічия!";
                } 
                JOptionPane.showMessageDialog(frame, winmsg, "Гру завершено", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        board.addBoardClickListener(new BoardClickEventListener() {
            @Override
            public void BoardClick(BoardClickEvent e) {
                game.doMove(e.getX(), e.getY());
            }           
        });
    }
}
