/**
 * The Pawn class represents a game piece in the Breakthrough game.
 * Each pawn belongs to either Player 1 or Player 2 and can move or capture
 * based on specific rules.
 * 
 * Pawns move one step forward or diagonally forward, and they can capture
 * opponent pawns diagonally forward.
 */
package breakthrough;

public class Pawn extends Doll {
    public boolean isPlayerOne; // Indicates if this pawn belongs to Player 1.

    /**
     * Creates a Pawn with a specific position and player assignment.
     * 
     * @param position The initial position of the pawn on the board.
     * @param isPlayerOne True if the pawn belongs to Player 1, false otherwise.
     */
    public Pawn(Position position, boolean isPlayerOne) {
        super(position);
        this.isPlayerOne = isPlayerOne;
    }

    /**
     * Determines if the pawn can move to the specified position.
     * A pawn can move forward by one row (straight or diagonally) if:
     * - It is not captured.
     * - The move follows the rules for the player it belongs to.
     * 
     * @param newPosition The position the pawn wants to move to.
     * @return True if the move is valid, false otherwise.
     */
    @Override
    public boolean canMove(Position newPosition) {
        if (isCaptured) return false; // A captured pawn cannot move.

        int rowDiff = newPosition.getRow() - position.getRow();
        int colDiff = Math.abs(newPosition.getColumn() - position.getColumn());

        // Check if the move is valid for Player 1 or Player 2.
        if (isPlayerOne) {
            return (rowDiff == 1 && colDiff <= 1); // Player 1 moves forward.
        } else {
            return (rowDiff == -1 && colDiff <= 1); // Player 2 moves backward (towards their goal).
        }
    }

    /**
     * Determines if the pawn can capture an opponent pawn at the specified position.
     * Capturing is allowed diagonally forward (1 row and 1 column difference).
     * 
     * @param opponentPosition The position of the opponent pawn.
     * @return True if the capture move is valid, false otherwise.
     */
    @Override
    public boolean canCapture(Position opponentPosition) {
        if (isCaptured) return false; // A captured pawn cannot capture others.

        int rowDiff = opponentPosition.getRow() - position.getRow();
        int colDiff = Math.abs(opponentPosition.getColumn() - position.getColumn());

        // Check if the capture is valid for Player 1 or Player 2.
        if (isPlayerOne) {
            return (rowDiff == 1 && colDiff == 1); // Player 1 captures diagonally forward.
        } else {
            return (rowDiff == -1 && colDiff == 1); // Player 2 captures diagonally backward.
        }
    }
}
