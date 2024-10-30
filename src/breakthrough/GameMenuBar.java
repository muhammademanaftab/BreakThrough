/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package breakthrough;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenuBar extends JMenuBar {

    private final JMenuItem restartItem;
    private final JMenuItem exitItem;
    private final JRadioButtonMenuItem size6x6;
    private final JRadioButtonMenuItem size8x8;
    private final JRadioButtonMenuItem size10x10;
    private final BreakthroughGameGUI gameGUI;

    public GameMenuBar(BreakthroughGameGUI gameGUI) {
        this.gameGUI = gameGUI;
        
        JMenu gameMenu = new JMenu("Game");

        // Restart menu item
        restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> gameGUI.restartGame());
        gameMenu.add(restartItem);

        // Exit menu item
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(exitItem);

        JMenu difficultyMenu = new JMenu("Change Difficulty");
        ButtonGroup difficultyGroup = new ButtonGroup();

        // Radio buttons for difficulty levels
        size6x6 = new JRadioButtonMenuItem("6x6");
        size6x6.addActionListener(e -> promptChangeDifficulty(6));
        difficultyGroup.add(size6x6);
        difficultyMenu.add(size6x6);

        size8x8 = new JRadioButtonMenuItem("8x8");
        size8x8.addActionListener(e -> promptChangeDifficulty(8));
        difficultyGroup.add(size8x8);
        difficultyMenu.add(size8x8);

        size10x10 = new JRadioButtonMenuItem("10x10");
        size10x10.addActionListener(e -> promptChangeDifficulty(10));
        difficultyGroup.add(size10x10);
        difficultyMenu.add(size10x10);

        add(gameMenu);
        add(difficultyMenu);
    }

    private void promptChangeDifficulty(int newSize) {
        int result = JOptionPane.showConfirmDialog(
                gameGUI,
                "Are you sure you want to change the board size to " + newSize + "x" + newSize + "?",
                "Confirm Board Size Change",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            gameGUI.changeDifficulty(newSize);
        } else {
            updateDifficultySelection(gameGUI.getBoardSize()); // Reset the selection if canceled
        }
    }

    public void updateDifficultySelection(int boardSize) {
        // Select the radio button based on the current board size
        if (boardSize == 6) {
            size6x6.setSelected(true);
        } else if (boardSize == 8) {
            size8x8.setSelected(true);
        } else if (boardSize == 10) {
            size10x10.setSelected(true);
        }
    }
}
