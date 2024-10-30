/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package breakthrough;

import javax.swing.*;
import java.awt.*;

public class BreakthroughGameGUI extends JFrame {

    private int boardSize;
    private JButton[][] boardButtons;
    private Board board;
    private boolean isPlayerOneTurn = true;
    private JLabel statusLabel;
    private Position selectedPosition = null;
    private String playerOneName;
    private String playerTwoName;
    private GameMenuBar menuBar;

    public BreakthroughGameGUI() {
        initializeGame();
    }

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

    private void showGameRules() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));
        panel.add(iconLabel, BorderLayout.WEST);

        JLabel textLabel = new JLabel("<html><body width='500'><h2>Breakthrough Game Rules</h2>"
                + "<p>Breakthrough is a two-player game, played on a board consisting of n x n fields. "
                + "Each player has 2n dolls in two rows, placed initially on the player’s side (similarly to the chess game, "
                + "but here, all dolls of a player look the same). A player can move their doll one step forward or one step "
                + "diagonally forward (no backward moves allowed). A player can capture an opponent's doll by moving diagonally "
                + "forward onto it. The game is won when a player's doll reaches the opposite edge of the board.</p>"
                + "<p>The board size is selectable (6x6, 8x8, or 10x10). The game should recognize when it has ended, and it "
                + "must display which player won in a message box. After this, a new game should start automatically.</p>"
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

    private void getPlayerNames() {
        playerOneName = JOptionPane.showInputDialog(this, "Enter name for Player 1:", "Player Name", JOptionPane.QUESTION_MESSAGE);
        if (playerOneName == null) {
            System.exit(0);
        } else if (playerOneName.trim().isEmpty()) {
            playerOneName = "P1";
        }

        playerTwoName = JOptionPane.showInputDialog(this, "Enter name for Player 2:", "Player Name", JOptionPane.QUESTION_MESSAGE);
        if (playerTwoName == null) {
            System.exit(0);
        } else if (playerTwoName.trim().isEmpty()) {
            playerTwoName = "P2";
        }
    }

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

    public void restartGame() {
        initializeGame();
    }

    public void changeDifficulty(int size) {
        boardSize = size;
        setupGame();
        menuBar.updateDifficultySelection(boardSize);
    }

    public void showWinMessage(int winningPlayer) {
        String winner = (winningPlayer == 1) ? playerOneName : playerTwoName;
        int choice = JOptionPane.showConfirmDialog(this, winner + " wins! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            dispose();
        }
    }

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

    public void toggleTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
        statusLabel.setText((isPlayerOneTurn ? playerOneName : playerTwoName) + "'s turn");
    }

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
}
