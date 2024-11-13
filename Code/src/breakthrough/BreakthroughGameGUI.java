/**
 * The BreakthroughGameGUI class is the main graphical user interface for the Breakthrough game.
 * It handles the game's setup, display, user interaction, and overall game flow.
 * Players can see the game board, select moves, and get feedback through this GUI.
 */
package breakthrough;

import javax.swing.*;
import java.awt.*;

public class BreakthroughGameGUI extends JFrame {

    private int boardSize; // Size of the board (e.g.... 6x6, 8x8, 10x10).
    private JButton[][] boardButtons; // Buttons representing each cell on the board.
    private Board board; // The game board object that handles game logic.
    private boolean isPlayerOneTurn = true; // Tracks whose turn it is (Player 1 starts first).
    private JLabel statusLabel; // Displaying the current game status (e.g... whose turn it is).
    private Position selectedPosition = null; // The currently selected position on the board.
    private String playerOneName; // Name of Player 1.
    private String playerTwoName; // Name of Player 2.
    private GameMenuBar menuBar; // Menu bar for game options.

    /**
     * Constructs the game GUI and initializes the game setup(calling initialize function to setup game).
     */
    public BreakthroughGameGUI() {
        initializeGame();
    }

    /**
     * Initializes the game by showing rules, getting player names,
     * selecting board size, and setting up the game board. ----Game ko initialize krwanay ke liay
     */
    private void initializeGame() {
        showGameRules();
        getPlayerNames();
        boardSize = getBoardSize();
        if (boardSize != -1) {
            setupGame();
        } else {
            System.exit(0);
        }
    }

