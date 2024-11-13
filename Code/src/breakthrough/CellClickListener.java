/**
 * The CellClickListener class handles user clicks on the game board cells.
 * It determines the player's descision, such as selecting a pawn or moving it,
 * and interacts with the game logic to perform the corresponding actions.
 * 
 * This listener ensures that moves are valid, updates the game board, and
 * checks for winning conditions after each move.
 */
package breakthrough;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellClickListener implements ActionListener {

    private int row; // The row index of the clicked cell.
    private int col; // The column index of the clicked cell.
    private Board board; // The game board that contains the game logic.
    private BreakthroughGameGUI gameGUI; // The GUI for updating the game display and status.

    /**
     * Creating  a CellClickListener for a specific cell on the board.
     * 
     * @param row The row index of the cell this listener is attached to.
     * @param col The column index of the cell this listener is attached to.
     * @param board The game board to interact with for game logic.
     * @param gameGUI The game GUI for accessing game state and updating the display.
     */
    public CellClickListener(int row, int col, Board board, BreakthroughGameGUI gameGUI) {
        this.row = row;
        this.col = col;
        this.board = board;
        this.gameGUI = gameGUI;
    }

    /**
     * Handles the cell click event. 
     * - If no pawn is selected, it selects the pawn in the clicked cell (if valid).
     * - If a pawn is already selected, it attempts to move it to the clicked cell.
     * - Updates the game display and status after each action.
     * 
     * @param e The action event triggered by clicking a cell.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Position selectedPosition = gameGUI.getSelectedPosition();
        JLabel statusLabel = gameGUI.getStatusLabel();
        boolean isPlayerOneTurn = gameGUI.isPlayerOneTurn();

        // If no pawn is selected, select a pawn from the clicked cell --- Agar pawn select nai hwua to kia krna ha
        if (selectedPosition == null) {
            Doll selectedDoll = board.getBoard()[row][col];
            if (selectedDoll instanceof Pawn && ((Pawn) selectedDoll).isPlayerOne == isPlayerOneTurn) {
                gameGUI.setSelectedPosition(new Position(row, col));
                statusLabel.setText("Select a destination for Player " + (isPlayerOneTurn ? "1" : "2"));
            }
        } 
        // If a pawn is already selected, attempt to move it,,ahgar select hogya to baad ak decision
        else {
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
