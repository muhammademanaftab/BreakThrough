/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Muhammad Eman Aftab
 */
package breakthrough;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellClickListener implements ActionListener {

    private int row;
    private int col;
    private Board board;
    private BreakthroughGameGUI gameGUI;

    public CellClickListener(int row, int col, Board board, BreakthroughGameGUI gameGUI) {
        this.row = row;
        this.col = col;
        this.board = board;
        this.gameGUI = gameGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Position selectedPosition = gameGUI.getSelectedPosition();
        JLabel statusLabel = gameGUI.getStatusLabel();
        boolean isPlayerOneTurn = gameGUI.isPlayerOneTurn();

        if (selectedPosition == null) {
            Doll selectedDoll = board.getBoard()[row][col];
            if (selectedDoll instanceof Pawn && ((Pawn) selectedDoll).isPlayerOne == isPlayerOneTurn) {
                gameGUI.setSelectedPosition(new Position(row, col));
                statusLabel.setText("Select a destination for Player " + (isPlayerOneTurn ? "1" : "2"));
            }
        } else {
            Position newPosition = new Position(row, col);
            Doll selectedDoll = board.getBoard()[selectedPosition.getRow()][selectedPosition.getColumn()];

            if (selectedDoll instanceof Pawn) {
                Pawn selectedPawn = (Pawn) selectedDoll;
                if (board.movePawn(selectedPawn, newPosition)) {
                    gameGUI.updateBoardDisplay();
                    if (board.checkWin(selectedPawn)) {
                        gameGUI.showWinMessage(isPlayerOneTurn ? 1 : 2);
                    } else {
                        gameGUI.toggleTurn();
                    }
                } else {
                    statusLabel.setText("Invalid move. Try again.");
                }
            }
            gameGUI.setSelectedPosition(null);
        }
    }
}
