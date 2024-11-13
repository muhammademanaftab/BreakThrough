/**
 * The GameMenuBar class represents the menu bar in the Breakthrough game GUI.
 * It provides options for restarting the game, exiting the application, 
 * and changing the game board's difficulty level.----- is class ka use yeh ha saray menu options bnanany ke liay.
 * 
 * This class interacts with the main game GUI (`BreakthroughGameGUI`) to apply user actions.
 */
package breakthrough;

import javax.swing.*;

public class GameMenuBar extends JMenuBar {

    private final JMenuItem restartItem; // Menu item for restarting the game.
    private final JMenuItem exitItem; // Menu item for exiting the game.
    private final JRadioButtonMenuItem size6x6; // Radio button for selecting 6x6 board size.
    private final JRadioButtonMenuItem size8x8; // Radio button for selecting 8x8 board size.
    private final JRadioButtonMenuItem size10x10; // Radio button for selecting 10x10 board size.
    private final BreakthroughGameGUI gameGUI; // Reference to the main game GUI.

    /**
     * Creating the menu bar with game options such as restart, exit, and difficulty level.
     * 
     * @param gameGUI The main game GUI to interact with for applying menu actions.
     */
    public GameMenuBar(BreakthroughGameGUI gameGUI) {
        this.gameGUI = gameGUI;
        
        JMenu gameMenu = new JMenu("Game");

        restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> gameGUI.restartGame());
        gameMenu.add(restartItem);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(exitItem);

        JMenu difficultyMenu = new JMenu("Change Difficulty");
        ButtonGroup difficultyGroup = new ButtonGroup();

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

    /**
     * Prompts the user to confirm the board size change and applies the new size if confirmed.
     * --------agar use board ka size change krnaa chahta ha to phele os se confimration lena ha yeh oska box ha
     * 
     * @param newSize The new board size selected by the user (6, 8, or 10).
     */
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
            updateDifficultySelection(gameGUI.getBoardSize()); // Reset the selection if canceled.
        }
    }

    /**
     * Updates the selection in the difficulty menu based on the current board size.
     * 
     * ---Board ka szie update krne liay agar confirmaiton mil gya ha to
     * @param boardSize The current size of the game board.
     */
    public void updateDifficultySelection(int boardSize) {
        // Automatically select the appropriate radio button for the current board size.
        if (boardSize == 6) {
            size6x6.setSelected(true);
        } else if (boardSize == 8) {
            size8x8.setSelected(true);
        } else if (boardSize == 10) {
            size10x10.setSelected(true);
        }
    }
}
