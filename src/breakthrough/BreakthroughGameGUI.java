/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package breakthrough;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BreakthroughGameGUI extends JFrame {

    private int boardSize;
    private JButton[][] boardButtons;
    private Board board;
    private boolean isPlayerOneTurn = true;
    private JLabel statusLabel;
    private Position selectedPosition = null;

    public BreakthroughGameGUI(int size) {
        this.boardSize = size;
        this.board = new Board(size);
        this.boardButtons = new JButton[size][size];

        setTitle("Breakthrough Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add the game board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(size, size));
        initializeBoard(boardPanel);

        // Status label to show player turns and messages
        statusLabel = new JLabel("Player 1's turn", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
    }

    private void initializeBoard(JPanel boardPanel) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton cellButton = new JButton();
                cellButton.setFont(new Font("Arial", Font.PLAIN, 20));
                cellButton.addActionListener(new CellClickListener(row, col));
                boardButtons[row][col] = cellButton;
                boardPanel.add(cellButton);

                // Place player pawns visually based on initial setup
                if (board.board[row][col] instanceof Pawn) {
                    Pawn pawn = (Pawn) board.board[row][col];
                    cellButton.setText(pawn.isPlayerOne ? "P1" : "P2");
                }
            }
        }
    }

    private void updateBoardDisplay() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.board[row][col] instanceof Pawn) {
                    Pawn pawn = (Pawn) board.board[row][col];
                    boardButtons[row][col].setText(pawn.isPlayerOne ? "P1" : "P2");
                } else {
                    boardButtons[row][col].setText("");
                }
            }
        }
    }

    private void toggleTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
        statusLabel.setText("Player " + (isPlayerOneTurn ? "1" : "2") + "'s turn");
    }

    private void showWinMessage(int winningPlayer) {
        int choice = JOptionPane.showConfirmDialog(
                this, "Player " + winningPlayer + " wins! Would you like to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            dispose(); 
        }
    }

    private void resetGame() {
        // Reset the board to the initial state
        board = new Board(boardSize);
        isPlayerOneTurn = true;
        selectedPosition = null;
        statusLabel.setText("Player 1's turn");
        updateBoardDisplay(); // Refresh the GUI to reflect the reset state
    }

    private class CellClickListener implements ActionListener {

        private int row;
        private int col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedPosition == null) {
                // Select a pawn
                Doll selectedDoll = board.board[row][col];
                if (selectedDoll instanceof Pawn && ((Pawn) selectedDoll).isPlayerOne == isPlayerOneTurn) {
                    selectedPosition = new Position(row, col);
                    statusLabel.setText("Select a destination for Player " + (isPlayerOneTurn ? "1" : "2"));
                }
            } else {
                // Move the pawn
                Position newPosition = new Position(row, col);
                Doll selectedDoll = board.board[selectedPosition.getRow()][selectedPosition.getColumn()];

                if (selectedDoll instanceof Pawn) {
                    Pawn selectedPawn = (Pawn) selectedDoll;
                    if (board.movePawn(selectedPawn, newPosition)) {
                        updateBoardDisplay();

                        // Check for win
                        if (board.checkWin(selectedPawn)) {
                            showWinMessage(isPlayerOneTurn ? 1 : 2);
                        } else {
                            toggleTurn();
                        }
                    } else {
                        statusLabel.setText("Invalid move. Try again.");
                    }
                }
                selectedPosition = null; // Reset selection
            }
        }
    }
}