    /**
     * Displays the game rules in a dialog box to explain how the game works.-----Rules ko Html show krwanay ke liay
     */
    private void showGameRules() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));
        panel.add(iconLabel, BorderLayout.WEST);

        JLabel textLabel = new JLabel("<html><body width='500'><h2>Breakthrough Game Rules</h2>"
                + "<p>Breakthrough is a two-player strategy game played on an n x n board. "
                + "Each player has pawns that can move one step forward or diagonally forward. "
                + "Pawns can capture opponents by moving diagonally forward onto them. "
                + "The game ends when a pawn reaches the opponent's side.</p>"
                + "<p>You can select the board size (6x6, 8x8, or 10x10) before starting. "
                + "After the game ends, a winner will be announced, and you can choose to start a new game.</p>"
                + "</body></html>");
        panel.add(textLabel, BorderLayout.CENTER);

        int option = JOptionPane.showOptionDialog(
                this,
                panel,
                "Game Rules",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"Next"},
                "Next"
        );

        if (option == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Taking input from the players to enter their names. Default names are assigned if input is empty. ----Defualt should be P1, P2
     */
    private void getPlayerNames() {
    // Get Player 1 name
    do {
        playerOneName = JOptionPane.showInputDialog(this, "Enter name for Player 1 (max 50 characters):", "Player Name", JOptionPane.QUESTION_MESSAGE);
        if (playerOneName == null) {
            System.exit(0); // Exit if the user cancels
        } else if (playerOneName.trim().isEmpty() || playerOneName.length() > 50) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid name (max 50 characters).", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            break; // Valid input, exit the loop
        }
    } while (true);

    // Get Player 2 name
    do {
        playerTwoName = JOptionPane.showInputDialog(this, "Enter name for Player 2 (max 50 characters):", "Player Name", JOptionPane.QUESTION_MESSAGE);
        if (playerTwoName == null) {
            System.exit(0); 
        } else if (playerTwoName.trim().isEmpty() || playerTwoName.length() > 50) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid name (max 50 characters).", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            break; 
        }
    } while (true);

    if (playerOneName.trim().isEmpty()) {
        playerOneName = "P1";
    }
    if (playerTwoName.trim().isEmpty()) {
        playerTwoName = "P2";
    }
}


    /**
     * Displays a dialog box for selecting the board size and returns the selected size.---Making board of desired state, so that taking user input
     * 
     * @return The board size (6, 8, or 10), or -1 if the user cancels.
     */
    public int getBoardSize() {
        String[] options = {"6x6", "8x8", "10x10"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose Difficulty Level",
                "Board Size",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(this, "Exiting the game.", "Exit", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        switch (choice) {
            case 0:
                return 6;
            case 1:
                return 8;
            case 2:
                return 10;
            default:
                return -1;
        }
    }

    /**
     * Sets up the game board, GUI components, and the initial game state.----Setting up board
     */
    private void setupGame() {
        board = new Board(boardSize);
        boardButtons = new JButton[boardSize][boardSize];
        isPlayerOneTurn = true;

        getContentPane().removeAll();
        setTitle("Breakthrough Game");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        menuBar = new GameMenuBar(this);
        setJMenuBar(menuBar);
        menuBar.updateDifficultySelection(boardSize);

        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        setupBoard(boardPanel);
        add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel(playerOneName + "'s turn", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    /**
     * Setting up the board panel by adding buttons for each cell and setting initial pawn states.
     * 
     * @param boardPanel The panel to configure.
     */
    private void setupBoard(JPanel boardPanel) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton cellButton = new JButton();
                cellButton.setFont(new Font("Arial", Font.PLAIN, 12));
                cellButton.addActionListener(new CellClickListener(row, col, board, this));
                boardButtons[row][col] = cellButton;
                boardPanel.add(cellButton);

                if (board.getBoard()[row][col] instanceof Pawn) {
                    Pawn pawn = (Pawn) board.getBoard()[row][col];
                    cellButton.setBackground(pawn.isPlayerOne ? Color.RED : Color.BLUE);
                    cellButton.setForeground(pawn.isPlayerOne ? Color.WHITE : Color.BLACK);
                    cellButton.setText(pawn.isPlayerOne ? playerOneName : playerTwoName);
                } else {
                    cellButton.setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    /**
     * Restarts the game by removing all components by using dispose and starting fresh.
     */
    public void restartGame() {
        dispose();
        BreakthroughGameGUI newGame = new BreakthroughGameGUI();
        newGame.setVisible(true);
    }

    /**
     * Changes the board size and resets the game with the new size.
     * 
     * @param size The new board size.
     */
    public void changeDifficulty(int size) {
        boardSize = size;
        setupGame();
        menuBar.updateDifficultySelection(boardSize);
    }

    /**
     * Displays a message indicating the winner and offers the option to start a new game.
     * 
     * @param winningPlayer The player number (1 or 2) who won.
     */
    public void showWinMessage(int winningPlayer) {
        String winner = (winningPlayer == 1) ? playerOneName : playerTwoName;
        int choice = JOptionPane.showConfirmDialog(this, winner + " wins! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {            
            restartGame();
        } else {
            dispose();
        }
    }

    /**
     * Updates the game board's display to show the current game state.
     */
    public void updateBoardDisplay() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.getBoard()[row][col] instanceof Pawn) {
                    Pawn pawn = (Pawn) board.getBoard()[row][col];
                    boardButtons[row][col].setBackground(pawn.isPlayerOne ? Color.RED : Color.BLUE);
                    boardButtons[row][col].setForeground(pawn.isPlayerOne ? Color.WHITE : Color.BLACK);
                    boardButtons[row][col].setText(pawn.isPlayerOne ? playerOneName : playerTwoName);
                } else {
                    boardButtons[row][col].setBackground(Color.LIGHT_GRAY);
                    boardButtons[row][col].setText("");
                }
            }
        }
    }

    /**
     * Toggles the turn between Player 1 and Player 2.
     */
    public void toggleTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
        statusLabel.setText((isPlayerOneTurn ? playerOneName : playerTwoName) + "'s turn");
    }

    // Getter and Setter methods for status label and selected position.
    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(Position selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }
}
