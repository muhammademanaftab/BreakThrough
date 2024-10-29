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

    public BreakthroughGameGUI() {
        boardSize = getBoardSize();
        if (boardSize != -1) {
            setupGame();
        } else {
            System.exit(0);
        }
    }

    private int getBoardSize() {
        while (true) {
            String input = JOptionPane.showInputDialog(this, "Enter board size (6, 8, or 10):", "Board Size", JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                JOptionPane.showMessageDialog(this, "Exiting the game.", "Exit", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            try {
                int size = Integer.parseInt(input);
                if (size == 6 || size == 8 || size == 10) {
                    return size;
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter 6, 8, or 10.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setupGame() {
        board = new Board(boardSize);
        boardButtons = new JButton[boardSize][boardSize];
        setTitle("Breakthrough Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        setupBoard(boardPanel);

        statusLabel = new JLabel("Player 1's turn", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
    }

    private void setupBoard(JPanel boardPanel) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton cellButton = new JButton();
                cellButton.setFont(new Font("Arial", Font.PLAIN, 20));
                cellButton.addActionListener(new CellClickListener(row, col, board, this));
                boardButtons[row][col] = cellButton;
                boardPanel.add(cellButton);

                if (board.getBoard()[row][col] instanceof Pawn) {
                    Pawn pawn = (Pawn) board.getBoard()[row][col];
                    if (pawn.isPlayerOne) {
                        cellButton.setBackground(Color.RED);
                        cellButton.setForeground(Color.WHITE);
                        cellButton.setText("P1");
                    } else {
                        cellButton.setBackground(Color.BLUE);
                        cellButton.setForeground(Color.BLACK);
                        cellButton.setText("P2");
                    }
                } else {
                    cellButton.setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    public void updateBoardDisplay() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.getBoard()[row][col] instanceof Pawn) {
                    Pawn pawn = (Pawn) board.getBoard()[row][col];
                    boardButtons[row][col].setBackground(pawn.isPlayerOne ? Color.RED : Color.BLUE);
                    boardButtons[row][col].setForeground(pawn.isPlayerOne ? Color.WHITE : Color.BLACK);
                    boardButtons[row][col].setText(pawn.isPlayerOne ? "P1" : "P2");
                } else {
                    boardButtons[row][col].setBackground(Color.LIGHT_GRAY);
                    boardButtons[row][col].setText("");
                }
            }
        }
    }

    public void toggleTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
        statusLabel.setText("Player " + (isPlayerOneTurn ? "1" : "2") + "'s turn");
    }

    public void showWinMessage(int winningPlayer) {
        int choice = JOptionPane.showConfirmDialog(this, "Player " + winningPlayer + " wins! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            dispose();
        }
    }

    private void resetGame() {
        board = new Board(boardSize);
        isPlayerOneTurn = true;
        selectedPosition = null;
        statusLabel.setText("Player 1's turn");
        updateBoardDisplay();
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
}
